import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FunctionCallNode extends Node {
  private String functionName;
  private TupleNode params;

  public FunctionCallNode(String functionName, TupleNode params) {
    this.functionName = functionName;
    this.params = params;
  }

  public Value evaluate(Map<String, Value> scope) {
    Function function = (Function)scope.get(functionName);
    List<Value> values = params.getNodes()
                        .stream().map(n->n.evaluate(scope))
                        .collect(Collectors.toList());
    return function.call(values,scope);
  }
  
}
