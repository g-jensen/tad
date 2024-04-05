import java.util.ArrayList;
import java.util.List;

public class RootNode implements Node {
  private List<Node> children;

  public RootNode() {
    children = new ArrayList<>();
  }

  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof RootNode)) return false;
    RootNode n = (RootNode)obj;
    return n.children.equals(this.children);
  }

  public List<Node> getChildren() {
    return children;
  }

  public void add(Node n) {
    children.add(n);
  }

  public Value evaluate() {
    Value lastValue = new NullValue();
    for (Node n : children) {
      lastValue = n.evaluate();
    }
    return lastValue;
  }
}