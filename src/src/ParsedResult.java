import java.util.List;

public class ParsedResult {
  public Node node;
  public List<String> tokens;
  public ParsedResult(Node node, List<String> tokens) {
    this.node = node;
    this.tokens = tokens;
  }
}