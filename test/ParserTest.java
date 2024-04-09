import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
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
    RootNode n = parser.generateAst(List.of("1"));
    Value v = new NumberValue(1);
    assertEquals(List.of(new ValueNode(v)), n.getChildren());
    assertEquals(v, n.evaluate());
    
    n = parser.generateAst(List.of("0"));
    v = new NumberValue(0);
    assertEquals(List.of(new ValueNode(v)), n.getChildren());
    assertEquals(v, n.evaluate());
  }

  @Test
  public void multipleNumberLiteralTokens() {
    RootNode n = parser.generateAst(List.of("1","2"));
    ValueNode n0 = new ValueNode(new NumberValue(0));
    ValueNode n1 = new ValueNode(new NumberValue(1));
    ValueNode n2 = new ValueNode(new NumberValue(2));
    ValueNode n3 = new ValueNode(new NumberValue(3));
    assertEquals(List.of(n1, n2), n.getChildren());
    assertEquals(new NumberValue(2), n.evaluate());

    n = parser.generateAst(List.of("0","1","3"));
    assertEquals(List.of(n0, n1, n3), n.getChildren());
    assertEquals(new NumberValue(3), n.evaluate());
  }

  @Test
  public void emptySetLiteralTokens() {
    RootNode n = parser.generateAst(List.of("{","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of()), n.evaluate());
  }

  @Test
  public void populatedSetLiteralOfNumbersTokens() {
    RootNode n = parser.generateAst(List.of("{","1","}"));
    NumberValue n1 = new NumberValue(1);
    NumberValue n2 = new NumberValue(2);
    NumberValue n3 = new NumberValue(3);
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(n1)), n.evaluate());

    n = parser.generateAst(List.of("{","1",",","2","}"));
    assertEquals(1, n.getChildren().size()); 
    assertEquals(new FiniteSet(java.util.Set.of(n1, n2)),n.evaluate());

    n = parser.generateAst(List.of("{","1",",","2",",","3","}"));
    assertEquals(1, n.getChildren().size()); 
    assertEquals(new FiniteSet(java.util.Set.of(n1, n2, n3)),n.evaluate());

    n = parser.generateAst(List.of("{","1",",","2",",","1","}"));
    assertEquals(1, n.getChildren().size()); 
    assertEquals(new FiniteSet(java.util.Set.of(n1, n2)),n.evaluate());
  }

  @Test
  public void populatedSetLiteralWithNestedSetTokens() {
    RootNode n = parser.generateAst(List.of("{","{","}","}"));
    NumberValue n1 = new NumberValue(1);
    NumberValue n2 = new NumberValue(2);
    FiniteSet s1 = new FiniteSet(java.util.Set.of(n1));
    FiniteSet s2 = new FiniteSet(java.util.Set.of(n2));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(new FiniteSet(java.util.Set.of()))), n.evaluate());
  
    n = parser.generateAst(List.of("{","{","1","}","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(s1)), n.evaluate());

    n = parser.generateAst(List.of("{","{","1","}",",","2","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(s1, n2)), n.evaluate());

    n = parser.generateAst(List.of("{","2",",","{","1","}","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(s1, n2)), n.evaluate());

    n = parser.generateAst(List.of("{","{","1","}","{","2","}","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(s1, s2)), n.evaluate());
  }

  @Test
  public void emptyTupleLiteralTokens() {
    RootNode n = parser.generateAst(List.of("(",")"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of()), n.evaluate());
  }
}
