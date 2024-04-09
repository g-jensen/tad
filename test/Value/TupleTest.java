import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TupleTest {
  @Test
  public void contains() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Tuple t = new Tuple(List.of(v0));
    assertTrue(t.contains(v0));
    assertFalse(t.contains(v1));
  }
}
