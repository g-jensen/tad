import java.util.HashSet;
import java.util.Set;

public class FiniteSet implements Collection {
  private Set<Value> elements;

  public FiniteSet(Set<Value> set) {
    elements = new HashSet<>(set);
  }

  public boolean contains(Value v) {
    return elements.contains(v);
  }
}
