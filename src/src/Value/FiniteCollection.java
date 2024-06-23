import java.util.Map;

public interface FiniteCollection extends Collection {
  public java.util.Collection<Value> getCollection(); 
  public FiniteCollection map(Function f, Map<String,Value> scope);
  public FiniteCollection filter(Function f, Map<String,Value> scope);
}
