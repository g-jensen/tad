import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class AssignmentNodeTest {
  @Test
  public void evaluate() {
    Map<String,Value> scope = new HashMap<>();
    Node n = new AssignmentNode("c", new ValueNode(new NumberValue(0)));
    assertEquals(new NumberValue(0), n.evaluate(scope));
    assertEquals(new NumberValue(0), scope.get("c"));
  }
}
