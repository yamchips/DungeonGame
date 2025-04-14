package view;

import controller.Icontroller;
import dungeon.ReadonlyDungeon;

/**
 * A view for Dungeon game: display the game board and provide visual interface
 * for users.
 */
public interface DungeonView {
  
  /**
   * Set up the controller to handle click events in this view.
   * 
   * @param listener the controller
   */
  void addClickListener(Icontroller listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();
  
  /**
   * Set a new model for the view.
   * @param model The new model
   */
  void setModel(ReadonlyDungeon model);
  
  /**
   * Shows a pop-up message to the user.
   * @param message The message to display in the pop-up window.
   */
  void showPopupMessage(String message);
}
