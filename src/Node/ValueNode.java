public class ValueNode implements Node {
  private Value value;

  public ValueNode(Value value) {
    this.value = value;
  }

  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof ValueNode)) return false;
    ValueNode n = (ValueNode)obj;
    return n.value.equals(this.value);
  }

  public Value evaluate() {
    return value;
  }
}
