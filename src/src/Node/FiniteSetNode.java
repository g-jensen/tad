import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FiniteSetNode extends ListNode {
  private List<Node> nodes;

  public FiniteSetNode() {
    this.nodes = new ArrayList<>();
  }

  public void addNode(Node n) {
    nodes.add(n);
  }

  public Value evaluate(Map<String,Value> scope) {
    java.util.Set<Value> values = new HashSet<>();
    for (Node n : nodes) {
      values.add(n.evaluate(scope));
    }
    return new FiniteSet(values);
  }
  
}
