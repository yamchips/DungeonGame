package player;

import java.util.List;
import treasure.Itreasure;

/**
 * This interface represents a player.
 * @author Fan Wu
 *
 */
public interface Iplayer {

  /**
   * Get the current state of the player.
   * @return The state of the player
   */
  State getState();
  
  /**
   * Set the player's state to dead when the player is killed by the monster.
   */
  void setStateDead();
  
  /**
   * The player picks up an arrow.
   */
  void pickupArrow();
  
  /**
   * The player shoots an arrow and the total number of arrow minus 1.
   */
  void shootArrow();
  
  /**
   * Get the total number of arrows the player has.
   * @return the total number of arrows.
   */
  int getArrowNum();
  
  /**
   * Set the x coordinate of the player.
   * @param i the x coordinate.
   */
  void setX(int i);
  
  /**
   * Set the y coordinate of the player.
   * @param i the y coordinate.
   */
  void setY(int i);
  
  /**
   * Get the x coordinate of the player.
   * @return the x coordinate.
   */
  int getX();
  
  /**
   * Get the y coordinate of the player.
   * @return the y coordinate.
   */
  int getY();
  
  /**
   * Get the name of the player.
   * @return the name of the player.
   */
  String getName();
  
  /**
   * Get the treasure collected by the player.
   * @return a list of treasure.
   */
  List<String> getTreasure();
  
  /**
   * Set the treasure to the player.
   * @param treasure the treasure picked up by the player.
   */
  void setTreasure(List<Itreasure> treasure);

}
