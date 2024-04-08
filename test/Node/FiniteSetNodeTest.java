import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FiniteSetNodeTest {
  @Test
  public void evaluate() {
    FiniteSetNode n = new FiniteSetNode();
    assertEquals(new FiniteSet(java.util.Set.of()), n.evaluate());

    n = new FiniteSetNode();
    n.addNode(new ValueNode(new NumberValue(1)));
    assertEquals(new FiniteSet(java.util.Set.of(new NumberValue(1))), n.evaluate());

    n = new FiniteSetNode();
    n.addNode(new ValueNode(new NumberValue(1)));
    n.addNode(new ValueNode(new NumberValue(2)));
    assertEquals(new FiniteSet(java.util.Set.of(new NumberValue(1), new NumberValue(2))),
                 n.evaluate());
  }
}
