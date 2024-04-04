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

    n = parser.generateAst(List.of());
    assertEquals(new NullValue(), n.evaluate());
  }

  @Test
  public void singleNumberLiteralTokens() {
    RootNode n = parser.generateAst(Lexer.getTokens("1"));
    Value v = new NumberValue(1);
    assertEquals(List.of(new ValueNode(v)), n.getChildren());
    assertEquals(v, n.evaluate());
    
    n = parser.generateAst(Lexer.getTokens("0"));
    v = new NumberValue(0);
    assertEquals(List.of(new ValueNode(v)), n.getChildren());
    assertEquals(v, n.evaluate());
  }

  @Test
  public void multipleNumberLiteralTokens() {
    RootNode n = parser.generateAst(Lexer.getTokens("1 2"));
    ValueNode n0 = new ValueNode(new NumberValue(0));
    ValueNode n1 = new ValueNode(new NumberValue(1));
    ValueNode n2 = new ValueNode(new NumberValue(2));
    ValueNode n3 = new ValueNode(new NumberValue(3));
    assertEquals(List.of(n1, n2), n.getChildren());
    assertEquals(new NumberValue(2), n.evaluate());

    n = parser.generateAst(Lexer.getTokens("0 1 3"));
    assertEquals(List.of(n0, n1, n3), n.getChildren());
    assertEquals(new NumberValue(3), n.evaluate());
  }

  // @Test
  // public void 
}
