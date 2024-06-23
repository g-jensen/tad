import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class SyntaxErrorExceptionTest {
  @Test
  public void emptyTokens() {
    SyntaxErrorException e = new SyntaxErrorException(List.of(""), 0);
    assertEquals("Syntax Error 0 \"\"",e.getMessage());
  }

  @Test
  public void twoTokens() {
    List<String> tokens = List.of("a","b");
    SyntaxErrorException e = new SyntaxErrorException(tokens, 0);
    assertEquals("Syntax Error 0 \"a b\"",e.getMessage());

    e = new SyntaxErrorException(tokens, 1);
    assertEquals("Syntax Error 1 \"a b\"",e.getMessage());
  }

  @Test
  public void threeTokens() {
    List<String> tokens = List.of("a","b","c");
    SyntaxErrorException e = new SyntaxErrorException(tokens, 0);
    assertEquals("Syntax Error 0 \"a b c\"",e.getMessage());

    e = new SyntaxErrorException(tokens, 1);
    assertEquals("Syntax Error 1 \"a b c\"",e.getMessage());

    e = new SyntaxErrorException(tokens, 2);
    assertEquals("Syntax Error 2 \"a b c\"",e.getMessage());
  }

  @Test
  public void fourTokens() {
    List<String> tokens = List.of("a","b","c","d");
    SyntaxErrorException e = new SyntaxErrorException(tokens, 0);
    assertEquals("Syntax Error 0 \"a b c\"",e.getMessage());

    e = new SyntaxErrorException(tokens, 1);
    assertEquals("Syntax Error 1 \"a b c d\"",e.getMessage());

    e = new SyntaxErrorException(tokens, 2);
    assertEquals("Syntax Error 2 \"a b c d\"",e.getMessage());

    e = new SyntaxErrorException(tokens, 3);
    assertEquals("Syntax Error 3 \"b c d\"",e.getMessage());
  }
}
