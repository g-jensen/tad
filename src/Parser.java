import java.util.ArrayList;
import java.util.List;

public class Parser {
  // syntax errors

  public RootNode generateAst(List<String> tokens) {
    RootNode root = new RootNode();
    if (tokens == null || tokens.isEmpty()) return root;
    for (int i = 0; i < tokens.size();) {
      ParsedResult pr = parseExpression(tokens,i);
      root.add(pr.node);
      i += pr.tokens.size();
    }
    return root;
  }

  private boolean isInteger(String token) {
    try {
      Integer.parseInt(token);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private ParsedResult parseExpression(List<String> tokens, int tokensIndex) {
    String token = tokens.get(tokensIndex);
    Node n = null;
    List<String> parsedTokens = null;
    if (isInteger(token)) {
      n = new ValueNode(new NumberValue(Integer.parseInt(token)));
      parsedTokens = List.of(token);
    } else {
      n = new ValueNode(new FiniteSet(java.util.Set.of()));
      parsedTokens = List.of(token,tokens.get(tokensIndex+1));
    }
    return new ParsedResult(n, parsedTokens);
  }
}