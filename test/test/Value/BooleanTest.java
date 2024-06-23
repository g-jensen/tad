import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class BooleanTest {
  @Test
  public void booleanToString() {
    Boolean b = new Boolean(false);
    assertEquals("false", b.toString());
    b = new Boolean(true);
    assertEquals("true", b.toString());
  }

  @Test
  public void booleanEquals() {
    assertNotEquals(null,new Boolean(false));
    Value v = new Boolean(false);
    assertEquals(v,v);
    assertEquals(new Boolean(false), new Boolean(false));
    assertNotEquals(new Boolean(true), new Boolean(false));
    assertNotEquals(new Boolean(false), new Object());
  }
}
