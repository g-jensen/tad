import java.util.List;

public abstract class Collection {
  protected List<Collection> unions;
  protected List<Collection> intersections;
  protected List<Collection> differences;
  public abstract Collection copy();
  public abstract boolean contains(Value v);
  
  public Collection union(Collection c) {
    Collection copy = this.copy();
    copy.unions.add(c);
    return copy;
  }

  public Collection intersection(Collection c) {
    return null;
  }

  public Collection difference(Collection c) {
    return null;
  }
}
