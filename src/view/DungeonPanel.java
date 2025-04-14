package view;

import dungeon.MockDungeon;
import dungeon.ReadonlyDungeon;
import dungeon.Type;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

/**
 * This class represents a panel for dungeon game.
 * @author Fan Wu
 *
 */
public class DungeonPanel extends JPanel {

  private ReadonlyDungeon dungeon;
  
  /**
   * Constructor.
   */
  public DungeonPanel() {
    this.setBackground(Color.WHITE);
    // setSize seems have no effect on the panel
    //this.setSize(500, 500);
    this.setVisible(true);
    this.dungeon = new MockDungeon(new StringBuffer());
  }
  
  /**
   * Set the dungeon model and repaint the panel.
   * @param dungeon The input model
   */
  public void setDungeon(ReadonlyDungeon dungeon) {
    this.dungeon = dungeon;
    this.repaint();
  }
  
  @Override
  public void paintComponent(Graphics g) {
    if (this.dungeon.getColumns() > 0 && this.dungeon.getRows() > 0 
        && this.dungeon.getNodePath() != null) {
      // paint the grid of dungeon
      // vertical lines
      for (int i = 1; i < this.dungeon.getColumns(); i++) {
        g.fillRect(50 * i, 50, 2, 50 * this.dungeon.getRows());
      }
      // horizontal lines
      for (int i = 1; i < this.dungeon.getRows(); i++) {
        g.fillRect(0, 50 * i + 50, 50 * this.dungeon.getColumns(), 2);
      }

      // paint the nodes in nodePath
      for (dungeon.Node n : this.dungeon.getNodePath()) {

        if (n.getType() == Type.cave) {
          g.drawOval(50 * n.getY() + 15, 50 + 50 * n.getX() + 15, 20, 20);
          drawNodeDirection(n, g);
        }
        if (n.getType() == Type.tunnel) {
          g.fillOval(50 * n.getY() + 24, 50 + 50 * n.getX() + 24, 4, 4);
          drawNodeDirection(n, g);
        }

      }

      // paint the player's current location
      int playerx1 = this.dungeon.getPlayerx1();
      int playery1 = this.dungeon.getPlayery1();
      g.setColor(Color.RED);
      g.drawOval(50 * playery1 + 10, 50 + 50 * playerx1 + 10, 30, 30);
      
      int playerx2 = this.dungeon.getPlayerx2();
      int playery2 = this.dungeon.getPlayery2();
      g.setColor(Color.BLUE);
      g.drawOval(50 * playery2 + 10, 50 + 50 * playerx2 + 10, 30, 30);
      
    }
    
    

  }
  
  /**
   * This method draws the input node and its direction.
   * @param n The input node
   * @param g The graphics object
   */
  private void drawNodeDirection(dungeon.Node n, Graphics g) {
    List<String> directions = n.getDirection();
    for (String dir : directions) {
      if ("north".equals(dir)) {
        if (n.getType() == Type.cave) {
          g.fillRect(50 * n.getY() + 25, 50 + 50 * n.getX(), 1, 15);
        }
        if (n.getType() == Type.tunnel) {
          g.fillRect(50 * n.getY() + 25, 50 + 50 * n.getX(), 1, 15);
        }
      }
      if ("south".equals(dir)) {
        if (n.getType() == Type.cave) {
          g.fillRect(50 * n.getY() + 25, 50 + 50 * n.getX() + 35, 1, 15);
        }
        if (n.getType() == Type.tunnel) {
          g.fillRect(50 * n.getY() + 25, 50 + 50 * n.getX() + 35, 1, 15);
        }
      }
      if ("east".equals(dir)) {
        if (n.getType() == Type.cave) {
          g.fillRect(50 * n.getY() + 35, 50 + 50 * n.getX() + 25, 15, 1);
        }
        if (n.getType() == Type.tunnel) {
          g.fillRect(50 * n.getY() + 35, 50 + 50 * n.getX() + 25, 15, 1);
        }
      }
      if ("west".equals(dir)) {
        if (n.getType() == Type.cave) {
          g.fillRect(50 * n.getY(), 50 + 50 * n.getX() + 25, 15, 1);
        }
        if (n.getType() == Type.tunnel) {
          g.fillRect(50 * n.getY(), 50 + 50 * n.getX() + 25, 15, 1);
        }
      }
    }
  }
  
  
  
  
}
