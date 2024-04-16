import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {
  // syntax errors
  private String assignmentOperator = ":=";
  private String startSetToken = "{";
  private String endSetToken = "}";
  private String startTupleToken = "(";
  private String endTupleToken = ")";

  public RootNode generateAst(List<String> tokens) {
    RootNode root = new RootNode();
    if (tokens == null || tokens.isEmpty()) return root;
    for (int i = 0; i < tokens.size();) {
      ParsedResult pr = parseExpression(tokens,i,false);
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

  private boolean isSymbol(String token) {
    for (char c : token.toCharArray()) {
      if (!Lexer.isValidSymbolChar(c)) {
        return false;
      }
    }
    return true;
  }

  private ParsedResult parseExpression(List<String> tokens, int tokensIndex, boolean inMap) {
    String token = tokens.get(tokensIndex);
    if (isInteger(token)) {
      return parseInteger(tokens, tokensIndex);
    } else if (token.equals(startSetToken) || token.equals(startTupleToken)) {
      return parseLiteralCollection(tokens, tokensIndex);
    } else if (isSymbol(token)) {
      return parseSymbol(tokens, tokensIndex,inMap);
    } else {
      ParsedResult pr = parseExpression(tokens, tokensIndex+1,inMap);
      List<String> parsedTokens = new ArrayList<>(pr.tokens);
      parsedTokens.add(0,token);
      pr.tokens = parsedTokens;
      return pr;
    }
  }

  private ParsedResult parseAssignment(List<String> tokens, int tokensIndex) {
    String token = tokens.get(tokensIndex);
    ParsedResult pr = parseExpression(tokens, tokensIndex+2,false);
    List<String> parsedTokens = new ArrayList<>(List.of(token,assignmentOperator));
    parsedTokens.addAll(pr.tokens);
    return new ParsedResult(new AssignmentNode(token, pr.node), parsedTokens);
  }

  private ParsedResult parseFunction(List<String> tokens, int tokensIndex) {
    String token = tokens.get(tokensIndex);
    MapFunction function = new MapFunction();
    List<String> parsedTokens = new ArrayList<>(tokens.subList(tokensIndex, tokensIndex+3));
    tokensIndex += 3;
    while (!tokens.get(tokensIndex).equals(endSetToken)) {
      ParsedResult key = parseExpression(tokens, tokensIndex, true);
      tokensIndex += key.tokens.size();
      ParsedResult value = parseExpression(tokens, tokensIndex, true);
      tokensIndex += value.tokens.size();
      parsedTokens.addAll(key.tokens);
      parsedTokens.addAll(value.tokens);
      function.put(key.node, value.node);
    }
    parsedTokens.add(tokens.get(tokensIndex));
    return new ParsedResult(new FunctionNode(token, function), parsedTokens);
  }

  private ParsedResult parseSymbol(List<String> tokens, int tokensIndex, boolean inMap) {
    String token = tokens.get(tokensIndex);
    if (inMap || tokensIndex+1 >= tokens.size()) {
      return new ParsedResult(new SymbolNode(token), List.of(token));
    }
    String nextToken = tokens.get(tokensIndex+1);
    if (nextToken.equals(assignmentOperator)) {
      return parseAssignment(tokens, tokensIndex);
    } else {
      return parseFunction(tokens, tokensIndex);
    }
  }

  private ParsedResult parseInteger(List<String> tokens, int tokensIndex) {
    String token = tokens.get(tokensIndex);
    Node n = new ValueNode(new NumberValue(Integer.parseInt(token)));
    List<String> parsedTokens = List.of(token);
    return new ParsedResult(n, parsedTokens);
  }

  private ParsedResult parseLiteralCollection(List<String> tokens, int tokensIndex) {
    String token = tokens.get(tokensIndex);
    String endingToken = token.equals(startSetToken) ? endSetToken : endTupleToken;
    ListNode node = token.equals(startSetToken) ? new FiniteSetNode() : new TupleNode();
    List<String> parsedTokens = new ArrayList<>(List.of(token));
    tokensIndex++;
    while (!tokens.get(tokensIndex).equals(endingToken)) {
      ParsedResult pr = parseExpression(tokens,tokensIndex,false);
      parsedTokens.addAll(pr.tokens);
      node.addNode(pr.node);
      tokensIndex += pr.tokens.size();
    }
    parsedTokens.add(endingToken);
    return new ParsedResult(node, parsedTokens);
  }
}