import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class RootNodeTest {
  @Test
  public void evaluateNode() {
    RootNode n = new RootNode();
    Value v = new NullValue();
    assertEquals(v, n.evaluate(Map.of()));

    n = new RootNode();
    v = new NumberValue(1);
    n.add(new ValueNode(v));
    assertEquals(v, n.evaluate(Map.of()));

    n = new RootNode();
    v = new NumberValue(1);
    Value v1 = new NumberValue(2);
    n.add(new ValueNode(v));
    n.add(new ValueNode(v1));
    assertEquals(v1, n.evaluate(Map.of()));
  }
}
