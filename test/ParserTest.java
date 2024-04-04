import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {
  private Parser parser;
  
  @Before
  public void init() {
    parser = new Parser();
  }

  @Test
  public void nullOrEmptyTokens() {
    Node n = parser.generateAst(null);
    assertEquals(new NullValue(), n.evaluate());

    n = parser.generateAst(List.of(""));
    assertEquals(new NullValue(), n.evaluate());
  }
}
