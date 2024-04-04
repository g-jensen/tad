public interface Node {
  @Override
  public boolean equals(Object obj);
  
  public Value evaluate(); 
}
