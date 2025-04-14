package controller;

import dungeon.Dungeon;
import dungeon.Idungeon;
import dungeon.Node;
import dungeon.Turn;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import player.State;
import view.DungeonView;

/**
 * This class represents a controller for playing dungeon game.
 * @author Fan Wu
 * @field out  The out put information
 * @field scan The command line input
 */
public class Controller implements Icontroller {
  private Appendable out;
  private Scanner scan;
  
  private Idungeon model;
  private DungeonView view;
  
  /**
   * Constructor for the controller to play text-based one player game.
   * @param in  The source to read from
   * @param out The target to print to
   */
  public Controller(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null.");
    }
    this.out = out;
    scan = new Scanner(in);
  }
  
  /**
   * Constructor for GUI.
   * @param view  The input view
   * @param model The input model
   */
  public Controller(DungeonView view, Idungeon model) throws IllegalArgumentException {
    if (view == null || model == null) {
      throw new IllegalArgumentException("The controller's input can't be null.");
    }
    this.view = view;
    this.model = model;
  }
  
  
  @Override
  public void play() {
    this.view.addClickListener(this);
    this.view.refresh();
    
  }
  
  @Override
  public void playTwo() {
    
  }

  @Override
  public void handleCellClick(int row, int col) {
    // if the input is out of bound, do nothing
    if (row > this.model.getRows() - 1 || col > this.model.getColumns() - 1) {
      return;
    }
    
    if (this.model.getMode()) {
      movePlayer(row, col);
    } else {
      // two player game
      if (this.model.getTurn() == Turn.A) {
        movePlayer(row, col);
        this.model.setTurn(Turn.B);
      } else {
        movePlayer(row, col);
        this.model.setTurn(Turn.A);
      }
      this.view.refresh();
    }
    
  }
  
  @Override
  public void shoot(int distance, String direction) {
    if (this.model.getMode()) {
      playerShoot(distance, direction);
    } else {
      if (this.model.getTurn() == Turn.A) {
        playerShoot(distance, direction);
        this.model.setTurn(Turn.B);
      } else {
        playerShoot(distance, direction);
        this.model.setTurn(Turn.A);
      }
    }
  }

  @Override
  public void pickup() {
    if (this.model.getMode()) {
      playerPickup();
    } else {
      if (this.model.getTurn() == Turn.A) {
        playerPickup();
        this.model.setTurn(Turn.B);
      } else {
        playerPickup();
        this.model.setTurn(Turn.A);
      }
    }
  }

  @Override
  public void buildNewGame(int row, int col, int inter, boolean wrap, 
                           int percent, int monNum, boolean gameMode) {
    // build a dungeon model using the inputs
    //this.model.buildDungeonHelper(row, col, inter, wrap, percent, monNum);
    this.model = new Dungeon(row, col, inter, wrap, percent, monNum, gameMode);
    this.view.setModel(this.model);
    this.view.refresh();
  }

  @Override
  public void restartGame() {
    this.model = this.model.startGameAgain();
    this.view.setModel(model);
  }

  @Override
  public void restartSameGame() {
    this.model = this.model.retrieveOriginModel();
    this.view.setModel(this.model);
  }
  
  @Override
  public void playTextBasedGame(Idungeon dungeon) {
    if (dungeon == null) {
      throw new IllegalArgumentException("The model cannot be null.");
    }
    
    // create a player  
    try {
      //out.append(dungeon.toString() + "\n");
      out.append("Enter the name of the player: \n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed while outputing the dungeon.", e);
    }
    String name = scan.next();
    dungeon.playerEnter(name);  
    
    // player enters the dungeon
    try {
      out.append("Player Enters the dungeon at (" + dungeon.getPlayerx1() + ", "
          + dungeon.getPlayery1() + ")\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed while player entering dungeon.", e);
    }
    
    
    // player begins exploring dungeon until game over   
    boolean badAction = false;
    boolean quit = false;
    while (!dungeon.isGameOver() || quit) {
      
      if (!badAction) {
        // show player's current position and current node's arrow number
        try {
          out.append(dungeon.getPlayerName() + " is at (" + dungeon.getPlayerx1() + ", "
              + dungeon.getPlayery1() + ")\nThe number of arrows at this node: "
              + dungeon.getLastNodeFromPath().getArrow() + ".\n");
        } catch (IOException e) {
          throw new IllegalStateException("Append failed while displaying player's position.", e);
        } catch (NullPointerException npe) {
          throw new NullPointerException("Model methods have null return value.\n");
        }

        // show monster detecting information
        try {
          out.append(dungeon.detectMonster(dungeon.getLastNodeFromPath()));
        } catch (IOException e) {
          throw new IllegalStateException("Append failed while displaying monster detecting"
                                          + " information.", e);
        }
        
        // show current node's treasure
        try {
          out.append("" + dungeon.getNodeTreasure(dungeon.getLastNodeFromPath()));
        } catch (IOException e) {
          throw new IllegalStateException(
              "Append failed while displaying current node's " + "treasure.", e);
        }

        // show possible moving direction of current node
        String possibledir = String.join(",", dungeon.getLastNodeFromPath().getDirection());
        try {
          out.append("The possbile moving directions: " + possibledir + ".\n");
        } catch (IOException e) {
          throw new IllegalStateException(
              "Append failed while displaying current node's " + "possible directions.", e);
        }
      }
      
      badAction = false;
      
      
      // let player choose to move, shoot or pick up
      try {
        out.append("Move, shoot or pick up? Enter a choice(M/S/P):\n");
      } catch (IOException e) {
        throw new IllegalStateException("Append failed while displaying player's choice.", e);
      }
        
      
      String choice = scan.next();     
            
      // if player chooses to move, let player choose a direction     
      
      if ("m".equals(choice.toLowerCase())) {
        try {
          out.append("Please enter a direction. N for north, S for south, E for east,"
                     + " W for west.\n");
        } catch (IOException e) {
          throw new IllegalStateException("Append failed while asking for player's direction.", e);
        }
        
        String direction = scan.next();
        
        // if player chooses to quit
        if ("q".equals(direction.toLowerCase())) {
          quit = true;
          break;
        }
        
        String inputDir;
        if ("w".equals(direction.toLowerCase())) {
          inputDir = "west"; 
        } else if ("e".equals(direction.toLowerCase())) {
          inputDir = "east"; 
        } else if ("n".equals(direction.toLowerCase())) {
          inputDir = "north"; 
        } else if ("s".equals(direction.toLowerCase())) {
          inputDir = "south"; 
        } else {
          inputDir = "";
          badAction = true;
          try {
            out.append("Input directions should be n, s, e or w. Please try again.\n");
          } catch (IOException e) {
            throw new IllegalStateException("Append failed while re-asking for player's "
                                            + "direction.", e);
          }
          continue;
        }
        
        dungeon.moveOneStep(dungeon.getLastNodeFromPath(), inputDir);
        String currentNodeInfo = dungeon.checkCurrentNode(dungeon.getLastNodeFromPath());
        
        try {
          out.append(currentNodeInfo);
        } catch (IOException e) {
          throw new IllegalStateException("Append failed while checking current position's "
              + "safety state.", e);
        }
        
        
      }
      
      
      // if player chooses to shoot
      if ("s".equals(choice.toLowerCase())) {
        if (dungeon.getPlayer().getArrowNum() > 0) {
          try {
            out.append("Choose a shooting distance:\n");
          } catch (IOException e) {
            throw new IllegalStateException("Append failed while choosing shooting distance.\n", e);
          }

          String distance = scan.next();
          // if player chooses to quit
          if ("q".equals(distance.toLowerCase())) {
            quit = true;
            break;
          }
          int shootingDist;
          try {
            shootingDist = Integer.parseInt(distance);
          } catch (NumberFormatException e1) {
            try {
              out.append("Please enter an integer.\n");
              badAction = true;
            } catch (IOException e) {
              throw new IllegalStateException(
                  "Append failed while reminding user to give " + "valid integer.\n", e);
            }
            continue;
          }

          try {
            out.append("Choose shooting direction. N for north, S for south, E for east, "
                + "W for west:\n");
          } catch (IOException e) {
            throw new IllegalStateException("Append failed while choosing shooting direction.\n",
                e);
          }

          String direction = scan.next();
          // if player chooses to quit
          if ("q".equals(direction.toLowerCase())) {
            quit = true;
            break;
          }
          String inputDir;
          if ("w".equals(direction.toLowerCase())) {
            inputDir = "west";
          } else if ("e".equals(direction.toLowerCase())) {
            inputDir = "east";
          } else if ("n".equals(direction.toLowerCase())) {
            inputDir = "north";
          } else if ("s".equals(direction.toLowerCase())) {
            inputDir = "south";
          } else {
            inputDir = "";
            badAction = true;
            try {
              out.append("Input directions should be n, s, e or w. Please try again.\n");
            } catch (IOException e) {
              throw new IllegalStateException(
                  "Append failed while re-asking for player's " + "direction.", e);
            }
            continue;
          }

          String shootInfo = dungeon.shoot(dungeon.getLastNodeFromPath(), shootingDist, inputDir);

          try {
            out.append(shootInfo);
          } catch (IOException e) {
            throw new IllegalStateException(
                "Append failed while presenting shooting " + "information.\n", e);
          }
        } else {
          // the player has no arrow
          try {
            out.append("You are out of arrows.\n");
          } catch (IOException e) {
            throw new IllegalStateException(
                "Append failed while reminding the player has no arrow.\n", e);
          }
          
        }
        
      }
      
      
      // if player choose to pick up, player picks up all treasure and arrows in this node
      if ("p".equals(choice.toLowerCase())) {
        if (dungeon.getLastNodeFromPath().getArrow() != 0) {
          dungeon.getPlayer().pickupArrow();
          dungeon.getLastNodeFromPath().setArrow(0);
          try {
            out.append(dungeon.getPlayerName() + " picks up an arrow.\n" + dungeon.getPlayerName()
                       + " has " + dungeon.getPlayer().getArrowNum() + " arrows.\n");
          } catch (IOException e) {
            throw new IllegalStateException("Append failed while picking up arrows.\n", e);
          }
        }
        if (dungeon.getLastNodeFromPath().getTreasure().size() > 0) {
          dungeon.getPlayer().setTreasure(dungeon.getLastNodeFromPath().getTreasure());
          dungeon.getLastNodeFromPath().clearTreasure();
          try {
            out.append(dungeon.getPlayerName() + " picks up treasure.\n" 
                       + dungeon.describePlayer());
          } catch (IOException e) {
            throw new IllegalStateException("Append failed while picking up treasure.\n", e);
          }
        }
        
      }
      
      // if player choose to quit
      if ("q".equals(choice.toLowerCase())) {
        quit = true;
        break;
      }
      
      
      // if player enters input other than m, s, q, p
      if (!("m".equals(choice.toLowerCase()) || "s".equals(choice.toLowerCase()) 
          || "p".equals(choice.toLowerCase()) || "q".equals(choice.toLowerCase()))) {
        try {
          out.append("Input Action must be m, s or q. Please try again.\n");
          badAction = true;
        } catch (IOException e) {
          throw new IllegalStateException("Append failed while asking for correct "
                                          + "player's direction.", e);
          
        }
        continue;
      }
      
      
    } // the game loop 
    
    // when the game is over, print the final state
    if (quit == true) {
      // the player end the game
      try {
        out.append("You quit the game. See you next time.\n");
      } catch (IOException e) {
        throw new IllegalStateException("Append failed while outputing quit information.", e);
      }
      
    } else {
      if (dungeon.getPlayer().getState() == State.alive) {
        try {
          out.append(
              "Congratulations! You successfully reach the end cave.\n" + dungeon.describePlayer()
                  + "The remaining arrows: " + dungeon.getPlayer().getArrowNum());
        } catch (IOException e) {
          throw new IllegalStateException("Append failed while outputing final results (win).", e);
        }
      } else {
        try {
          out.append("Bad luck! You are eaten by an Otyugh!\nBetter luck next time.\n");
        } catch (IOException e) {
          throw new IllegalStateException("Append failed while outputing final results (lose).", e);
        }
      }
    }
    

    
    
  }
  
  private void movePlayer(int row, int col) {
    // single player game
    // if the input is within the dungeon, check the distance between input node and
    // current node
    Node current = this.model.getLastNodeFromPath();
    Node input = this.model.getNodeList().get(row * this.model.getColumns() + col);
    if (this.model.getLength(current, input) != 1) {
      return;
    }
    // if the length is 1, move to the input node
    try {
      this.model.moveOneStep(current, input);
    } catch (IllegalStateException ise) {
      JOptionPane.showMessageDialog(null, ise.getMessage());
      return;
    }

    this.view.refresh();

    // check whether the game is over
    // String currentNodeInfo =
    // this.model.checkCurrentNode(this.model.getLastNodeFromPath());

    if (this.model.isGameOver()) {
      if (this.model.getPlayer().getState() == State.alive) {
        this.view.showPopupMessage("Congratulations! You successfully reach the end cave.\n");
      } else {
        this.view.showPopupMessage(
            "Bad luck! You are eaten by an Otyugh!\n" + "Better luck next time.\n");
      }
    }
  }
  
  
  private void playerShoot(int distance, String direction) {
    if (this.model.getPlayer().getArrowNum() > 0) {
      String inputDir = "";
      if ("w".equals(direction.toLowerCase())) {
        inputDir = "west";
      } else if ("e".equals(direction.toLowerCase())) {
        inputDir = "east";
      } else if ("n".equals(direction.toLowerCase())) {
        inputDir = "north";
      } else if ("s".equals(direction.toLowerCase())) {
        inputDir = "south";
      }
      String shootInfo = this.model.shoot(this.model.getLastNodeFromPath(), distance, inputDir);
      if ("test".equals(shootInfo)) {
        return;
      } else {
        this.view.showPopupMessage(shootInfo);
      }

    } else {
      // the player has no arrow
      this.view.showPopupMessage("Oops! You don't have any arrows!");
    }
  }
  
  private void playerPickup() {
    String pickupArrow = "";
    if (this.model.getLastNodeFromPath().getArrow() != 0) {
      this.model.getPlayer().pickupArrow();
      this.model.getLastNodeFromPath().setArrow(0);
      try {
        pickupArrow = this.model.getPlayerName() + " picks up an arrow.\n";
      } catch (IOException e) {
        this.view.showPopupMessage("Get player name fails.");;
      }
    } else {
      pickupArrow = "There is no arrow here.\n";
    }
    String pickupTreasure = "";
    if (this.model.getLastNodeFromPath().getTreasure().size() > 0) {
      this.model.getPlayer().setTreasure(this.model.getLastNodeFromPath().getTreasure());
      this.model.getLastNodeFromPath().clearTreasure();
      try {
        pickupTreasure = this.model.getPlayerName() + " picks up treasure.\n" 
                         + this.model.describePlayer();
      } catch (IOException e) {
        this.view.showPopupMessage("Get player name fails.");
      }
    } else {
      pickupTreasure = "There is no treasure here.\n" + this.model.describePlayer();
    }
    this.view.showPopupMessage(pickupArrow + pickupTreasure);
  }
  
}
