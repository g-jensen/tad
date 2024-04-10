import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class FiniteSetNodeTest {
  @Test
  public void evaluate() {
    FiniteSetNode n = new FiniteSetNode();
    assertEquals(new FiniteSet(java.util.Set.of()), n.evaluate(Map.of()));

    n = new FiniteSetNode();
    n.addNode(new ValueNode(new NumberValue(1)));
    assertEquals(new FiniteSet(java.util.Set.of(new NumberValue(1))), n.evaluate(Map.of()));

    n = new FiniteSetNode();
    n.addNode(new ValueNode(new NumberValue(1)));
    n.addNode(new ValueNode(new NumberValue(2)));
    assertEquals(new FiniteSet(java.util.Set.of(new NumberValue(1), new NumberValue(2))),
                 n.evaluate(Map.of()));
  }
}
