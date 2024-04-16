public class Boolean implements Value {
  private boolean value;

  public Boolean(boolean value) {
    this.value = value;
  }

  public boolean getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value ? "true" : "false";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Boolean)) return false;
    Boolean v = (Boolean)obj;
    return value == v.value;
  }
}
