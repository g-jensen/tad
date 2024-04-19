import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Tuple implements FiniteCollection {
  private List<Value> values;

  public Tuple(List<Value> values) {
    this.values = values;
  }

  public boolean contains(Value v) {
    return values.contains(v);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {return true;}
    if (!(obj instanceof Tuple)) {return false;}
    Tuple t = (Tuple)obj;
    return this.values.containsAll(t.values) && t.values.containsAll(this.values);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Iterator<Value> it = values.iterator();
    sb.append("(");
    while (it.hasNext()) {
      sb.append(it.next().toString());
      if (it.hasNext()) {
        sb.append(", ");
      }
    }
    sb.append(")");
    return sb.toString();
  }

  public Collection<Value> getCollection() {
    return values;
  }

}
