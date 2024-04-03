import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Parser {
  private static List<Character> separatorChars = new ArrayList<>(List.of(','));
  private static List<Character> operatorChars = new ArrayList<>(List.of(':','='));
  private static List<Character> valueChars = new ArrayList<>(List.of('{','('));

  public static List<String> getTokens(String str) {
    List<String> tokens = new ArrayList<>();
    for (int i = 0; i < str.length();) {
      Character c = str.charAt(i);
      String parsed = new String();
      if (isValidSymbolChar(c)) {
        parsed = parse(str,i,Parser::isValidSymbolChar);
      } else if (isSeparatorChar(c)) {
        parsed = c.toString();
      } else if (isOperatorChar(c)) {
        parsed = parse(str,i,Parser::isOperatorChar);
      } else if (isValueChar(c)) {
        parsed = c.toString();
      } else {
        parsed = c.toString();
      }
      if (!parsed.isBlank()) {
        tokens.add(parsed);
      }
      i += parsed.length();
    }
    return tokens;
  }

  private static String parse(String str, int idx, Predicate<Character> f) {
    StringBuilder sb = new StringBuilder();
    for (; idx < str.length(); idx++) {
      Character c = str.charAt(idx);
      if (!f.test(c)) break;
      sb.append(c);
    }
    return sb.toString();
  }

  private static boolean isSeparatorChar(Character c) {
    return separatorChars.contains(c);
  }

  private static boolean isOperatorChar(Character c) {
    return operatorChars.contains(c);
  }

  private static boolean isValueChar(Character c) {
    return valueChars.contains(c);
  }

  private static boolean isPartOfNumber(char c) {
    return  c == '-' || Character.isDigit(c);
  }

  private static boolean isValidSymbolChar(Character c) {
    return Character.isAlphabetic(c) || isPartOfNumber(c) || c == '_' || c == '?';
  }
}
