package dungeon;

/**
 * This class represents the type of cave.
 * @author Fan Wu
 *
 */
public enum Type {
  tunnel("tunnel"), cave("cave");
  
  private final String disp;
  
  /**
   * Constructor.
   * @param disp the string of this type.
   */
  private Type(String disp) {
    this.disp = disp;
  }
  
  @Override
  public String toString() {
    return disp;
  }
}
