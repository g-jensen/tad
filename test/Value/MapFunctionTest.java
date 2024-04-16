import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MapFunctionTest {
  @Test
  public void callEmptyMap() {
    MapFunction f = new MapFunction();
    assertEquals(new NullValue(), f.call(List.of(),Map.of()));
  }

  @Test
  public void callPopulatedMap() {
    MapFunction f = new MapFunction();
    f.put(new ValueNode(new NumberValue(1)), new ValueNode(new NumberValue(0)));
    assertEquals(new NumberValue(0), f.call(List.of(new NumberValue(1)),Map.of()));
    assertEquals(new NullValue(), f.call(List.of(new NumberValue(0)),Map.of()));
  }

  @Test
  public void callMapWithNullValue() {
    MapFunction f = new MapFunction();
    f.put(new ValueNode(new NullValue()), new ValueNode(new NumberValue(5)));
    assertEquals(new NumberValue(5), f.call(List.of(new NullValue()),Map.of()));
  }
}
