
public interface Collection extends Value {
  public boolean contains(Value v);

  public Collection backwardsUnion(Collection c);
  public Collection finiteCollectionUnion(FiniteCollection c);
  
  public Collection backwardsIntersection(Collection c);
  public Collection finiteCollectionIntersection(FiniteCollection c);
  
  public Collection backwardsDifference(Collection c);
  public Collection finiteCollectionDifference(FiniteCollection c);
}
