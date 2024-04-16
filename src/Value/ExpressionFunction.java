import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionFunction implements Function {
  private RootNode root;
  private List<String> parameterNames;

  public ExpressionFunction(List<String> parameterNames, RootNode root) {
    this.root = root;
    this.parameterNames = parameterNames;
  }

  public Value call(List<Value> parameters, Map<String, Value> scope) {
    Map<String, Value> newScope = new HashMap<>(scope);
    for (int i = 0; i < parameterNames.size(); i++) {
      newScope.put(parameterNames.get(i),parameters.get(i));
    }
    return root.evaluate(newScope);
  }
  
}
