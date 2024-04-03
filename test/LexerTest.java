import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class LexerTest {
  @Test
  public void getsTokensOfNothing() {
    List<String> tokens = Lexer.getTokens("");
    assertEquals(0, tokens.size());
  }

  @Test
  public void getsTokensOfNumberLiteral() {
    List<String> tokens = Lexer.getTokens("3");
    assertEquals(1, tokens.size());
    assertEquals("3", tokens.get(0));

    tokens = Lexer.getTokens("10");
    assertEquals(1, tokens.size());
    assertEquals("10", tokens.get(0));
  }

  @Test
  public void getsTokensOfNumberLiteralWithWhitespace() {
    List<String> tokens = Lexer.getTokens(" 5 ");
    assertEquals(1, tokens.size());
    assertEquals("5", tokens.get(0));
  }

  @Test
  public void getsTokensOfNegativeNumberLiteral() {
    List<String> tokens = Lexer.getTokens("-4");
    assertEquals(1, tokens.size());
    assertEquals("-4", tokens.get(0));
  }

  @Test
  public void getsTokensOfAssigningSymbolToNumberLiteral() {
    List<String> tokens = Lexer.getTokens("A := 2");
    assertEquals(List.of("A",":=","2"), tokens);

    tokens = Lexer.getTokens("B:=5");
    assertEquals(List.of("B",":=","5"), tokens);

    tokens = Lexer.getTokens("B :=5");
    assertEquals(List.of("B",":=","5"), tokens);

    tokens = Lexer.getTokens("B:= 5");
    assertEquals(List.of("B",":=","5"), tokens);

    tokens = Lexer.getTokens("B:=-5");
    assertEquals(List.of("B",":=","-5"), tokens);
  }

  @Test
  public void getsTokensOfAssignmentName() {
    List<String> tokens = Lexer.getTokens("A1 := 2");
    assertEquals(List.of("A1",":=","2"), tokens);

    tokens = Lexer.getTokens("A1:= 2");
    assertEquals(List.of("A1",":=","2"), tokens);

    tokens = Lexer.getTokens("A-1:= 2");
    assertEquals(List.of("A-1",":=","2"), tokens);

    tokens = Lexer.getTokens("A_1:= 2");
    assertEquals(List.of("A_1",":=","2"), tokens);

    tokens = Lexer.getTokens("1A := 2");
    assertEquals(List.of("1A",":=","2"), tokens);

    tokens = Lexer.getTokens("A? := 2");
    assertEquals(List.of("A?",":=","2"), tokens);
  }

  @Test
  public void getsTokensOfExplicitSet() {
    List<String> tokens = Lexer.getTokens("{}");
    assertEquals(List.of("{","}"), tokens);

    tokens = Lexer.getTokens("{1}");
    assertEquals(List.of("{","1","}"), tokens);

    tokens = Lexer.getTokens("{1,2}");
    assertEquals(List.of("{","1",",","2","}"), tokens);

    tokens = Lexer.getTokens("{1,2,-3}");
    assertEquals(List.of("{","1",",","2",",","-3","}"), tokens);

    tokens = Lexer.getTokens("A :={1,2,-3}");
    assertEquals(List.of("A",":=","{","1",",","2",",","-3","}"), tokens);
  }

  @Test
  public void getsTokensOfFilterSet() {
    List<String> tokens = Lexer.getTokens("A{}");
    assertEquals(List.of("A","{","}"), tokens);

    tokens = Lexer.getTokens("Abc{f}");
    assertEquals(List.of("Abc","{","f","}"), tokens);

    tokens = Lexer.getTokens("A{even?}");
    assertEquals(List.of("A","{","even?","}"), tokens);

    tokens = Lexer.getTokens("ABC-?{even?}");
    assertEquals(List.of("ABC-?","{","even?","}"), tokens);

    tokens = Lexer.getTokens("{1,2,3}{even?}");
    assertEquals(List.of("{","1",",","2",",","3","}","{","even?","}"), tokens);
  }

  @Test
  public void getsTokensOfTuple() {
    List<String> tokens = Lexer.getTokens("()");
    assertEquals(List.of("(",")"), tokens);

    tokens = Lexer.getTokens("(1)");
    assertEquals(List.of("(","1",")"), tokens);

    tokens = Lexer.getTokens("(1,2)");
    assertEquals(List.of("(","1",",","2",")"), tokens);

    tokens = Lexer.getTokens("A :=(1,2)");
    assertEquals(List.of("A",":=","(","1",",","2",")"), tokens);

    tokens = Lexer.getTokens("((1,2),2)");
    assertEquals(List.of("(","(","1",",","2",")",",","2",")"), tokens);

    tokens = Lexer.getTokens("(1,2){even?}");
    assertEquals(List.of("(","1",",","2",")","{","even?","}"), tokens);
  }

  @Test
  public void getsTokensOfFunctionMapNotation() {
    List<String> tokens = Lexer.getTokens("A:{}");
    assertEquals(List.of("A",":","{","}"), tokens);

    tokens = Lexer.getTokens("not: {true: false}");
    assertEquals(List.of("not",":","{","true",":","false","}"), tokens);

    tokens = Lexer.getTokens("not: {true: false, false: true}");
    assertEquals(List.of("not",":","{","true",":","false",",","false",":","true","}"), tokens);
  
    tokens = Lexer.getTokens("my_map: {add(5,1): false, 9: 44}");
    assertEquals(List.of("my_map",":","{","add","(","5",",","1",")",":","false",",","9",":","44","}"), tokens);
  }

  @Test
  public void getsTokensOfFunctionExpressionNotation() {
    List<String> tokens = Lexer.getTokens("A:(){}");
    assertEquals(List.of("A",":","(",")","{","}"), tokens);

    tokens = Lexer.getTokens("A: (n) {n}");
    assertEquals(List.of("A",":","(","n",")","{","n","}"), tokens);

    tokens = Lexer.getTokens("add: (n1,n2) {n1 + n2}");
    assertEquals(List.of("add",":","(","n1",",","n2",")","{","n1","+","n2","}"), tokens);

    tokens = Lexer.getTokens("A: (n) {\n   n+1\n}");
    assertEquals(List.of("A",":","(","n",")","{","n","+","1","}"), tokens);
  }

  @Test
  public void getsTokensOfFunctionCall() {
    List<String> tokens = Lexer.getTokens("A()");
    assertEquals(List.of("A","(",")"), tokens);

    tokens = Lexer.getTokens("A(B)");
    assertEquals(List.of("A","(","B",")"), tokens);

    tokens = Lexer.getTokens("A(B,C)");
    assertEquals(List.of("A","(","B",",","C",")"), tokens);

    tokens = Lexer.getTokens("add-two-numbers(4,3)");
    assertEquals(List.of("add-two-numbers","(","4",",","3",")"), tokens);
  }

  @Test
  public void getsTokensOfCond() {
    List<String> tokens = Lexer.getTokens("cond()");
    assertEquals(List.of("cond","(",")"), tokens);

    tokens = Lexer.getTokens("cond(true: 1)");
    assertEquals(List.of("cond","(","true",":","1",")"), tokens);

    tokens = Lexer.getTokens("cond(even?(2): 1)");
    assertEquals(List.of("cond","(","even?","(","2",")",":","1",")"), tokens);

    tokens = Lexer.getTokens("cond(even?(2): 1, false: 2)");
    assertEquals(List.of("cond","(","even?","(","2",")",":","1",",","false",":","2",")"), tokens);
  }
}
