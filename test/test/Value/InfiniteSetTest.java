import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class InfiniteSetTest {
  @Test
  public void backwardsUnionFiniteCollection() {
    Value vn1 = new NumberValue(-1);
    Value v1 = new NumberValue(1);
    Tuple s = new Tuple(List.of(vn1));
    Collection c1 = StandardLibrary.naturals.backwardsUnion(s);
    assertTrue(c1.contains(vn1));
    assertTrue(c1.contains(v1));
  }

  @Test
  public void backwardsIntersectionFiniteCollection() {
    Value vn1 = new NumberValue(-1);
    Value v1 = new NumberValue(1);
    Tuple s = new Tuple(List.of(v1));
    Collection c1 = StandardLibrary.naturals.backwardsIntersection(s);
    assertTrue(c1.contains(v1));
    Collection c2 = c1.backwardsIntersection(new Tuple(List.of(vn1)));
    assertFalse(c2.contains(vn1));
    assertFalse(c2.contains(v1));
  }

  @Test
  public void backwardsDifferenceFiniteCollection() {
    Value vn1 = new NumberValue(-1);
    Value v1 = new NumberValue(1);
    Collection c1 = StandardLibrary.naturals.backwardsDifference(StandardLibrary.integers);
    assertTrue(c1.contains(vn1));
    assertFalse(c1.contains(v1));
  }
}
