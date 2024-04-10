import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class ValueNodeTest {
  @Test
  public void evaluateNode() {
    Node n = new ValueNode(new NumberValue(0));
    assertEquals(new NumberValue(0), n.evaluate(Map.of()));

    n = new ValueNode(new NullValue());
    assertEquals(new NullValue(), n.evaluate(Map.of()));
  }
}
