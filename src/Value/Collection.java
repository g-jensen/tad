
public interface Collection extends Value {
  public boolean contains(Value v);

  public static Collection union(Set c1, Set c2) {
    return new UnionSet(c1, c2);
  }
  public static Collection intersection(Set c1, Set c2) {
    return new IntersectionSet(c1, c2);
  }
  public static Collection difference(Set c1, Set c2) {
    return new DifferenceSet(c1, c2);
  }
}
