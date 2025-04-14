package test;

import static org.junit.Assert.assertEquals;

import controller.Controller;
import controller.Icontroller;
import dungeon.Idungeon;
import dungeon.MockDungeon;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import view.DungeonGuiView;

/**
 * This class contains tests for the dungeon class.
 * @author Fan Wu
 *
 */
public class DungeonTest {
  
  /**
   * Test controller with a null model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullModel() {
    new Controller(new DungeonGuiView(null), null);
  }

  /**
   * Test controller with a null view.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullView() {
    new Controller(null, new MockDungeon(new StringBuffer()));
  }
  
  /**
   * Test controller make a move.
   */
  @Test
  public void testControllerMove() {
    StringBuffer log = new StringBuffer();
    Idungeon model = new MockDungeon(log);
    Icontroller controller = new Controller(new DungeonGuiView(model), model);
    controller.handleCellClick(2, 3);
    String expected = "getLastNodeFromPath()\n"
                      + "moveOneStep()\n"
                      + "getLastNodeFromPath()\n"
                      + "getLastNodeFromPath()\n";
    assertEquals(expected, log.toString());
  }

  /**
   * Test controller make a shoot.
   */
  @Test
  public void testControllerShoot() {
    StringBuffer log = new StringBuffer();
    Idungeon model = new MockDungeon(log);
    Icontroller controller = new Controller(new DungeonGuiView(model), model);
    controller.shoot(1, "n");
    String expected = "getLastNodeFromPath()\n"
                      + "shoot()\n";
    assertEquals(expected, log.toString());
    
  }
  

  
  
}
