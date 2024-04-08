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
    if (isInteger(token)) {
      return parseInteger(tokens, tokensIndex);
    } else if (token.equals("{")) {
      return parseSet(tokens, tokensIndex);
    } else {
      ParsedResult pr = parseExpression(tokens, tokensIndex+1);
      List<String> parsedTokens = new ArrayList<>(pr.tokens);
      parsedTokens.add(0,token);
      pr.tokens = parsedTokens;
      return pr;
    }
  }

  private ParsedResult parseInteger(List<String> tokens, int tokensIndex) {
    String token = tokens.get(tokensIndex);
    Node n = new ValueNode(new NumberValue(Integer.parseInt(token)));
    List<String> parsedTokens = List.of(token);
    return new ParsedResult(n, parsedTokens);
  }

  private ParsedResult parseSet(List<String> tokens, int tokensIndex) {
    String token = tokens.get(tokensIndex);
    FiniteSetNode node = new FiniteSetNode();
    List<String> parsedTokens = new ArrayList<>(List.of(token));
    tokensIndex++;
    while (tokensIndex < tokens.size() &&
          !tokens.get(tokensIndex).equals("}")) {
      ParsedResult pr = parseExpression(tokens,tokensIndex);
      parsedTokens.addAll(pr.tokens);
      node.addNode(pr.node);
      tokensIndex += pr.tokens.size();
    }
    parsedTokens.add("}");
    return new ParsedResult(node, parsedTokens);
  }
}