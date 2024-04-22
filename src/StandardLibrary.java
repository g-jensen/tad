import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StandardLibrary {
  public static PrintStream pstream = new PrintStream(System.out);

  public static InfiniteSet integers = new InfiniteSet() {
    public boolean contains(Value v) {
      return (v instanceof NumberValue);
    }
  };

  public static InfiniteSet naturals = new InfiniteSet() {
    public boolean contains(Value v) {
      NumberValue nv = (NumberValue)v;
      return nv.getNumber() >= 0;
    }
  };

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

  public static Function sub = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      int acc = ((NumberValue)parameters.get(0)).getNumber();
      for (int i = 1; i < parameters.size(); i++) {
        acc -= ((NumberValue)parameters.get(i)).getNumber();
      }
      return new NumberValue(acc);
    }
  };

  public static Function mult = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      int acc = 1;
      for (Value v: parameters) {
        acc *= ((NumberValue)v).getNumber();
      }
      return new NumberValue(acc);
    }
  };

  public static Function quot = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      int acc = 1;
      for (Value v: parameters) {
        acc /= ((NumberValue)v).getNumber();
      }
      return new NumberValue(acc);
    }
  };

  public static Function mod = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      int n0 = ((NumberValue)parameters.get(0)).getNumber();
      int n1 = ((NumberValue)parameters.get(1)).getNumber();
      return new NumberValue(n0 % n1);
    }
  };

  public static Function in = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Collection c = (Collection)parameters.get(1);
      return new Boolean(c.contains(parameters.get(0)));
    }
  };


  public static Function union = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Collection c1 = (Collection)parameters.get(0);
      Collection c2 = (Collection)parameters.get(1);
      return c2.backwardsUnion(c1);
    }
  };

  public static Function intersection = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Collection c1 = (Collection)parameters.get(0);
      Collection c2 = (Collection)parameters.get(1);
      return c2.backwardsIntersection(c1);
    }
  };

  public static Function difference = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Collection c1 = (Collection)parameters.get(0);
      Collection c2 = (Collection)parameters.get(1);
      return c2.backwardsDifference(c1);
    }
  };

  public static Function map = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Function f = (Function)parameters.get(0);
      FiniteCollection c = (FiniteCollection)parameters.get(1);
      return c.map(f,scope);
    }
  };

  public static Function filter = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Function f = (Function)parameters.get(0);
      FiniteCollection c = (FiniteCollection)parameters.get(1);
      return c.filter(f,scope);
    }
  };

  public static Function reduce = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      Function f = (Function)parameters.get(0);
      FiniteCollection c = (FiniteCollection)parameters.get(1);
      return c.getCollection().stream().reduce((v0,v1)->f.call(List.of(v0,v1),scope)).get();
    }
  };

  public static Function nth = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      int n = ((NumberValue)parameters.get(0)).getNumber();
      FiniteCollection c = (FiniteCollection)parameters.get(1);
      return (Value)(c.getCollection().toArray()[n]);
    }
  };

  public static Function count = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      FiniteCollection c = (FiniteCollection)parameters.get(0);
      return new NumberValue(c.getCollection().size());
    }
  };

  public static Function range = new Function() {
    public Value call(List<Value> parameters, Map<String, Value> scope) {
      int n0 = ((NumberValue)parameters.get(0)).getNumber();
      int n1 = ((NumberValue)parameters.get(1)).getNumber();
      List<Value> values = new ArrayList<>();
      for (int i = n0; i < n1; i++) {
        values.add(new NumberValue(i));
      }
      Tuple t = new Tuple(values);
      return t;
    }
  };
}
