package player;

/**
 * This class represents the state of a player.
 * @author Fan Wu
 *
 */
public enum State {
  alive("alive"), dead("dead");
  
  private final String disp;
  
  private State(String disp) {
    this.disp = disp;
  }
  
  @Override
  public String toString() {
    return disp;
  }
}
