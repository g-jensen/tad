import java.util.Map;

public class SymbolNode extends Node {
  private String symbol;

  public SymbolNode(String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }

  public Value evaluate(Map<String, Value> scope) {
    return scope.getOrDefault(symbol, new NullValue());
  }
  
}
