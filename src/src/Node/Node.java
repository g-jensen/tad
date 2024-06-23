import java.io.Serializable;
import java.util.Map;

public abstract class Node implements Serializable {
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Node)) return false;
    Node n = (Node)obj;
    return n.evaluate(Map.of()).equals(this.evaluate(Map.of()));
  }
  
  public abstract Value evaluate(Map<String,Value> scope); 
}
