import java.util.Map;

public class FunctionNode extends Node {
  private String name;
  private Function function;

  public FunctionNode(String name, Function function) {
    this.name = name;
    this.function = function;
  }

  public Value evaluate(Map<String, Value> scope) {
    scope.put(name,function);
    return function;
  }
}
