package dungeon;

import java.util.List;
import treasure.Itreasure;

/**
 * This interface represents a node.
 * @author Fan Wu
 *
 */
public interface Inode {

  /**
   * Add an arrow to the node.
   * @param num The number of arrow
   */
  void setArrow(int num);
  
  /**
   * Get the number of arrow in this node.
   * @return the number of arrow
   */
  int getArrow();
  
  /**
   * Add a monster to this node.
   */
  void setMonster();
  
  /**
   * Get the monster in this cave.
   * @return The monster
   */
  Imonster getMonster();
  
  /**
   * Get the x coordinate of this node.
   * @return x coordinate.
   */
  int getX();
  
  /**
   * Get the y coordinate of this node.
   * @return y coordinate.
   */
  int getY();
  
  /**
   * Set the direction of this node.
   * @param str the direction string, such as "north".
   */
  void setDirection(String str);
  
  /**
   * Get all the directions of this node.
   * @return a list of directions.
   */
  List<String> getDirection();
  
  /**
   * Set the type of this node. 
   * @param type the type of this node, such as cave, tunnel.
   */
  void setType(Type type);
  
  /**
   * Get the type of this node.
   * @return the type of this node.
   */
  Type getType();
  
  /**
   * Set the treasure of this node. 
   * @param treasure the treasure that will be added to this node.
   */
  void setTreasure(Itreasure treasure);
 
  /**
   * Clear all the treasure in this node.
   */
  void clearTreasure();
  
  /**
   * Get all the treasure in this node. 
   * @return a list containing all treasure.
   */
  List<Itreasure> getTreasure();
  
  /**
   * Get the distance of this node and start node.
   * @return the distance
   */
  double getDistance();
  
  /**
   * Set the distance of this node.
   * @param distance the allocated distance 
   */
  void setDistance(double distance);
}
