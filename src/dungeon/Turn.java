package dungeon;

/**
 * This class represents the turn of the game.
 * @author Fan Wu
 *
 */
public enum Turn {
  A("A"), B("B");
  
  private final String disp;
  
  /**
   * Constructor.
   * @param disp the string of this turn.
   */
  private Turn(String disp) {
    this.disp = disp;
  }
  
  @Override
  public String toString() {
    return disp;
  }
}