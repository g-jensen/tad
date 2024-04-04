import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class NullValueTest {
  @Test
  public void nullToString() {
    assertEquals("null", new NullValue().toString());
  }

  @Test
  public void nullEquals() {
    assertNotEquals(null, new NullValue());
    assertEquals(new NullValue(), new NullValue());
    assertNotEquals(new Object(), new NullValue());
  }
}
