package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class represents a mouse adapter.
 * @author Fan Wu
 * @field listener the dungeon controller that receives the mouse movement
 */
public class DungeonMouse extends MouseAdapter {

  private Icontroller listener;
  
  /**
   * Constructor.
   * @param controller The input controller
   */
  public DungeonMouse(Icontroller controller) {
    this.listener = controller;
  }
  
  @Override
  public void mouseClicked(MouseEvent e) {
    
    int row = (e.getY() - 50) / 50;
    int col = e.getX() / 50;
    
    
    this.listener.handleCellClick(row, col);
     
  }
  
}
