import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class FiniteSetTest {
  @Test
  public void containsWithoutOperations() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    FiniteSet fs = new FiniteSet(Set.of(v0));
    assertTrue(fs.contains(v0));
    assertFalse(fs.contains(v1));
  }

  @Test
  public void containsWithUnion() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Value v2 = new NumberValue(2);
    Collection c1 = new FiniteSet(Set.of()).union(new FiniteSet(Set.of(v1)));
    assertTrue(c1.contains(v1));
    assertFalse(c1.contains(v0));
    assertFalse(c1.contains(v2));
    Collection c2 = c1.union(new FiniteSet(Set.of(v0,v2)));
    assertTrue(c2.contains(v0));
    assertTrue(c2.contains(v2));
  }
}
