import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class FunctionNodeTest {
  @Test
  public void evaluate() {
    RootNode root = new RootNode();
    root.add(new ValueNode(new NumberValue(5)));
    Function function = new MapFunction();
    FunctionNode fnode = new FunctionNode("greg1", function);
    Map<String,Value> scope = new HashMap<>();
    assertEquals(function, fnode.evaluate(scope));
    assertEquals(function, scope.get("greg1"));
  }
}
