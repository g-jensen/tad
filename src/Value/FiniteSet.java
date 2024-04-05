import java.util.HashSet;

public class FiniteSet implements Set {
  private java.util.Set<Value> elements;

  public FiniteSet(java.util.Set<Value> set) {
    elements = new HashSet<>(set);
  }

  public boolean contains(Value v) {
    return elements.contains(v);
  }
}
