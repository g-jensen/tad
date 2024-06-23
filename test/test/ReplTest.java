import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ReplTest {
  private ByteArrayOutputStream ostream;
  private ByteArrayInputStream istream;

  @Before
  public void init() {
    ostream = new ByteArrayOutputStream();
  }

  private void loadString(String string) {
    istream = new ByteArrayInputStream(string.getBytes());
  }
  
  @Test
  public void quitLoopWithNoScope() throws IOException, SyntaxErrorException {
    loadString("quit\n");
    Repl repl = new Repl(istream, ostream, new HashMap<>(), new HashMap<>());
    repl.loop();
    assertEquals("=> ",ostream.toString());
  }

  @Test
  public void evalLoopWithNoScope() throws IOException, SyntaxErrorException {
    loadString("123\nquit\n");
    Repl repl = new Repl(istream, ostream, new HashMap<>(), new HashMap<>());
    repl.loop();
    assertEquals("=> 123\n=> ",ostream.toString());
  }

  @Test
  public void evalLoopWithBasicScope() throws IOException, SyntaxErrorException {
    Map<String,Value> scope = new HashMap<>(Map.of("a",new NumberValue(1)));
    loadString("a\nquit\n");
    Repl repl = new Repl(istream, ostream, scope, new HashMap<>());
    repl.loop();
    assertEquals("=> 1\n=> ",ostream.toString());
  }

  @Test
  public void evalLoopWithLoadedScope() throws IOException, SyntaxErrorException {
    Map<String,Value> loadedScope = new HashMap<>(Map.of("a",new NumberValue(1)));
    loadString("a\nquit\n");
    Repl repl = new Repl(istream, ostream, new HashMap<>(), loadedScope);
    repl.loop();
    assertEquals("Loaded Debug Scope:\n{a=1}\n=> 1\n=> ",ostream.toString());
  }
}
