package dungeon;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import player.Iplayer;

/**
 * This interface represents a read-only dungeon model.
 * @author Fan Wu
 *
 */
public interface ReadonlyDungeon {
  /**
   * Get the current turn of a two player game.
   * @return The current turn
   */
  Turn getTurn();
  
  /**
   * Get the game mode of current model.
   * @return True for single player game, false for two player game.
   */
  boolean getMode();
  
  /**
   * Get the nodePath from dungeon.
   * @return The node path
   */
  List<Node> getNodePath();
  
  /**
   * Get the column number of this dungeon.
   * @return The column number
   */
  int getColumns();
  
  /**
   * Get the row number of this dungeon.
   * @return The row number
   */
  int getRows();
  
  /**
   * Check whether it is safe in current node.
   * @param node Current node
   * @return Hint information
   */
  String checkCurrentNode(Node node);
  
  /**
   * Shoot an arrow at the given distance.
   * @param node      The current node
   * @param distance  The shooting distance
   * @param direction The shooting direction
   * @return Hint information according to the shooting distance and the injury of the monster
   */
  String shoot(Node node, int distance, String direction);
  
  /**
   * Detect the nearby monster and return hint information.
   * @param node The current node of player
   * @return Hint information according to the distance and number of monsters.
   */
  String detectMonster(Node node);
  
  /**
   * Get player of this game.
   * @return The player object
   */
  Iplayer getPlayer();
  
  /**
   * Get the treasure of given node.
   * @param node The target node
   * @return If there are treasure, return the name of treasure, else return a hint
   */
  String getNodeTreasure(Node node);
  
  /**
   * Get the last node from nodePath.
   * @return The last node
   */
  Node getLastNodeFromPath();
  
  /**
   * Get the player's name.
   * @return The name of current player.
   * @throws IOException For testing purpose.
   */
  String getPlayerName() throws IOException;
  
  /**
   * If a player reaches the end, the game is over.
   * @return true, the game is over; false otherwise.
   */
  boolean isGameOver();
  
  /**
   * Show the player's name and treasure collected.
   * @return a list of string containing the above information.
   */
  String describePlayer();
  
  /**
   * Get a set of nodes represents all node that the player has visited.
   * @return a set of nodes that the player visited.
   */
  Set<Node> getNodeHistory();
 
  /**
   * Get the length between the start node and the end node.
   * @param start the start node.
   * @param end   the end node.
   * @return the length between start and end node.
   */
  int getLength(Node start, Node end);
  
  /**
   * Get the start node.
   * @return the start node.
   */
  Node getStart();
  
  /**
   * Get the end node.
   * @return the end node.
   */
  Node getEnd();
  
  /**
   * Get the number of caves with treasure in this dungeon.
   * @return the number of caves with treasure.
   */
  int getCaveWithTreasureNum();
  
  /**
   * Get the number of caves of this dungeon.
   * @return the number of caves.
   */
  int getCaveNum();
  
  /**
   * Get the player A's y coordinate.
   * @return the y coordinate of player.
   */
  int getPlayery1();
  
  /**
   * Get the player A's x coordinate.
   * @return the x coordinate of player.
   */
  int getPlayerx1();
  
  /**
   * Get the player b's y coordinate.
   * @return the y coordinate of player.
   */
  int getPlayery2();
  
  /**
   * Get the player b's x coordinate.
   * @return the x coordinate of player.
   */
  int getPlayerx2();
  
  /**
   * Get the node list of this dungeon.
   * @return the node list of this dungeon.
   */
  List<Node> getNodeList();
}
