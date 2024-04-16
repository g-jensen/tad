import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class StandardLibrary {
  public static PrintStream pstream = new PrintStream(System.out);

  public static Function print = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      pstream.print(parameters.get(0));
      return new NullValue();
    }
  };

  public static Function println = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      pstream.println(parameters.get(0));
      return new NullValue();
    }
  };

  public static Function eq = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      return new Boolean(parameters.get(0).equals(parameters.get(1)));
    }
  };

  public static Function and = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Boolean b1 = (Boolean)parameters.get(0);
      Boolean b2 = (Boolean)parameters.get(1);
      return new Boolean(b1.getValue() && b2.getValue());
    }
  };

  public static Function or = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Boolean b1 = (Boolean)parameters.get(0);
      Boolean b2 = (Boolean)parameters.get(1);
      return new Boolean(b1.getValue() || b2.getValue());
    }
  };

  public static Function not = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Boolean b1 = (Boolean)parameters.get(0);
      return new Boolean(!b1.getValue());
    }
  };

  public static Function add = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      int acc = 0;
      for (Value v: parameters) {
        acc += ((NumberValue)v).getNumber();
      }
      return new NumberValue(acc);
    }
  };
}
