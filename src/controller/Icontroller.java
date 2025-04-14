package controller;

import dungeon.Idungeon;

/**
 * This interface contains all methods of a dungeon game controller.
 * @author Fan Wu
 *
 */
public interface Icontroller {
  
  /**
   * Pick up the arrow and treasure at current node.
   */
  void pickup();
  
  /**
   * Shoot an arrow given the distance and direction.
   * @param distance  The given distance
   * @param direction The given direction
   */
  void shoot(int distance, String direction); 
  
  /**
   * Play a text-based dungeon game.
   * @param dungeon The input dungeon model
   */
  void playTextBasedGame(Idungeon dungeon);
  
  /**
   * Start a new dungeon game with the original model.
   */
  void restartSameGame();
  
  /**
   * Start a new dungeon game with a new instance of the model.
   */
  void restartGame();
  
  /**
   * Handle the input model parameters from the view.
   * @param row     The number of rows
   * @param col     The number of columns
   * @param inter   The interconnectivity
   * @param wrap    True, a wrapping dungeon; false otherwise
   * @param percent The percentage of treasure and arrow
   * @param monNum  The number of monsters
   * @param gameMode True, single player game.False, two player game
   */
  void buildNewGame(int row, int col, int inter, boolean wrap, int percent, int monNum, 
                    boolean gameMode);
  
  /**
   * Handle an action in a single cell of the dungeon, such as to make a move.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  void handleCellClick(int row, int col);
  
  /**
   * Play the game dungeon with the given dungeon model and two players.
   * 
   */
  void playTwo();
  
  /**
   * Play the game dungeon with the given dungeon model.
   * 
   */
  void play();

}
