public class IntersectionSet extends InfiniteSet {
  private Collection c1;
  private Collection c2;

  public IntersectionSet(Collection c1, Collection c2) {
    this.c1 = c1;
    this.c2 = c2;
  }

  public boolean contains(Value v) {
    return c1.contains(v) && c2.contains(v);
  }
}
