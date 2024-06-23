import java.util.Map;

public abstract class ListNode extends Node {
  public abstract Value evaluate(Map<String,Value> scope);
  public abstract void addNode(Node n);
}
