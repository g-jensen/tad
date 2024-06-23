import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TupleNode extends ListNode {
  private List<Node> nodes;

  public TupleNode() {
    this.nodes = new ArrayList<>();
  }

  public void addNode(Node n) {
    nodes.add(n);
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public Value evaluate(Map<String,Value> scope) {
    List<Value> values = new ArrayList<>();
    for (Node n : nodes) {
      values.add(n.evaluate(scope));
    }
    return new Tuple(values);
  }
  
}
