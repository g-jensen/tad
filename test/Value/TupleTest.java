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

  @Test
  public void backwardsUnionFiniteCollection() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Tuple s = new Tuple(List.of());
    Collection c1 = s.backwardsUnion(new Tuple(List.of(v0)));
    assertTrue(c1.contains(v0));
    assertFalse(c1.contains(v1));
    Collection c2 = c1.backwardsUnion(new Tuple(List.of(v1)));
    assertTrue(c2.contains(v0));
    assertTrue(c2.contains(v1));
  }

  @Test
  public void backwardsUnionInfiniteCollection() {
    Value v0 = new Tuple(List.of(new NumberValue(0)));
    Tuple s = new Tuple(List.of(v0));
    Collection c1 = s.backwardsUnion(StandardLibrary.integers);
    assertTrue(c1.contains(v0));
    assertTrue(c1.contains(new NumberValue(1)));
  }

  @Test
  public void backwardsIntersectionFiniteCollection() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Tuple s = new Tuple(List.of(v0,v1));
    Collection c1 = s.backwardsIntersection(new Tuple(List.of(v0)));
    assertTrue(c1.contains(v0));
    assertFalse(c1.contains(v1));
    Collection c2 = c1.backwardsIntersection(new Tuple(List.of(v1)));
    assertFalse(c2.contains(v0));
    assertFalse(c2.contains(v1));
  }

  @Test
  public void backwardsIntersectionInfiniteCollection() {
    Value v0 = new NumberValue(0);
    Tuple s = new Tuple(List.of(v0));
    Collection c1 = s.backwardsIntersection(StandardLibrary.integers);
    assertTrue(c1.contains(v0));
    assertFalse(c1.contains(new NumberValue(1)));
  }

  @Test
  public void backwardsDifferenceFiniteCollection() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Tuple s = new Tuple(List.of(v0,v1));
    Collection c1 = new Tuple(List.of(v0)).backwardsDifference(s);
    assertFalse(c1.contains(v0));
    assertTrue(c1.contains(v1));
  }

  @Test
  public void backwardsDifferenceInfiniteCollection() {
    Value v0 = new NumberValue(0);
    Tuple s = new Tuple(List.of(v0));
    Collection c1 = StandardLibrary.integers.backwardsDifference(s);
    assertFalse(c1.contains(v0));
    assertFalse(c1.contains(new NumberValue(1)));
  }
}
