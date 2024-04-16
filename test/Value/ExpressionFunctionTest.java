import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ExpressionFunctionTest {
  @Test
  public void callEmptyFunction() {
    ExpressionFunction f = new ExpressionFunction(List.of(),new RootNode());
    assertEquals(new NullValue(), f.call(List.of(),Map.of()));
  }

  @Test
  public void callConstantFunction() {
    RootNode root = new RootNode();
    root.add(new ValueNode(new NumberValue(1)));
    ExpressionFunction f = new ExpressionFunction(List.of(),root);
    assertEquals(new NumberValue(1), f.call(List.of(),Map.of()));
  }

  @Test
  public void callConstantFunctionWithScope() {
    RootNode root = new RootNode();
    Map<String,Value> scope = new HashMap<>(Map.of("a",new NumberValue(5)));
    root.add(new SymbolNode("a"));
    ExpressionFunction f = new ExpressionFunction(List.of(),root);
    assertEquals(new NumberValue(5), f.call(List.of(),scope));
  }

  @Test
  public void callFunctionWithParams() {
    RootNode root = new RootNode();
    root.add(new SymbolNode("a"));
    ExpressionFunction f = new ExpressionFunction(List.of("a"),root);
    assertEquals(new NumberValue(3), f.call(List.of(new NumberValue(3)),Map.of()));
  }

  @Test
  public void callFunctionWithParamsOverridingScope() {
    RootNode root = new RootNode();
    Map<String,Value> scope = new HashMap<>(Map.of("a",new NumberValue(5)));
    root.add(new SymbolNode("a"));
    ExpressionFunction f = new ExpressionFunction(List.of("a"),root);
    assertEquals(new NumberValue(3), f.call(List.of(new NumberValue(3)),scope));
  }
}
