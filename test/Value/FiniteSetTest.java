import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class FiniteSetTest {
  @Test
  public void contains() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    FiniteSet fs = new FiniteSet(Set.of(v0));
    assertTrue(fs.contains(v0));
    assertFalse(fs.contains(v1));
  }
}
