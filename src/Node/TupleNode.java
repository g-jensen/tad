import java.util.ArrayList;
import java.util.List;

public class TupleNode extends ListNode {
  private List<Node> nodes;

  public TupleNode() {
    this.nodes = new ArrayList<>();
  }

  public void addNode(Node n) {
    nodes.add(n);
  }

  public Value evaluate() {
    List<Value> values = new ArrayList<>();
    for (Node n : nodes) {
      values.add(n.evaluate());
    }
    return new Tuple(values);
  }
  
}
