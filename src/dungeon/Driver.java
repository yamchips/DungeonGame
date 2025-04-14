package dungeon;

import controller.Controller;
import controller.Icontroller;
import java.io.InputStreamReader;
import java.util.Scanner;
import view.DungeonGuiView;


/**
 * This is the drive class.
 * @author Fan Wu
 *
 */
public class Driver {
  /**
   * This class is the main class.
   * @param args the default parameter.
   */
  public static void main(String[] args) {  
    
    System.out.println("Please enter the game mode: gui for a GUI interface and text for "
                       + "a text-based game.");
    Readable input = new InputStreamReader(System.in);
    Scanner scan = new Scanner(input);
    String in = scan.next();
    
    if ("gui".equals(in.toLowerCase())) {
      // 1. Create an instance of the model. We need get the inputs from view.
      StringBuffer log = new StringBuffer();
      Idungeon model = new MockDungeon(log);
      // 2. Create an instance of the view.
      DungeonGuiView view = new DungeonGuiView(model);
      // 3. Create an instance of the controller.
      Icontroller controller = new Controller(view, model);
      // 4. Call playGame() on the controller.
      controller.play();
    }
    if ("text".equals(in.toLowerCase())) {
      // build the model
      // Readable param = new StringReader("4 5 3 true 50 5");
      System.out.println("Please build the dungeon model first.\nFor example, type in "
                         + "\"4 5 3 true 50 5\" means building a dungeon of 4 rows, 5 columns,"
                         + " 3 interconnectivity, wrapping, 50% caves have treasure, 5 monsters.");
      Readable param = new InputStreamReader(System.in);
      Idungeon dungeon = new Dungeon(param);
      //Idungeon dungeon = new Dungeon();
      //System.out.println(dungeon.toString());
      
      // build the controller
      Readable textinput = new InputStreamReader(System.in);
      Appendable output = System.out;    
      Controller control = new Controller(textinput, output); 

      // play the game
      control.playTextBasedGame(dungeon);
    }
    
    scan.close();
      
  }
}
