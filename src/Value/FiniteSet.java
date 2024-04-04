import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FiniteSet extends Collection {
  private Set<Value> elements;

  public FiniteSet(Set<Value> set) {
    elements = new HashSet<>(set);
    unions = new ArrayList<>();
    intersections = new ArrayList<>();
    differences = new ArrayList<>();
  }

  public Collection copy() {
    FiniteSet fs = new FiniteSet(Set.of());
    fs.elements = new HashSet<>(this.elements);
    fs.unions = new ArrayList<>(this.unions);
    fs.intersections = new ArrayList<>(this.intersections);
    fs.differences = new ArrayList<>(this.differences);
    return fs;
  }

  public boolean contains(Value v) {
    return elements.contains(v) || unions.stream().anyMatch(c->c.contains(v));
  }
}
