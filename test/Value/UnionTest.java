import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class UnionTest {
  @Test
  public void contains() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Collection us1 = new Union(new FiniteSet(Set.of()),
                                  new FiniteSet(Set.of(v0)));
    assertTrue(us1.contains(v0));
    assertFalse(us1.contains(v1));

    Collection us2 = new Union(us1,new FiniteSet(Set.of(v1)));
    assertTrue(us2.contains(v0));
    assertTrue(us2.contains(v1));
  }
}
