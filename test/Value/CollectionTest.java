import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CollectionTest {
  @Test
  public void union() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Collection us1 = Collection.union(new FiniteSet(java.util.Set.of()),
                                      new FiniteSet(java.util.Set.of(v0)));
    assertTrue(us1.contains(v0));
    assertFalse(us1.contains(v1));

    Collection us2 = Collection.union((Set)us1,new FiniteSet(java.util.Set.of(v1)));
    assertTrue(us2.contains(v0));
    assertTrue(us2.contains(v1));
  }

  @Test
  public void intersection() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Collection us1 = Collection.intersection(new FiniteSet(java.util.Set.of()),
                                             new FiniteSet(java.util.Set.of(v0)));
    assertFalse(us1.contains(v0));

    Collection us2 = Collection.intersection(new FiniteSet(java.util.Set.of(v0, v1)),
                                             new FiniteSet(java.util.Set.of(v1)));
    assertTrue(us2.contains(v1));
    assertFalse(us2.contains(v0));
  }

  @Test
  public void difference() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Collection us1 = Collection.difference(new FiniteSet(java.util.Set.of(v0)),
                                           new FiniteSet(java.util.Set.of()));
    assertTrue(us1.contains(v0));
    assertFalse(us1.contains(v1));

    Collection us2 = Collection.difference(new FiniteSet(java.util.Set.of(v0, v1)),
                                           new FiniteSet(java.util.Set.of(v1)));
    assertTrue(us2.contains(v0));
    assertFalse(us2.contains(v1));
  }
}
