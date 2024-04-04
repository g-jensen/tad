import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class NumberValueTest {
  @Test
  public void numberToString() {
    Value v = new NumberValue(0);
    assertEquals("0",v.toString());

    v = new NumberValue(5);
    assertEquals("5",v.toString());

    v = new NumberValue(-10);
    assertEquals("-10",v.toString());
  }

  @Test
  public void numberEquals() {
    assertNotEquals(null,new NumberValue(0));
    Value v = new NumberValue(0);
    assertEquals(v,v);
    assertEquals(new NumberValue(0), new NumberValue(0));
    assertNotEquals(new NumberValue(5), new NumberValue(0));
    assertNotEquals(new NumberValue(5), new Object());
  }
}