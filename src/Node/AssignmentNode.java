import java.util.Map;

public class AssignmentNode extends Node {
  private String symbolName;
  private Node valueNode;

  public AssignmentNode(String symbolName, Node valueNode) {
    this.symbolName = symbolName;
    this.valueNode = valueNode;
  }

  public Value evaluate(Map<String, Value> scope) {
    Value v = valueNode.evaluate(scope);
    scope.put(symbolName,v);
    return v;
  }
  
}
