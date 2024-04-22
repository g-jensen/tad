import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;

public class Repl {
  private BufferedReader reader;
  private PrintStream pstream;
  private Map<String,Value> scope;
  private Parser parser;
  private Map<String,Value> loadedScope;

  public Repl(InputStream istream, OutputStream ostream, Map<String,Value> scope) {
    this.scope = scope;
    this.loadedScope = null;
    this.reader = new BufferedReader(new InputStreamReader(istream));
    this.pstream = new PrintStream(ostream);
    this.parser = new Parser();
  }

  public Repl(InputStream istream, OutputStream ostream, Map<String,Value> scope, Map<String,Value> loadedScope) {
    this.loadedScope = loadedScope;
    this.scope = scope;
    for (Map.Entry<String,Value> entry: loadedScope.entrySet()) {
      scope.put(entry.getKey(),entry.getValue());
    }
    this.reader = new BufferedReader(new InputStreamReader(istream));
    this.pstream = new PrintStream(ostream);
    this.parser = new Parser();
  }

  private String read() throws IOException {
    pstream.print("=> ");
    return reader.readLine();
  }

  private Value evaluate(String input) {
    return parser.generateAst(Lexer.getTokens(input)).evaluate(scope);
  }

  private void print(Value v) {
    pstream.println(v);
  }

  public void loop() throws IOException {
    if (loadedScope != null) {
      System.out.println("Loaded Debug Scope:");
      System.out.println(loadedScope);
    }
    Value value = evaluate(read());
    while (true) {
      print(value);
      value = evaluate(read());
    }
  }

}
