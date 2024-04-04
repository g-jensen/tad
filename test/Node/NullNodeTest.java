import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NullNodeTest {
  @Test
  public void evaluateNullNode() {
    assertEquals(new NullValue(), new NullNode().evaluate());
  }
}
