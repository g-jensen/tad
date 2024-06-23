import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class FunctionCallNodeTest {
  @Test
  public void evaluateFunctionNoParams() {
    RootNode root = new RootNode();
    root.add(new ValueNode(new NumberValue(5)));
    Map<String, Value> scope = new HashMap<>(Map.of("hi",new ExpressionFunction(List.of(), root)));
    FunctionCallNode f = new FunctionCallNode("hi", new TupleNode());
    assertEquals(new NumberValue(5), f.evaluate(scope));
  }

  @Test
  public void evaluateFunctionWithParams() {
    RootNode root = new RootNode();
    root.add(new SymbolNode("b"));
    Map<String, Value> scope = new HashMap<>(Map.of("hi",new ExpressionFunction(List.of("b"), root)));
    TupleNode t = new TupleNode();
    t.addNode(new ValueNode(new NumberValue(99)));
    FunctionCallNode f = new FunctionCallNode("hi", t);
    assertEquals(new NumberValue(99), f.evaluate(scope));
  }
}
