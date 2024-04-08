import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class FiniteSet implements Set {
  private java.util.Set<Value> elements;

  public FiniteSet(java.util.Set<Value> set) {
    elements = new HashSet<>(set);
  }

  public boolean contains(Value v) {
    return elements.contains(v);
  }

  public java.util.Set<Value> getElements() {
    return elements;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {return true;}
    if (!(obj instanceof FiniteSet)) {return false;}
    FiniteSet fs = (FiniteSet)obj;
    ArrayList<Value> a1 = new ArrayList<>(this.elements);
    ArrayList<Value> a2 = new ArrayList<>(fs.elements);
    return a1.containsAll(a2) && a2.containsAll(a1);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Iterator<Value> it = elements.iterator();
    sb.append("{");
    while (it.hasNext()) {
      sb.append(it.next().toString());
      if (it.hasNext()) {
        sb.append(", ");
      }
    }
    sb.append("}");
    return sb.toString();
  }
}
