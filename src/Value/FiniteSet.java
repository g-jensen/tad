import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FiniteSet implements FiniteCollection {
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

  public java.util.Collection<Value> getCollection() {
    return elements;
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
    java.util.Set<Value> l = new HashSet<>(getCollection());
    l.addAll(c.getCollection()); 
    return new FiniteSet(l);
  }

  public Collection finiteCollectionIntersection(FiniteCollection c) {
    java.util.Set<Value> l = new HashSet<>(getCollection());
    return new FiniteSet(l.stream().filter(e->c.contains(e)).collect(Collectors.toSet()));
  }

  public Collection finiteCollectionDifference(FiniteCollection c) {
    java.util.Set<Value> l = new HashSet<>(getCollection());
    return new FiniteSet(l.stream().filter(e->!c.contains(e)).collect(Collectors.toSet()));
  }

  public FiniteCollection map(Function f, Map<String,Value> scope) {
    java.util.Set<Value> newSet = new HashSet<>();
    for (Value v : elements) {
      newSet.add(f.call(List.of(v),scope));
    }
    return new FiniteSet(newSet);
  }
}
