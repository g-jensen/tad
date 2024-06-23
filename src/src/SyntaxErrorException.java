import java.util.List;

public class SyntaxErrorException extends Exception {
  private static String concatTokens(List<String> tokens, int start, int end) {
    String out = "";
    start = start < 0 ? 0 : start;
    for (int i = start; i < end && i < tokens.size(); i++) {
      out += tokens.get(i);
      if (i != end-1 && i != tokens.size()-1) {
        out += " ";
      }
    }
    return out;
  }

  public SyntaxErrorException(List<String> tokens, int tokenIndex) {
    super("Syntax Error " + tokenIndex + " \"" + concatTokens(tokens, tokenIndex-2,tokenIndex+3) + "\"");
  }
}
