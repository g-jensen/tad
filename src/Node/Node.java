public abstract class Node {
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Node)) return false;
    Node n = (Node)obj;
    return n.evaluate().equals(this.evaluate());
  }
  
  public abstract Value evaluate(); 
}
