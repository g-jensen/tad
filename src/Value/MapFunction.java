import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapFunction implements Function {
  private List<AbstractMap.SimpleImmutableEntry<Node,Node>> map;

  public MapFunction() {
    this.map = new ArrayList<>();
  }

  public void put(Node key, Node value) {
    map.add(new AbstractMap.SimpleImmutableEntry<Node,Node>(key,value));
  }

  public Value call(List<Value> parameters, Map<String,Value> scope) {
    if (parameters.size() > 0) {
      return get(parameters.get(0),scope);
    } else {
      return get(new NullValue(),scope);
    }
  }

  private Value get(Value value, Map<String,Value> scope) {
    for (AbstractMap.SimpleImmutableEntry<Node,Node> v: map) {
      if (v.getKey().evaluate(scope).equals(value)) {
        return v.getValue().evaluate(scope);
      }
    }
    return new NullValue();
  }
}
