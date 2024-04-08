public class ValueNode extends Node {
  private Value value;

  public ValueNode(Value value) {
    this.value = value;
  }

  public Value evaluate() {
    return value;
  }
}
