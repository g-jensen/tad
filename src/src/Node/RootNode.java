import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  public Value evaluate(Map<String,Value> scope) {
    Value lastValue = new NullValue();
    for (Node n : children) {
      lastValue = n.evaluate(scope);
    }
    return lastValue;
  }
}
