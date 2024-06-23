import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class SymbolNodeTest {
  @Test
  public void evaluate() {
    Node n = new SymbolNode("a");
    assertEquals(new NullValue(), n.evaluate(Map.of()));

    n = new SymbolNode("a");
    assertEquals(new NumberValue(1), n.evaluate(Map.of("a",new NumberValue(1))));
  }
}
