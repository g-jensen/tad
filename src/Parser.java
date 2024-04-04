import java.util.List;

public class Parser {
  // syntax errors

  public RootNode generateAst(List<String> tokens) {
    RootNode root = new RootNode();
    if (tokens == null || tokens.isEmpty()) return root;
    for (String token: tokens) {
      root.add(new ValueNode(new NumberValue(Integer.parseInt(token))));
    }
    return root;
  }
}
