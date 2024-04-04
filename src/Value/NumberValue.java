public class NumberValue implements Value {
  private int number;
  
  public NumberValue(int number) {
    this.number = number;
  }

  public int getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return Integer.toString(number);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof NumberValue)) return false;
    NumberValue v = (NumberValue)obj;
    return this.number == v.number;
  }
}
