import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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
  public void nullOrEmptyTokens() throws SyntaxErrorException {
    Node n = parser.generateAst(null);
    assertEquals(new NullValue(), n.evaluate(Map.of()));

    n = parser.generateAst(List.of());
    assertEquals(new NullValue(), n.evaluate(Map.of()));
  }

  @Test
  public void singleNumberLiteral() throws SyntaxErrorException{
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
  public void multipleNumberLiteral() throws SyntaxErrorException{
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
  public void ignoreNewlines() throws SyntaxErrorException{
    RootNode n = parser.generateAst(List.of("1","\n","2"));
    ValueNode n1 = new ValueNode(new NumberValue(1));
    ValueNode n2 = new ValueNode(new NumberValue(2));
    assertEquals(List.of(n1, n2), n.getChildren());
    assertEquals(new NumberValue(2), n.evaluate(Map.of()));
  }

  @Test
  public void emptySetLiteral() throws SyntaxErrorException{
    RootNode n = parser.generateAst(List.of("{","}"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new FiniteSet(java.util.Set.of()), n.evaluate(Map.of()));
  }

  @Test
  public void populatedSetLiteralOfNumbers() throws SyntaxErrorException{
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
  public void populatedSetLiteralWithNest() throws SyntaxErrorException{
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
  public void emptyTupleLiteral() throws SyntaxErrorException{
    RootNode n = parser.generateAst(List.of("(",")"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new Tuple(List.of()), n.evaluate(Map.of()));
  }

  @Test
  public void populatedTupleLiteralOfNumbers() throws SyntaxErrorException{
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
  public void populatedTupleLiteralWithNest() throws SyntaxErrorException{
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
  public void dereferenceUndefinedSymbol() throws SyntaxErrorException{
    RootNode n = parser.generateAst(List.of("a"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new NullValue(), n.evaluate(Map.of()));
  }

  @Test
  public void dereferenceDefinedSymbol() throws SyntaxErrorException{
    RootNode n = parser.generateAst(List.of("b"));
    assertEquals(1, n.getChildren().size());
    assertEquals(new NumberValue(1), n.evaluate(Map.of("b",new NumberValue(1))));
  }

  @Test
  public void defineSymbol() throws SyntaxErrorException {
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

  @Test
  public void defineEmptyMapFunction() throws SyntaxErrorException{
    Map<String,Value> scope = new HashMap<>();
    RootNode n = parser.generateAst((List.of("a",":","{","}")));
    assertEquals(1, n.getChildren().size());
    Function f = (Function)n.evaluate(scope);
    assertEquals(new NullValue(), f.call(List.of(),scope));
    assertEquals(f, scope.get("a"));
  }

  @Test
  public void defineSingleEntryMapFunction() throws SyntaxErrorException{
    Map<String,Value> scope = new HashMap<>();
    RootNode n = parser.generateAst((List.of("a",":","{","1",":","2","}")));
    assertEquals(1, n.getChildren().size());
    Function f = (Function)n.evaluate(scope);
    assertEquals(new NumberValue(2), f.call(List.of(new NumberValue(1)),scope));
    assertEquals(f, scope.get("a"));
  }

  @Test
  public void defineMultiEntryMapFunction() throws SyntaxErrorException{
    Map<String,Value> scope = new HashMap<>(Map.of("c",new NumberValue(2)));
    RootNode n = parser.generateAst((List.of("b",":","{","3",":","9",",","c",":","4","}")));
    assertEquals(1, n.getChildren().size());
    Function f = (Function)n.evaluate(scope);
    assertEquals(new NumberValue(9), f.call(List.of(new NumberValue(3)),scope));
    assertEquals(new NumberValue(4), f.call(List.of(new NumberValue(2)),scope));
    assertEquals(f, scope.get("b"));
  }

  @Test
  public void defineEmptyExpressionFunction() throws SyntaxErrorException{
    Map<String,Value> scope = new HashMap<>();
    RootNode n = parser.generateAst((List.of("a",":","(",")","{","}")));
    assertEquals(1, n.getChildren().size());
    Function f = (Function)n.evaluate(scope);
    assertEquals(new NullValue(), f.call(List.of(),scope));
    assertEquals(f, scope.get("a"));
  }

  @Test
  public void defineConstantExpressionFunction() throws SyntaxErrorException{
    Map<String,Value> scope = new HashMap<>();
    RootNode n = parser.generateAst((List.of("a",":","(",")","{","1","}")));
    assertEquals(1, n.getChildren().size());
    Function f = (Function)n.evaluate(scope);
    assertEquals(new NumberValue(1), f.call(List.of(),scope));
    assertEquals(f, scope.get("a"));
  }

  @Test
  public void defineConstantExpressionFunctionWithScope() throws SyntaxErrorException{
    Map<String,Value> scope = new HashMap<>(Map.of("b",new NumberValue(5)));
    RootNode n = parser.generateAst((List.of("a",":","(",")","{","b","}")));
    assertEquals(1, n.getChildren().size());
    Function f = (Function)n.evaluate(scope);
    assertEquals(new NumberValue(5), f.call(List.of(),scope));
    assertEquals(f, scope.get("a"));
  }

  @Test
  public void defineExpressionFunctionWithParameter() throws SyntaxErrorException{
    Map<String,Value> scope = new HashMap<>();
    RootNode n = parser.generateAst((List.of("a",":","(","b",")","{","b","}")));
    assertEquals(1, n.getChildren().size());
    Function f = (Function)n.evaluate(scope);
    assertEquals(new NumberValue(3), f.call(List.of(new NumberValue(3)),scope));
    assertEquals(f, scope.get("a"));
  }

  @Test
  public void functionCallNoParams() throws SyntaxErrorException{
    RootNode root = new RootNode();
    root.add(new ValueNode(new NumberValue(5)));
    Map<String,Value> scope = new HashMap<>(Map.of("greg", new ExpressionFunction(List.of(),root)));
    RootNode n = parser.generateAst((List.of("greg","(",")")));
    assertEquals(1, n.getChildren().size());
    assertEquals(new NumberValue(5), n.evaluate(scope));
  }

  @Test
  public void functionCallWithParams() throws SyntaxErrorException{
    RootNode root = new RootNode();
    root.add(new SymbolNode("a"));
    Map<String,Value> scope = new HashMap<>(Map.of("greg", new ExpressionFunction(List.of("a"),root)));
    RootNode n = parser.generateAst((List.of("greg","(","3",")")));
    assertEquals(1, n.getChildren().size());
    assertEquals(new NumberValue(3), n.evaluate(scope));
  }

  @Test
  public void syntaxErrorHandling() {
    Parser p = new Parser();
    SyntaxErrorException thrown = assertThrows(SyntaxErrorException.class, ()->{
      p.generateAst(List.of("("));
    });
    assertEquals("Syntax Error 0 \"(\"", thrown.getMessage());

    thrown = assertThrows(SyntaxErrorException.class, ()->{
      p.generateAst(List.of("a","\n","("));
    });
    assertEquals("Syntax Error 2 \"a \n (\"", thrown.getMessage());
  }
}
