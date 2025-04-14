package dungeon;

/**
 * This interface contains all methods shared by monsters.
 * @author Fan Wu
 *
 */
public interface Imonster {

  /**
   * Get the injury of this monster.
   * @return The total injury of this monster. If the injury is 2, it is dead.
   */
  int getInjury();
  
  /**
   * Add injury to this monster.
   */
  void addInjury();
}
