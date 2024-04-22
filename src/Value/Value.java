import java.io.Serializable;

public interface Value extends Serializable {
  @Override
  public String toString();
  
  @Override
  public boolean equals(Object obj);
}
