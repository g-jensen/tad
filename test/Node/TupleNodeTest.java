import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TupleNodeTest {
  @Test
  public void evaluate() {
    TupleNode n = new TupleNode();
    assertEquals(new Tuple(List.of()), n.evaluate());

    n = new TupleNode();
    n.addNode(new ValueNode(new NumberValue(1)));
    assertEquals(new Tuple(List.of(new NumberValue(1))), n.evaluate());

    n = new TupleNode();
    n.addNode(new ValueNode(new NumberValue(1)));
    n.addNode(new ValueNode(new NumberValue(2)));
    assertEquals(new Tuple(List.of(new NumberValue(1), new NumberValue(2))),
                 n.evaluate());
  }
}
