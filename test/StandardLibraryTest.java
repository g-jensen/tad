import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class StandardLibraryTest {
  private ByteArrayOutputStream ostream = new ByteArrayOutputStream();
  
  @Before
  public void init() {
    ostream = new ByteArrayOutputStream();
    StandardLibrary.pstream = new PrintStream(ostream);
  }

  @Test
  public void print() {
    Value v = StandardLibrary.print.call(List.of(new NumberValue(4)),Map.of());
    assertEquals(new NullValue(), v);
    assertArrayEquals(new byte[]{'4'},ostream.toByteArray());
    init();
    v = StandardLibrary.print.call(List.of(new NumberValue(10)),Map.of());
    assertArrayEquals(new byte[]{'1','0'},ostream.toByteArray());
    assertEquals(new NullValue(), v);
  }

  @Test
  public void println() {
    Value v = StandardLibrary.println.call(List.of(new NumberValue(4)),Map.of());
    assertArrayEquals(new byte[]{'4','\n'},ostream.toByteArray());
    assertEquals(new NullValue(), v);
    init();
    v = StandardLibrary.println.call(List.of(new NumberValue(10)),Map.of());
    assertArrayEquals(new byte[]{'1','0','\n'},ostream.toByteArray());
    assertEquals(new NullValue(), v);
  }

  @Test
  public void eq() {
    Value v = StandardLibrary.eq.call(List.of(new NumberValue(4),new NumberValue(4)),Map.of());
    assertEquals(new Boolean(true), v);
    v = StandardLibrary.eq.call(List.of(new NumberValue(4),new NumberValue(5)),Map.of());
    assertEquals(new Boolean(false), v);
  }

  @Test
  public void and() {
    Value v = StandardLibrary.and.call(List.of(new Boolean(true),new Boolean(true)),Map.of());
    assertEquals(new Boolean(true), v);
    v = StandardLibrary.and.call(List.of(new Boolean(false),new Boolean(true)),Map.of());
    assertEquals(new Boolean(false), v);
    v = StandardLibrary.and.call(List.of(new Boolean(true),new Boolean(false)),Map.of());
    assertEquals(new Boolean(false), v);
    v = StandardLibrary.and.call(List.of(new Boolean(false),new Boolean(false)),Map.of());
    assertEquals(new Boolean(false), v); 
  }

  @Test
  public void or() {
    Value v = StandardLibrary.or.call(List.of(new Boolean(true),new Boolean(true)),Map.of());
    assertEquals(new Boolean(true), v);
    v = StandardLibrary.or.call(List.of(new Boolean(false),new Boolean(true)),Map.of());
    assertEquals(new Boolean(true), v);
    v = StandardLibrary.or.call(List.of(new Boolean(true),new Boolean(false)),Map.of());
    assertEquals(new Boolean(true), v);
    v = StandardLibrary.or.call(List.of(new Boolean(false),new Boolean(false)),Map.of());
    assertEquals(new Boolean(false), v); 
  }

  @Test
  public void not() {
    Value v = StandardLibrary.not.call(List.of(new Boolean(true)),Map.of());
    assertEquals(new Boolean(false), v);
    v = StandardLibrary.not.call(List.of(new Boolean(false)),Map.of());
    assertEquals(new Boolean(true), v);
  }

  @Test
  public void add() {
    Value v = StandardLibrary.add.call(List.of(),Map.of());
    assertEquals(new NumberValue(0), v);
    v = StandardLibrary.add.call(List.of(new NumberValue(1)),Map.of());
    assertEquals(new NumberValue(1), v);
    v = StandardLibrary.add.call(List.of(new NumberValue(1), new NumberValue(2)),Map.of());
    assertEquals(new NumberValue(3), v);
    v = StandardLibrary.add.call(List.of(new NumberValue(9), new NumberValue(10),new NumberValue(11)),Map.of());
    assertEquals(new NumberValue(30), v);
  }
}
