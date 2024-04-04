public class Difference implements Collection {
  private Collection c1;
  private Collection c2;

  public Difference(Collection c1, Collection c2) {
    this.c1 = c1;
    this.c2 = c2;
  }

  public Collection copy() {
    return new Difference(c1, c2);
  }

  public boolean contains(Value v) {
    return c1.contains(v) && !c2.contains(v);
  }
}
