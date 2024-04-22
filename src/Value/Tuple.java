import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

  public java.util.Collection<Value> getCollection() {
    return values;
  }

  public FiniteCollection map(Function f, Map<String,Value> scope) {
    List<Value> newSet = new ArrayList<>();
    for (Value v : values) {
      newSet.add(f.call(List.of(v),scope));
    }
    return new Tuple(newSet);
  }

  public Collection backwardsUnion(Collection c) {
    return c.finiteCollectionUnion(this);
  }
  public Collection backwardsIntersection(Collection c) {
    return c.finiteCollectionIntersection(this);
  }
  public Collection backwardsDifference(Collection c) {
    return c.finiteCollectionDifference(this);
  }

  public Collection finiteCollectionUnion(FiniteCollection c) {
    List<Value> l = new ArrayList<>(getCollection());
    l.addAll(c.getCollection()); 
    return new Tuple(l);
  }

  public Collection finiteCollectionIntersection(FiniteCollection c) {
    List<Value> l = new ArrayList<>(getCollection());
    return new Tuple(l.stream().filter(e->c.contains(e)).collect(Collectors.toList()));
  }

  public Collection finiteCollectionDifference(FiniteCollection c) {
    List<Value> l = new ArrayList<>(getCollection());
    return new Tuple(l.stream().filter(e->!c.contains(e)).collect(Collectors.toList()));
  }

}
