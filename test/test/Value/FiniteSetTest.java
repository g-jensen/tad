import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class FiniteSetTest {
  @Test
  public void contains() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Value v2 = new NumberValue(2);
    FiniteSet fs = new FiniteSet(Set.of(v0));
    assertTrue(fs.contains(v0));
    assertFalse(fs.contains(v1));
    fs = new FiniteSet(Set.of(v0,v2));
    assertTrue(fs.contains(v0));
    assertTrue(fs.contains(v2));
    assertFalse(fs.contains(v1));
  }

  @Test
  public void map() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Value v2 = new NumberValue(2);
    MapFunction f = new MapFunction();
    f.put(new ValueNode(new NumberValue(0)),new ValueNode(new NumberValue(1)));
    f.put(new ValueNode(new NumberValue(1)),new ValueNode(new NumberValue(2)));
    FiniteSet fs = new FiniteSet(Set.of(v0,v1));
    FiniteCollection fc = fs.map(f,new HashMap<>());
    assertEquals(fc.getCollection(),Set.of(v1,v2));
  }

  @Test
  public void filter() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    Value v2 = new NumberValue(2);
    MapFunction f = new MapFunction();
    f.put(new ValueNode(new NumberValue(0)),new ValueNode(new Boolean(true)));
    f.put(new ValueNode(new NumberValue(1)),new ValueNode(new Boolean(true)));
    FiniteSet fs = new FiniteSet(Set.of(v0,v1,v2));
    FiniteCollection fc = fs.filter(f,new HashMap<>());
    assertEquals(fc.getCollection(),Set.of(v0,v1));
  }

  @Test
  public void backwardsUnionFiniteCollection() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    FiniteSet s = new FiniteSet(Set.of());
    Collection c1 = s.backwardsUnion(new FiniteSet(Set.of(v0)));
    assertTrue(c1.contains(v0));
    assertFalse(c1.contains(v1));
    Collection c2 = c1.backwardsUnion(new FiniteSet(Set.of(v1)));
    assertTrue(c2.contains(v0));
    assertTrue(c2.contains(v1));
  }

  @Test
  public void backwardsUnionInfiniteCollection() {
    Value v0 = new FiniteSet(Set.of(new NumberValue(0)));
    FiniteSet s = new FiniteSet(Set.of(v0));
    Collection c1 = s.backwardsUnion(StandardLibrary.integers);
    assertTrue(c1.contains(v0));
    assertTrue(c1.contains(new NumberValue(1)));
  }

  @Test
  public void backwardsIntersectionFiniteCollection() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    FiniteSet s = new FiniteSet(Set.of(v0,v1));
    Collection c1 = s.backwardsIntersection(new FiniteSet(Set.of(v0)));
    assertTrue(c1.contains(v0));
    assertFalse(c1.contains(v1));
    Collection c2 = c1.backwardsIntersection(new FiniteSet(Set.of(v1)));
    assertFalse(c2.contains(v0));
    assertFalse(c2.contains(v1));
  }

  @Test
  public void backwardsIntersectionInfiniteCollection() {
    Value v0 = new NumberValue(0);
    FiniteSet s = new FiniteSet(Set.of(v0));
    Collection c1 = s.backwardsIntersection(StandardLibrary.integers);
    assertTrue(c1.contains(v0));
    assertFalse(c1.contains(new NumberValue(1)));
  }

  @Test
  public void backwardsDifferenceFiniteCollection() {
    Value v0 = new NumberValue(0);
    Value v1 = new NumberValue(1);
    FiniteSet s = new FiniteSet(Set.of(v0,v1));
    Collection c1 = new FiniteSet(Set.of(v0)).backwardsDifference(s);
    assertFalse(c1.contains(v0));
    assertTrue(c1.contains(v1));
  }

  @Test
  public void backwardsDifferenceInfiniteCollection() {
    Value v0 = new NumberValue(0);
    FiniteSet s = new FiniteSet(Set.of(v0));
    Collection c1 = StandardLibrary.integers.backwardsDifference(s);
    assertFalse(c1.contains(v0));
    assertFalse(c1.contains(new NumberValue(1)));
  }
}
