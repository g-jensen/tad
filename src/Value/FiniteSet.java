import java.util.HashSet;

public class FiniteSet implements Set {
  private java.util.Set<Value> elements;

  public FiniteSet(java.util.Set<Value> set) {
    elements = new HashSet<>(set);
  }

  public boolean contains(Value v) {
    return elements.contains(v);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {return true;}
    if (!(obj instanceof FiniteSet)) {return false;}
    FiniteSet fs = (FiniteSet)obj;
    return this.elements.equals(fs.elements);
  }
}
