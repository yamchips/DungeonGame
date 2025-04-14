package dungeon;

import java.util.List;


/**
 * This interface represents a dungeon. It contains methods that change the model.
 * @author Fan Wu
 *
 */
public interface Idungeon extends ReadonlyDungeon {
  
  /**
   * Set the two player game's turn.
   * @param turn The turn that will be set
   */
  void setTurn(Turn turn);
  
  
  /**
   * Retrieve the model with same size but different random seed.
   * @return The model with same size but different random seed
   */
  Idungeon startGameAgain();
  
  /**
   * Retrieve the origin model when restart the new game with the same random seed.
   * @return the origin model
   */
  Idungeon retrieveOriginModel();
  
  /**
   * Move from node1 to node2, check the distance between these two nodes 
   * before calling this method.
   * @param node1 The current node
   * @param node2 The destination
   */
  void moveOneStep(Node node1, Node node2);
   
  /**
   * Move one step ahead.
   * @param node1     The current node
   * @param direction The moving direction
   */
  void moveOneStep(Node node1, String direction);
  
  /**
   * 
   * Constructor using inputs given by the controller.
   * @param rows     The number of rows
   * @param columns     The number of columns
   * @param inter   The interconnectivity
   * @param wrap    True, a wrapping dungeon; false otherwise
   * @param percent The percentage of treasure and arrow
   * @param monsterNum  The number of monsters
   * @param mode The game mode, true for one player game, false for two player game
   */
  void buildDungeonHelper(int rows, int columns, int inter, boolean wrap, 
                          int percent, int monsterNum, boolean mode);
  

  /**
   * This method moves the player from one node to the next node.
   * @param node      the current node.
   * @param direction the intended moving direction.
   * @return the next node.
   */
  Node moveToNext(Node node, String direction);
  
  /**
   * Create a player with playerName and set the player to the start node.
   * @param playerName the name of the player.
   */
  void playerEnter(String playerName);
  
  /**
   * Create two players with different names.
   * @param name1 The name of first player
   * @param name2 The name of second player
   */
  void playerEnterTwo(String name1, String name2);
  
  /**
   * Move the player so that the player visited all nodes in the dungeon.
   * @return a list recording the process.
   */
  List<String> movePlayerAll();

  /**
   * Move the player to the end node.
   * @return a list recording the process.
   */
  List<String> movePlayerToEnd();


}
