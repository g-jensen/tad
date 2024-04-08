import java.util.ArrayList;
import java.util.List;

public class RootNode extends Node {
  private List<Node> children;

  public RootNode() {
    children = new ArrayList<>();
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
