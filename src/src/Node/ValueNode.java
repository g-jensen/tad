import java.util.Map;

public class ValueNode extends Node {
  private Value value;

  public ValueNode(Value value) {
    this.value = value;
  }

  public Value evaluate(Map<String,Value> scope) {
    return value;
  }
}
