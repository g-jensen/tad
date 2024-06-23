public abstract class InfiniteSet implements Collection {
  public Collection backwardsUnion(Collection c) {
    return new UnionSet(c,this);
  }
  public Collection finiteCollectionUnion(FiniteCollection c) {return backwardsUnion(c);}

  public Collection backwardsIntersection(Collection c) {
    return new IntersectionSet(c,this);
  }
  public Collection finiteCollectionIntersection(FiniteCollection c) {return backwardsIntersection(c);}

  public Collection backwardsDifference(Collection c) {
    return new DifferenceSet(c,this);
  }
  public Collection finiteCollectionDifference(FiniteCollection c) {return backwardsDifference(c);}
}
