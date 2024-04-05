import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class IntersectionSetTest {
  @Test
  public void contains() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Collection us1 = new IntersectionSet(new FiniteSet(Set.of()),
                                         new FiniteSet(Set.of(v0)));
    assertFalse(us1.contains(v0));

    Collection us2 = new IntersectionSet(new FiniteSet(Set.of(v0, v1)),
                                         new FiniteSet(Set.of(v1)));
    assertTrue(us2.contains(v1));
    assertFalse(us2.contains(v0));
  }
}
