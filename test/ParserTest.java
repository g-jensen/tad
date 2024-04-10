import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    assertEquals(new NullValue(), n.evaluate(Map.of()));

    n = parser.generateAst(List.of());
    assertEquals(new NullValue(), n.evaluate(Map.of()));
  }

  @Test
  public void singleNumberLiteralTokens() {
    RootNode n = parser.generateAst(List.of("1"));
    Value v = new NumberValue(1);
    assertEquals(List.of(new ValueNode(v)), n.getChildren());
    assertEquals(v, n.evaluate(Map.of()));
    
    n = parser.generateAst(List.of("0"));
    v = new NumberValue(0);
    assertEquals(List.of(new ValueNode(v)), n.getChildren());
    assertEquals(v, n.evaluate(Map.of()));
  }

  @Test
  public void multipleNumberLiteralTokens() {
    RootNode n = parser.generateAst(List.of("1","2"));
    ValueNode n0 = new ValueNode(new NumberValue(0));
    ValueNode n1 = new ValueNode(new NumberValue(1));
    ValueNode n2 = new ValueNode(new NumberValue(2));
    ValueNode n3 = new ValueNode(new NumberValue(3));
    assertEquals(List.of(n1, n2), n.getChildren());
    assertEquals(new NumberValue(2), n.evaluate(Map.of()));

    n = parser.generateAst(List.of("0","1","3"));
    assertEquals(List.of(n0, n1, n3), n.getChildren());
    assertEquals(new NumberValue(3), n.evaluate(Map.of()));
  }

  @Test
  public void emptySetLiteralTokens() {
    RootNode n = parser.generateAst(List.of("{","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of()), n.evaluate(Map.of()));
  }

  @Test
  public void populatedSetLiteralOfNumbersTokens() {
    RootNode n = parser.generateAst(List.of("{","1","}"));
    NumberValue n1 = new NumberValue(1);
    NumberValue n2 = new NumberValue(2);
    NumberValue n3 = new NumberValue(3);
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(n1)), n.evaluate(Map.of()));

    n = parser.generateAst(List.of("{","1",",","2","}"));
    assertEquals(1, n.getChildren().size()); 
    assertEquals(new FiniteSet(java.util.Set.of(n1, n2)),n.evaluate(Map.of()));

    n = parser.generateAst(List.of("{","1",",","2",",","3","}"));
    assertEquals(1, n.getChildren().size()); 
    assertEquals(new FiniteSet(java.util.Set.of(n1, n2, n3)),n.evaluate(Map.of()));

    n = parser.generateAst(List.of("{","1",",","2",",","1","}"));
    assertEquals(1, n.getChildren().size()); 
    assertEquals(new FiniteSet(java.util.Set.of(n1, n2)),n.evaluate(Map.of()));
  }

  @Test
  public void populatedSetLiteralWithNestTokens() {
    RootNode n = parser.generateAst(List.of("{","{","}","}"));
    NumberValue n1 = new NumberValue(1);
    NumberValue n2 = new NumberValue(2);
    FiniteSet s1 = new FiniteSet(java.util.Set.of(n1));
    FiniteSet s2 = new FiniteSet(java.util.Set.of(n2));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(new FiniteSet(java.util.Set.of()))), n.evaluate(Map.of()));
  
    n = parser.generateAst(List.of("{","{","1","}","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(s1)), n.evaluate(Map.of()));

    n = parser.generateAst(List.of("{","{","1","}",",","2","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(s1, n2)), n.evaluate(Map.of()));

    n = parser.generateAst(List.of("{","2",",","{","1","}","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(s1, n2)), n.evaluate(Map.of()));

    n = parser.generateAst(List.of("{","{","1","}","{","2","}","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of(s1, s2)), n.evaluate(Map.of()));
  }

  @Test
  public void emptyTupleLiteralTokens() {
    RootNode n = parser.generateAst(List.of("(",")"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of()), n.evaluate(Map.of()));
  }

  @Test
  public void populatedTupleLiteralOfNumbersTokens() {
    RootNode n = parser.generateAst(List.of("(","1",")"));
    NumberValue n1 = new NumberValue(1);
    NumberValue n2 = new NumberValue(2);
    NumberValue n3 = new NumberValue(3);
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of(n1)), n.evaluate(Map.of()));

    n = parser.generateAst(List.of("(","1",",","2",")"));
    assertEquals(1, n.getChildren().size()); 
    assertEquals(new Tuple(List.of(n1, n2)),n.evaluate(Map.of()));

    n = parser.generateAst(List.of("(","1",",","2",",","3",")"));
    assertEquals(1, n.getChildren().size()); 
    assertEquals(new Tuple(List.of(n1, n2, n3)),n.evaluate(Map.of()));

    n = parser.generateAst(List.of("(","1",",","2",",","1",")"));
    assertEquals(1, n.getChildren().size()); 
    assertEquals(new Tuple(List.of(n1, n2)),n.evaluate(Map.of()));
  }

  @Test
  public void populatedTupleLiteralWithNestTokens() {
    RootNode n = parser.generateAst(List.of("(","(",")",")"));
    NumberValue n1 = new NumberValue(1);
    NumberValue n2 = new NumberValue(2);
    Tuple t1 = new Tuple(List.of(n1));
    Tuple t2 = new Tuple(List.of(n2));
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of(new Tuple(List.of()))), n.evaluate(Map.of()));
  
    n = parser.generateAst(List.of("(","(","1",")",")"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of(t1)), n.evaluate(Map.of()));

    n = parser.generateAst(List.of("(","(","1",")",",","2",")"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of(t1, n2)), n.evaluate(Map.of()));

    n = parser.generateAst(List.of("(","2",",","(","1",")",")"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of(t1, n2)), n.evaluate(Map.of()));

    n = parser.generateAst(List.of("(","(","1",")","(","2",")",")"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of(t1, t2)), n.evaluate(Map.of()));
  }

  @Test
  public void dereferenceUndefinedSymbolTokens() {
    RootNode n = parser.generateAst(List.of("a"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new NullValue(), n.evaluate(Map.of()));
  }

  @Test
  public void dereferenceDefinedSymbolTokens() {
    RootNode n = parser.generateAst(List.of("b"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new NumberValue(1), n.evaluate(Map.of("b",new NumberValue(1))));
  }

  @Test
  public void defineSymbolTokens() {
    Map<String,Value> scope = new HashMap<>();
    RootNode n = parser.generateAst(List.of("b",":=","4"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new NumberValue(4), n.evaluate(scope));
    assertEquals(new NumberValue(4), scope.get("b"));

    n = parser.generateAst(List.of("b",":=","(","1",")"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of(new NumberValue(1))), n.evaluate(scope));
    assertEquals(new Tuple(List.of(new NumberValue(1))), scope.get("b"));
  }
}
