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
  public static void main(String[] args) throws IOException, ClassNotFoundException {
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

    
    if (args[0].equals("-r")) {
      Map<String,Value> scope = new HashMap<>();
      if (args.length > 1) {
        FileInputStream fis = new FileInputStream(args[1]);
        ObjectInputStream ois = new ObjectInputStream(fis);
        scope = (Map<String,Value>)ois.readObject();
      }
      System.out.println("Running tad REPL...");
      new Repl(System.in,System.out,standardLibraryScope,scope).loop();
    } else {
      Map<String,Value> scope = new HashMap<>(standardLibraryScope);
      FileInputStream fstream = new FileInputStream(args[0]);
      byte[] bytes = fstream.readAllBytes();
      fstream.close();
      String s = new String(bytes);
      Parser p = new Parser();

      List<String> tokens = Lexer.getTokens(s);
      try {
        p.generateAst(tokens).evaluate(scope);
      } catch (Exception e) {
        System.err.println(e);
        for (Map.Entry<String,Value> pair : standardLibraryScope.entrySet()) {
          scope.remove(pair.getKey());
        }
        String debugFileName = args[0] + ".debug";
        FileOutputStream fos = new FileOutputStream(debugFileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(scope);
        oos.close();
        System.out.println("Debug file saved to: " + debugFileName);
      }
    }

  }
}