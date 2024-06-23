import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

  @SuppressWarnings("unchecked")
  private static Map<String,Value> readScope(String filename) throws IOException, ClassNotFoundException {
    FileInputStream fis = new FileInputStream(filename);
    ObjectInputStream ois = new ObjectInputStream(fis);
    Map<String,Value> scope = (Map<String,Value>)ois.readObject();
    ois.close();
    return scope;
  }

  private static void writeScope(Map<String,Value> scope, String filename) throws IOException {
    FileOutputStream fos = new FileOutputStream(filename);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(scope);
    oos.close();
  }

  private static String readFile(String filename) throws IOException {
    FileInputStream fstream = new FileInputStream(filename);
    String s = new String(fstream.readAllBytes());
    fstream.close();
    return s;
  }

  public static void handleError(String filename, Map<String,Value> standardLibraryScope, Map<String,Value> scope) throws IOException {
    for (Map.Entry<String,Value> pair : standardLibraryScope.entrySet()) {
      scope.remove(pair.getKey());
    }
    String debugFileName = filename + ".debug";
    writeScope(scope,debugFileName);
    System.out.println("Debug file saved to: " + debugFileName);
  }

  public static Map<String,Value> generateStandardLibraryScope() {
    Map<String,Value> standardLibraryScope = new HashMap<>();
    standardLibraryScope.put("null", new NullValue());
    standardLibraryScope.put("true", new Boolean(true));
    standardLibraryScope.put("false", new Boolean(false));
    standardLibraryScope.put("print",StandardLibrary.print);
    standardLibraryScope.put("println",StandardLibrary.println);
    standardLibraryScope.put("eq",StandardLibrary.eq);
    standardLibraryScope.put("and",StandardLibrary.and);
    standardLibraryScope.put("or",StandardLibrary.or);
    standardLibraryScope.put("not",StandardLibrary.not);
    standardLibraryScope.put("add",StandardLibrary.add);
    standardLibraryScope.put("sub",StandardLibrary.sub);
    standardLibraryScope.put("mult",StandardLibrary.mult);
    standardLibraryScope.put("quot",StandardLibrary.quot);
    standardLibraryScope.put("mod",StandardLibrary.mod);
    standardLibraryScope.put("Z",StandardLibrary.integers);
    standardLibraryScope.put("N",StandardLibrary.naturals);
    standardLibraryScope.put("in?",StandardLibrary.in);
    standardLibraryScope.put("union",StandardLibrary.union);
    standardLibraryScope.put("inter",StandardLibrary.intersection);
    standardLibraryScope.put("diff",StandardLibrary.difference);
    standardLibraryScope.put("map",StandardLibrary.map);
    standardLibraryScope.put("filter",StandardLibrary.filter);
    standardLibraryScope.put("reduce",StandardLibrary.reduce);
    standardLibraryScope.put("nth",StandardLibrary.nth);
    standardLibraryScope.put("count",StandardLibrary.count);
    standardLibraryScope.put("range",StandardLibrary.range);
    return standardLibraryScope;
  }

  public static void runREPL(String[] args, Map<String,Value> standardLibraryScope) throws IOException, ClassNotFoundException, SyntaxErrorException {
    Map<String,Value> scope = new HashMap<>();
      if (args.length > 1) {
        scope = readScope(args[1]);
      }
      System.out.println("Running tad REPL...");
      new Repl(System.in,System.out,standardLibraryScope,scope).loop();
  }

  public static void runFile(String filename, Map<String,Value> standardLibraryScope) throws IOException, SyntaxErrorException {
    Map<String,Value> scope = new HashMap<>(standardLibraryScope);
      String s = readFile(filename);
      List<String> tokens = Lexer.getTokens(s);
      RootNode root = new Parser().generateAst(tokens);
      try {
        root.evaluate(scope);
      } catch (Exception e) {
        System.err.println(e);
        handleError(filename, standardLibraryScope, scope);
      }
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException, SyntaxErrorException {
    Map<String,Value> standardLibraryScope = generateStandardLibraryScope();
    if (args.length == 0) {
      System.out.println("Usage: tad [OPTION] [SRC]");
      return;
    }
    if (args[0].equals("-r")) {
      runREPL(args,standardLibraryScope);
    } else {
      runFile(args[0], standardLibraryScope);
    }
  }
}