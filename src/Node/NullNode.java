public class NullNode implements Node {
  public Value evaluate() {
    return new NullValue();
  }
}
