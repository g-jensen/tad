import java.util.List;
import java.util.Map;

public interface Function extends Value {
  public Value call(List<Value> parameters, Map<String,Value> scope);
}
