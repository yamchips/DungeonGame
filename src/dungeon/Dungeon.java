package dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import player.Iplayer;
import player.Player;
import player.State;
import treasure.Diamonds;
import treasure.Itreasure;
import treasure.Rubies;
import treasure.Sapphires;

/**
 * This class represents a dungeon model.
 * @author Fan Wu
 *
 */
public class Dungeon implements Idungeon {
  private int rows;
  private int columns;
  private int interconnect;
  private boolean wrapped;
  private int percentage;
  private List<Node> nodeList;
  private int start1;
  private int start2;
  private int end;
  private List<String> record;
  private List<String> allrecord;
  private Iplayer player1;
  private Iplayer player2;
  private Set<Node> nodeHistory;
  private int monsterNum;
  private Scanner scan;
  private List<Node> nodePath;
  private List<Node> nodePath2;
  private List<Integer> arrowIndexes;
  private List<Integer> treasureIndexes;
  private List<List<Itreasure>> treasureRecord;
  private List<Integer> monsterIndexes;
  private boolean mode;
  private Turn turn;
  
  
  /**
   * Constructor using inputs given by the controller.
   * @param row     The number of rows
   * @param col     The number of columns
   * @param inter   The interconnectivity
   * @param wrap    True, a wrapping dungeon; false otherwise
   * @param percent The percentage of treasure and arrow
   * @param monNum  The number of monsters
   * @param mode    True, single player game. False, two player game
   */
  public Dungeon(int row, int col, int inter, boolean wrap, int percent, int monNum, boolean mode) {
    this.mode = mode;

    buildDungeonHelper(row, col, inter, wrap, percent, monNum, this.mode); 

    
    
    
  }
  
  /**
   * Constructor, build a deterministic 4*5 dungeon for testing purpose.
   * @param str A string
   */
  public Dungeon(String str) {    
    this.wrapped = true;
    this.rows = 4;
    this.columns = 5;
    this.interconnect = 3;
    this.percentage = 50;
    this.nodeList = new ArrayList<>();
    this.record = new ArrayList<>();
    this.allrecord = new ArrayList<>();
    this.nodeHistory = new HashSet<>();
    this.monsterNum = 5;
    this.nodePath = new ArrayList<>();
    this.nodePath2 = new ArrayList<>();
    this.mode = true;
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.columns; j++) {
        Node newNode = new Node(i, j);
        this.nodeList.add(newNode);
      }
    }
    
    // first row
    this.nodeList.get(0).setDirection("east");
    this.nodeList.get(0).setDirection("south");
    
    this.nodeList.get(1).setDirection("south");
    this.nodeList.get(1).setDirection("west");
    
    this.nodeList.get(2).setDirection("north");
    //this.nodeList.get(2).setMonster();
    
    this.nodeList.get(3).setDirection("north");
    this.nodeList.get(3).setDirection("east");
    
    this.nodeList.get(4).setDirection("west");
    this.nodeList.get(4).setDirection("north");
    this.nodeList.get(4).setDirection("south");
    this.nodeList.get(4).setMonster();
    // second row
    this.nodeList.get(5).setDirection("north");
    this.nodeList.get(5).setDirection("west");
    
    this.nodeList.get(6).setDirection("east");
    this.nodeList.get(6).setDirection("north");
    
    this.nodeList.get(7).setDirection("west");
    this.nodeList.get(7).setDirection("east");
    
    this.nodeList.get(8).setDirection("west");
    this.nodeList.get(8).setDirection("south");
    
    this.nodeList.get(9).setDirection("north");
    this.nodeList.get(9).setDirection("east");
    // third row
    this.nodeList.get(10).setDirection("south");
    this.nodeList.get(10).setMonster();
    
    this.nodeList.get(11).setDirection("east");
    this.nodeList.get(11).setDirection("south");
    
    this.nodeList.get(12).setDirection("east");
    this.nodeList.get(12).setDirection("west");
    
    this.nodeList.get(13).setDirection("east");
    this.nodeList.get(13).setDirection("west");
    this.nodeList.get(13).setDirection("north");
    this.nodeList.get(13).setDirection("south");
    this.nodeList.get(13).setMonster();
    
    this.nodeList.get(14).setDirection("south");
    this.nodeList.get(14).setDirection("west");
    // fourth row    
    this.nodeList.get(15).setDirection("west");
    this.nodeList.get(15).setDirection("east");
    this.nodeList.get(15).setDirection("north");
    this.nodeList.get(15).setMonster();
    
    this.nodeList.get(16).setDirection("west");
    this.nodeList.get(16).setDirection("north");
    
    this.nodeList.get(17).setDirection("east");
    this.nodeList.get(17).setDirection("south");
    
    this.nodeList.get(18).setDirection("west");
    this.nodeList.get(18).setDirection("south");
    this.nodeList.get(18).setDirection("north");
    this.nodeList.get(18).setArrow(1);
    this.nodeList.get(18).setTreasure(new Diamonds());
    
    this.nodeList.get(19).setDirection("east");
    this.nodeList.get(19).setDirection("south");
    this.nodeList.get(19).setDirection("north");
    // set start and end position    
    this.start1 = 18;
    this.end = 10;
    
    for (Node n : this.nodeList) {
      if (n.getDirection().size() == 2) {
        n.setType(Type.tunnel);
      } else {
        n.setType(Type.cave);
      }
    }
    
    this.nodePath.add(this.nodeList.get(this.start1));
  
    playerEnter("Player A");
    
  
  }

  /**
   * Constructor.
   * @param param The input string that contains all parameters of this dungeon.
   *  
   */
  public Dungeon(Readable param) {
    if (param == null) {
      throw new IllegalArgumentException("Readable can't be null.");
    }
    scan = new Scanner(param);
    
    String row = scan.next();
    int rows;
    try {
      rows = Integer.parseInt(row);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Row number must be an integer.\n");
    }
    String cols = scan.next();
    int columns;
    try {
      columns = Integer.parseInt(cols);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Column number must be an integer.\n");
    }
    String interconnectivity = scan.next();
    int inter;
    try {
      inter = Integer.parseInt(interconnectivity);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Interconnectivity must be an integer.\n");
    }
    String wrap = scan.next();
    boolean wrapped = Boolean.parseBoolean(wrap);
    this.wrapped = wrapped;
    String percentage = scan.next();
    int percent;
    try {
      percent = Integer.parseInt(percentage);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Percentage must be an integer.\n");
    }
    String monsters = scan.next();
    int monsterNum;
    try {
      monsterNum = Integer.parseInt(monsters);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Monster number must be an integer.\n");
    }
    // TODO Auto-generated method stub
    buildDungeonHelper(rows, columns, inter, wrapped, percent, monsterNum, true);
  
  }

  @Override
  public Idungeon startGameAgain() {
    buildDungeonHelper(this.rows, this.columns, this.interconnect, this.wrapped, 
                       this.percentage, this.monsterNum, this.mode); 
    
    return this;
  }
  
  @Override
  public Idungeon retrieveOriginModel() {
    // since the node list is changed, we set the record to it
    
    // reset the arrows
    for (int i : this.arrowIndexes) {
      this.nodeList.get(i).setArrow(1);
    }
    
    // reset the treasure
    for (Node n : this.nodeList) {
      n.clearTreasure();
    }
    
    for (int i = 0; i < this.treasureIndexes.size(); i++) {
      int num = this.treasureIndexes.get(i);
      Node node = this.nodeList.get(num);
      for (Itreasure t : this.treasureRecord.get(i)) {
        node.setTreasure(t);
      }
    }
    
    // reset the monsters
    for (int i : this.monsterIndexes) {
      this.nodeList.get(i).setMonster();
    }
    
    // clear the node path and other fields, add the new start to node path
    this.nodePath = new ArrayList<>();
    this.nodePath2 = new ArrayList<>();
    this.record = new ArrayList<>();
    this.allrecord = new ArrayList<>();
    this.nodeHistory = new HashSet<>();
    
    this.nodePath.add(this.nodeList.get(this.start1));
    this.nodePath2.add(this.nodeList.get(this.start2));
    // player re-enter the game
    
    if (!this.mode) {
      playerEnterTwo("Player A", "Player B");
    } else {
      playerEnter("Player A");
    }
    //System.out.println(this.toString());
    return this;
  }
  
  
  @Override
  public void buildDungeonHelper(int rows, int columns, int inter, boolean wrap,
                                 int percent, int monsterNum, boolean mode) {
  
    if (inter < 0 | rows < 1 | columns < 1 | percent < 0 | monsterNum < 1) {
      throw new IllegalArgumentException("The input value is invalid.");
    }
    
    this.rows = rows;
    this.columns = columns;
    this.interconnect = inter;
    this.wrapped = wrap;
    this.percentage = percent;
    this.nodeList = new ArrayList<>();
    this.record = new ArrayList<>();
    this.allrecord = new ArrayList<>();
    this.nodeHistory = new HashSet<>();
    this.monsterNum = monsterNum;
    this.nodePath = new ArrayList<>();
    this.mode = mode;
    this.nodePath2 = new ArrayList<>();
    this.player2 = new Player("Player B");
    
    // Player A starts first
    if (!this.mode) {
      this.turn = Turn.A;
    }
        
    if (this.wrapped) {
      // create a wrapped dungeon
      
      Set<Set<Node>> allEdges = new HashSet<>();
      // create all the nodes in the dungeon
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.columns; j++) {
          Node newNode = new Node(i, j);
          this.nodeList.add(newNode);
        }
        // add horizontal link to the set s
        for (int k = i * this.columns; k < (i + 1) * this.columns; k++) {
          Set<Node> set1 = new HashSet<>();
          if (k == (i + 1) * this.columns - 1) {
            set1.add(this.nodeList.get(k));
            set1.add(this.nodeList.get(i * this.columns));
          } else {
            set1.add(this.nodeList.get(k));
            set1.add(this.nodeList.get(k + 1));
          }
          allEdges.add(set1);
        }
      }
      // add vertical link to the set s
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.columns; j++) {
          Set<Node> set1 = new HashSet<>();
          if (i == this.rows - 1) {
            set1.add(this.nodeList.get(i * this.columns + j));
            set1.add(this.nodeList.get(j));
          } else {
            set1.add(this.nodeList.get(i * this.columns + j));
            set1.add(this.nodeList.get(i * this.columns + j + this.columns));
          }
  
          allEdges.add(set1);
        }
      }
      // using the code above, when row/column number is 2, allEdge set loses several
      // edges, because the set does not contain duplicates, this error will be fixed in the
      // following code
      buildDungeon(this.wrapped, allEdges);
      
    } else {
      // create a non-wrapped dungeon
      
      Set<Set<Node>> allEdges = new HashSet<>();
      // create all the nodes in the dungeon
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.columns; j++) {
          Node newNode = new Node(i, j);
          this.nodeList.add(newNode);
        }
        // add horizontal link to the set s
        for (int k = i * this.columns; k < (i + 1) * this.columns - 1; k++) {
          Set<Node> set1 = new HashSet<>();
          set1.add(this.nodeList.get(k));
          set1.add(this.nodeList.get(k + 1));
          allEdges.add(set1);
        }
      }
  
      // add vertical link to the set s
      for (int i = 0; i < this.rows - 1; i++) {
        for (int j = 0; j < this.columns; j++) {
          Set<Node> set1 = new HashSet<>();
          set1.add(this.nodeList.get(i * this.columns + j));
          set1.add(this.nodeList.get(i * this.columns + j + this.columns));
          allEdges.add(set1);
        }
      }
      
      // build a non-wrapping dungeon
      buildDungeon(this.wrapped, allEdges);
      
    }
  
    // choose start and end cave
    chooseStartEnd();
    
    // if it's two player mode, choose another cave as start
    if (!this.mode) {
      // get a list contains indexes of every cave except for the start1 and end
      List<Integer> caveIndex = new ArrayList<>();
      for (int i = 0; i < this.nodeList.size(); i++) {
        if (this.nodeList.get(i).getType() == Type.cave 
            && i != this.start1 && i != this.end) {
          caveIndex.add(i);
        }
      }
      // randomly choose one cave from the list, calculate the length between it and end
      // if the length is greater than or equal to 5, choose it as the start2
      do {
        int randomIndex = new Random().nextInt(caveIndex.size());
        Node node = this.nodeList.get(caveIndex.get(randomIndex));
        if (getLength(node, this.nodeList.get(this.end)) >= 5) {
          this.start2 = caveIndex.get(randomIndex);
          break;
        } else {
          caveIndex.remove(randomIndex);
        }
        if (caveIndex.size() == 0) {
          // that means in this dungeon, only 1 cave is 5 or more steps from end
          // then we ask the user to build a larger dungeon or try again
          throw new IllegalArgumentException("Cannot find the start cave for player B. "
                                             + "Please expand the dungeon size or try again");
        }
      } while (caveIndex.size() > 0);
    }
    
    // after choose the start and end cave, allocate monsters to some node
    allocateMonster();
    
    this.nodePath.add(this.nodeList.get(this.start1));
    this.nodePath2.add(this.nodeList.get(this.start2));
    
    
    if (!this.mode) {
      playerEnterTwo("Player A", "Player B");
    } else {
      playerEnter("Player A");
    }
    
    
  }

  @Override
  public List<Node> getNodePath() {
    if (this.mode) {
      return this.nodePath;
    } else {
      List<Node> all = new ArrayList<>();
      all.addAll(this.nodePath);
      all.addAll(this.nodePath2);
      return all;
    }
  }

  @Override
  public int getColumns() {
    return this.columns;
  }

  @Override
  public int getRows() {
    return this.rows;
  }

  @Override
  public boolean getMode() {
    return this.mode;
  }

  @Override
  public Turn getTurn() {
    return this.turn;
  }

  @Override
  public void setTurn(Turn turn) {
    this.turn = turn;
    
  }

  @Override
  public void playerEnterTwo(String name1, String name2) {
    this.player1 = new Player(name1);
    this.player1.setX(this.nodeList.get(this.start1).getX());
    this.player1.setY(this.nodeList.get(this.start1).getY());
    
    this.player2 = new Player(name2);
    this.player2.setX(this.nodeList.get(this.start2).getX());
    this.player2.setY(this.nodeList.get(this.start2).getY());
  }
  
  
  @Override
  public void playerEnter(String name) {
    this.player1 = new Player(name);
    this.player1.setX(this.nodeList.get(start1).getX());
    this.player1.setY(this.nodeList.get(start1).getY());   
  }
  
  @Override
  public String describePlayer() {
    String description = "";
    if (this.mode) {
      if (this.player1.getTreasure().size() > 0) {
        description = this.player1.getName() + "'s treasusre: "
            + String.join(",", this.player1.getTreasure()) + "\n";
      } else {
        description = this.player1.getName() + " has not collected any treasure.\n";
      }

      return description + this.player1.getName() + "'s arrow number: "
          + this.player1.getArrowNum();
    } else {
      if (this.turn == Turn.A) {
        if (this.player1.getTreasure().size() > 0) {
          description = this.player1.getName() + "'s treasusre: "
              + String.join(",", this.player1.getTreasure()) + "\n";
        } else {
          description = this.player1.getName() + " has not collected any treasure.\n";
        }

        return description + this.player1.getName() + "'s arrow number: "
            + this.player1.getArrowNum();
      } else {
        if (this.player2.getTreasure().size() > 0) {
          description = this.player2.getName() + "'s treasusre: "
              + String.join(",", this.player2.getTreasure()) + "\n";
        } else {
          description = this.player2.getName() + " has not collected any treasure.\n";
        }

        return description + this.player2.getName() + "'s arrow number: "
            + this.player2.getArrowNum();
      }
    }
    
    
  }

  @Override
  public boolean isGameOver() {
    
    
    if ((this.player1.getX() == this.nodeList.get(end).getX()
        && this.player1.getY() == this.nodeList.get(end).getY())
        || this.player1.getState() == State.dead) {
      return true;
    } else if (((this.player2.getX() == this.nodeList.get(end).getX()
        && this.player2.getY() == this.nodeList.get(end).getY())
        || this.player2.getState() == State.dead)) {
      return false;
    } else {
      return false;
    }
    
  }
  
  @Override
  public Node moveToNext(Node node, String direction) {
    if (node.getDirection().contains(direction)) {
      if ("north".equals(direction)) {
        if (node.getX() == 0) {
          return this.nodeList.get((this.rows - 1) * this.columns + node.getY());
        } else {
          return this.nodeList.get((node.getX() - 1) * this.columns + node.getY());
        }
      } else if ("south".equals(direction)) {
        if (node.getX() == this.rows - 1) {
          return this.nodeList.get(node.getY());
        } else {
          return this.nodeList.get((node.getX() + 1) * this.columns + node.getY());
        }
      } else if ("east".equals(direction)) {
        if (node.getY() == this.columns - 1) {
          return this.nodeList.get(node.getX() * this.columns);
        } else {
          return this.nodeList.get(node.getX() * this.columns + node.getY() + 1);
        }
      } else if ("west".equals(direction)) {
        if (node.getY() == 0) {
          return this.nodeList.get(node.getX() * this.columns + this.columns - 1);
        } else {
          return this.nodeList.get(node.getX() * this.columns + node.getY() - 1);
        }
      }
    } else { // we cannot move
      return node;
    }
    return node;
  }

  @Override
  public Node getLastNodeFromPath() {
    if (this.mode) {
      return this.nodePath.get(this.nodePath.size() - 1);
    } else {
      if (this.turn == Turn.A) {
        return this.nodePath.get(this.nodePath.size() - 1);
      } else {
        return this.nodePath2.get(this.nodePath2.size() - 1);
      }
    }
  }
  
  @Override
  public String getNodeTreasure(Node node) {
    if (node.getTreasure().size() > 0) {
      ArrayList<String> treasureList = new ArrayList<>();
      for (Itreasure t : node.getTreasure()) {
        treasureList.add(t.getName());
      }
      return "The treasure in this cave: " + String.join(",", treasureList) + "\n";
    } else {
      return "This node does not have any treasure.\n";
    }
  }
  
  @Override
  public void moveOneStep(Node node1, Node node2) {
    if (isGameOver()) {
      throw new IllegalStateException("The game is over. You can't make a move.");
    }
    if (this.mode) {
      // player moves to node2
      this.nodePath.add(node2);
      // change the player's location
      this.player1.setX(node2.getX());
      this.player1.setY(node2.getY());
    } else {
      if (this.turn == Turn.A) {
        // player A moves to node2
        this.nodePath.add(node2);
        // change the player A's location
        this.player1.setX(node2.getX());
        this.player1.setY(node2.getY());
      } else {
        // player B moves to node2
        this.nodePath2.add(node2);
        // change the player B's location
        this.player2.setX(node2.getX());
        this.player2.setY(node2.getY());
      }
    }
  }
  
  @Override
  public void moveOneStep(Node node1, String direction) {
    Node node2 = moveToNext(node1, direction);
    
    // player moves to node2 
    this.nodePath.add(node2);
    // change the player's location
    this.player1.setX(node2.getX());
    this.player1.setY(node2.getY());
    
  }
  
  @Override
  public String checkCurrentNode(Node node) {
    if (node.getMonster() != null) {
      switch (node.getMonster().getInjury()) {
        case 0: // player get killed
          if (this.mode) {
            this.player1.setStateDead();
          } else {
            if (this.turn == Turn.A) {
              this.player1.setStateDead();
            } else {
              this.player2.setStateDead();
            }
          }
          return "You are killed by an Otyugh.\n";
        case 1: // 50% chance to escape
          int chance = new Random().nextInt(2);
          if (chance == 0) {
            if (this.mode) {
              this.player1.setStateDead();
            } else {
              if (this.turn == Turn.A) {
                this.player1.setStateDead();
              } else {
                this.player2.setStateDead();
              }
            }
            return "You are killed by an injured Otyugh.\n";
          } else {
            return "You escape from an injured Otyugh.\n";
          }
        case 2: // a dead monster
          return "You find a dead Otyugh.\n";
        default:
          return " ";
      }
    }
    return " ";
  }
  
  @Override
  public String shoot(Node node, int distance, String direction) {
    if (this.mode) {
      // player's arrow minus 1
      this.player1.shootArrow();
    } else {
      if (this.turn == Turn.A) {
        this.player1.shootArrow();
      } else {
        this.player2.shootArrow();
      }
    }

    // calculate the end node, if there is a monster, its injury plus 1
    int totalDis = distance;
    Node nextNode = node;
    while (totalDis > 0) {
      nextNode = moveToNext(node, direction);
      // next node is a cave
      if (nextNode.getType() == Type.cave) {
        if (!nextNode.getDirection().contains(direction)) {
          break;
        } else {
          node = nextNode;
          totalDis -= 1;
        }
      } else {
        // next node is a tunnel
        String tunneldir1;
        if ("east".equals(direction)) {
          tunneldir1 = "west";
        } else if ("west".equals(direction)) {
          tunneldir1 = "east";
        } else if ("north".equals(direction)) {
          tunneldir1 = "south";
        } else {
          tunneldir1 = "north";
        }
        for (int i = 0; i < 2; i++) {
          if (!nextNode.getDirection().get(i).equals(tunneldir1)) {
            direction = nextNode.getDirection().get(i);
          }
        }
        node = nextNode;
        totalDis -= 1;
      }

    }
    if (nextNode.getMonster() != null && nextNode.getMonster().getInjury() < 2) {
      // end cave has one living monster
      nextNode.getMonster().addInjury();
      if (nextNode.getMonster().getInjury() == 1) {
        return "You hear a deep howl.\n";
      } else {
        return "You hear a dying howl.\n";
      }

    } else {
      // end cave has no living monster
      return "You shoot an arrow and there is no response.\n";
    }

  }
  
  @Override
  public String detectMonster(Node node) {
    ArrayList<Node> neighborNodes = new ArrayList<>();
    for (Node n : this.nodeList) {
      if (getLength(node, n) <= 2
          && getLength(node, n) != 0
          && n.getType() == Type.cave) {
        neighborNodes.add(n);
      }
    }
    ArrayList<Node> monsterNodes = new ArrayList<>();
    for (Node n : neighborNodes) {
      if (n.getMonster() != null && n.getMonster().getInjury() < 2) {
        monsterNodes.add(n);
      }
    }
    if (monsterNodes.size() == 0) {
      return " ";
    }
    if (monsterNodes.size() == 1) {
      if (getLength(node, monsterNodes.get(0)) == 1) {
        return "Danger! You smell something terrible.\n";
      } else {
        return "Warning! You smell something strange.\n";
      }
    }
    if (monsterNodes.size() >= 2) {
      for (Node n : monsterNodes) {
        if (getLength(node, n) == 1) {
          return "Danger! You smell something terrible.\n";
        }
      }
      return "Warning! You smell something strange.\n";
    }
    return " ";
  }
  
  @Override
  public List<String> movePlayerToEnd() {
    // this method is not used in project 4 and 5
    this.record.add("\nThe player starts at: (" + this.player1.getX() + ", " + this.player1.getY() 
                      + ")\n");
       
    Node node1 = this.nodeList.get(this.start1);
  
    this.nodePath.add(node1);
    
    boolean breakFlag = false;
    do {  
        
      String direction = chooseDirection(node1);
      
      
      Node node2 = moveToNext(node1, direction);
      // if we move back, then choose direction again, unless node1 has only 1 direction 
      // that means a dead end, so we have to return
      if (this.nodePath.size() - 2 >= 0) {
        if (this.nodePath.get(this.nodePath.size() - 2).getX() == node2.getX() 
            && this.nodePath.get(this.nodePath.size() - 2).getY() == node2.getY()) {
          if (node1.getDirection().size() != 1) {
            continue;
          }
        }
      }
      // player moves to node2 
      this.nodePath.add(node2);
      // change the player's location
      this.player1.setX(node2.getX());
      this.player1.setY(node2.getY());
      
      // record the information in this move
      // current position is at node1
      this.record.add("\n" + this.player1.getName() + " is at: (" + node1.getX() + ", " 
                      + node1.getY() + ")\n");
      
      // treasure in current position: node1
      if (node1.getTreasure().size() > 0) {
        ArrayList<String> treasureList = new ArrayList<>();
        for (Itreasure t : node1.getTreasure()) {
          treasureList.add(t.getName());
        }
        this.record.add("Treasure at (" + node1.getX() + ", " + node1.getY() + "): " 
                        + String.join(",", treasureList) + "\n");
        
      } else {
        this.record.add("This node does not have any treasure.\n");
      }
      
      // player's current treasure
      this.record.add(describePlayer());
      
      // player picks up treasure in node1 if there is any
      if (node1.getTreasure().size() > 0) {
        this.record.add(this.player1.getName() + " picks up treasure at (" + node1.getX() + ", "
                        + node1.getY() + ")\n");
        this.player1.setTreasure(node1.getTreasure());
        node1.clearTreasure();
      }
      
      
      // possible directions
      this.record.add("Possible directions: " + String.join(",", node1.getDirection()) + "\n");
      
      this.record.add("Moving direction: " + direction + "\n");
    
      
      // if the player reaches the end
      if (node2.getX() == this.nodeList.get(end).getX() 
          && node2.getY() == this.nodeList.get(end).getY()) {
        this.record.add("\nThe player is at: (" + node2.getX() + ", " + node2.getY() + ")\n");
        
        if (node2.getTreasure().size() > 0) {
          ArrayList<String> treasureList = new ArrayList<>();
          for (Itreasure t : node2.getTreasure()) {
            treasureList.add(t.getName());
          }
          this.record.add("Treasure at (" + node2.getX() + ", " + node2.getY() + "): " 
                          + String.join(",", treasureList) + "\n");
          
        } else {
          this.record.add("This node does not have any treasure.\n");
          
        }
        this.record.add(describePlayer());
        this.record.add("Possible directions: " + String.join(",", node2.getDirection()) + "\n");
        
        if (node2.getTreasure().size() > 0) {
          this.record.add(this.player1.getName() + " picks up treasure at (" + node2.getX() + ", "
                          + node2.getY() + ")\n");
          this.player1.setTreasure(node2.getTreasure());
          node2.clearTreasure();
        }
        this.record.add("In the end, " + describePlayer() + "\n");
        this.record.add("The player has reached the end node.\n");
        
        breakFlag = true;
        break;
      }
      
      node1 = node2;
      
    } while (breakFlag = true);
    
    return this.record;
  }

  @Override
  public Iplayer getPlayer() {
    if (this.mode) {
      return this.player1;
    } else {
      if (this.turn == Turn.A) {
        return this.player1;
      } else {
        return this.player2;
      }
    }
  }

  @Override
  public String getPlayerName() {
    if (this.mode) {
      return this.player1.getName();
    } else {
      if (this.turn == Turn.A) {
        return this.player1.getName();
      } else {
        return this.player2.getName();
      }
    }
  }

  @Override
  public int getPlayerx1() {
    return this.player1.getX();
  }
  
  @Override
  public int getPlayery1() {
    return this.player1.getY();
  }
  
  @Override
  public List<Node> getNodeList() {
    return this.nodeList;
  }
  
  @Override
  public Set<Node> getNodeHistory() {
    return this.nodeHistory;
  }
  
  @Override
  public int getCaveWithTreasureNum() {
    int count = 0;
    for (Node n : this.nodeList) {
      if (n.getTreasure().size() > 0) {
        count += 1;
      }
    }
    return count;
  }
  
  @Override
  public int getCaveNum() {
    int count = 0;
    for (Node n : this.nodeList) {
      if (n.getType().toString().equals("cave")) {
        count += 1;
      }
    }
    return count;
  }
  
  @Override
  public String toString() {
    List<String> rows = new ArrayList<>();
    for (int i = 0; i < this.rows; i++) {
      List<String> firstRow = new ArrayList<>();  
      for (int j = 0; j < this.columns; j++) {
        Node node = this.nodeList.get(i * this.columns + j);   
        if (node.getDirection().contains("north")) {
          firstRow.add(" | ");
        } else {
          firstRow.add("   ");
        }
      }
      rows.add(String.join("", firstRow));
      
      List<String> secondRow = new ArrayList<>();
      for (int j = 0; j < this.columns; j++) {
        Node node = this.nodeList.get(i * this.columns + j); 
        if (node.getType().toString().equals("cave")) {
          if (node.getDirection().contains("east") 
              && node.getDirection().contains("west")) {
            secondRow.add("-O-");
          } else if (node.getDirection().contains("east")) {
            secondRow.add(" O-");
          } else if (node.getDirection().contains("west")) {
            secondRow.add("-O ");
          } else {
            secondRow.add(" O ");
          }
        } else {
          if (node.getDirection().contains("east") 
              && node.getDirection().contains("west")) {
            secondRow.add("-·-");
          } else if (node.getDirection().contains("east")) {
            secondRow.add(" ·-");
          } else if (node.getDirection().contains("west")) {
            secondRow.add("-· ");
          } else {
            secondRow.add(" · ");
          }
        }
      }
      rows.add(String.join("", secondRow));
      
      List<String> thirdRow = new ArrayList<>();
      if (i == this.rows - 1) {
        for (int j = 0; j < this.columns; j++) {
          Node node = this.nodeList.get(i * this.columns + j);
          if (node.getDirection().contains("south")) {
            thirdRow.add(" | ");
          } else {
            thirdRow.add("   ");
          }
        }
        rows.add(String.join("", thirdRow));
      }
      
    }
    
    return String.join("\n", rows);
  }

  @Override
  public Node getStart() {
    if (this.mode) {
      return this.nodeList.get(this.start1);
    } else {
      if (this.turn == Turn.A) {
        return this.nodeList.get(this.start1);
      } else {
        return this.nodeList.get(this.start2);
      }
    }
  }
  
  @Override
  public Node getEnd() {
    return this.nodeList.get(this.end);
  }
  
  @Override
  public int getLength(Node start, Node end) {
    // set all nodes' distance to infinity
    for (Node n : this.nodeList) {
      n.setDistance(Double.POSITIVE_INFINITY);
    }
    // all nodes' default distance is infinity, set start node's distance to zero
    start.setDistance(0);
    // workList stores nodes that are calculated at present
    ArrayList<Node> workList = new ArrayList<>();
    workList.add(start);
    while (workList.size() != 0) {
      Node current = workList.get(0);
      // get all directions of current node
      List<String> directions = current.getDirection();
      // find all neighbors of current node
      ArrayList<Node> neighbor = new ArrayList<>();
      for (String s : directions) {
        int x = 0;
        int y = 0;
        if ("north".equals(s)) {
          if (current.getX() == 0) {
            x = this.rows - 1;
          } else {
            x = current.getX() - 1;
          }
          y = current.getY();
        }
        if ("south".equals(s)) {
          if (current.getX() == this.rows - 1) {
            x = 0;
          } else {
            x = current.getX() + 1;
          }
          y = current.getY();
        }
        if ("east".equals(s)) {
          if (current.getY() == this.columns - 1) {
            y = 0;
          } else {
            y = current.getY() + 1;
          }
          x = current.getX();
        }
        if ("west".equals(s)) {
          if (current.getY() == 0) {
            y = this.columns - 1;
          } else {
            y = current.getY() - 1;
          }
          x = current.getX();
        }
        for (Node node : this.nodeList) {
          if (node.getX() == x && node.getY() == y) {
            neighbor.add(node);
            break;
          }
        }
      }
      // remove current node from workList
      workList.remove(0);
      for (Node node : neighbor) {
        // calculate every neighbor's distance
        if (node.getDistance() == Double.POSITIVE_INFINITY) {
          node.setDistance(current.getDistance() + 1);
          // add every neighbor to workList
          workList.add(node);
        }
  
      }
    }
    
    return (int) end.getDistance();
  }
  
  @Override
  public List<String> movePlayerAll() {
    
    ArrayList<Node> nodePath = new ArrayList<>();
    this.allrecord.add("\nThe player " + this.player1.getName() + " starts at: (" 
                       + this.player1.getX() + ", " + this.player1.getY() + ")\n");
    Node node1 = this.nodeList.get(this.start1);
    this.nodeHistory.add(node1);
    nodePath.add(node1);
  
    do {  
  
      String direction = chooseDirection(node1);
      Node node2 = moveToNext(node1, direction);
      // if we move back, then choose direction again, unless node1 has only 1 direction 
      // that means a dead end, so we have to return
      if (nodePath.size() - 2 > 0) {
        if (nodePath.get(nodePath.size() - 2).getX() == node2.getX() 
            && nodePath.get(nodePath.size() - 2).getY() == node2.getY()) {
          if (node1.getDirection().size() != 1) {
            continue;
          }
        }
      }
      // player moves to node2
      this.nodeHistory.add(node2);      
      nodePath.add(node2);
      // change the player's location
      this.player1.setX(node2.getX());
      this.player1.setY(node2.getY());
      
      
      // record the information in this move
      // player's current position is at node1
      this.allrecord.add("\n" + this.player1.getName() + " is at: (" + node1.getX() 
                         + ", " + node1.getY() + ")\n");
      
      // treasure in current position: node1
      if (node1.getTreasure().size() > 0) {
        ArrayList<String> treasureList = new ArrayList<>();
        for (Itreasure t : node1.getTreasure()) {
          treasureList.add(t.getName());
        }
        this.allrecord.add("Treasure at (" + node1.getX() + ", " + node1.getY() + "): " 
                           + String.join(",", treasureList) + "\n");
        
      } else {
        this.allrecord.add("This node does not have any treasure.\n");
      }
      // player's current treasure
      this.allrecord.add(describePlayer());
      
      
      // player picks up treasure in node1 if there is any
      if (node1.getTreasure().size() > 0) {
        this.allrecord.add(this.player1.getName() + " picks up treasure at (" + node1.getX() 
                           + ", " + node1.getY() + ")\n");
        this.player1.setTreasure(node1.getTreasure());
        node1.clearTreasure();
      }
      
      // possible directions
      this.allrecord.add("Possible directions: " + String.join(",", node1.getDirection()) + "\n");
      
      this.allrecord.add("Moving direction: " + direction + "\n");
      
      
      // if the player traversed all the nodes
      if (this.nodeHistory.size() == this.columns * this.rows) {
        this.allrecord.add("\nThe player is at: (" + node2.getX() + ", " + node2.getY() + ")\n");
        
        if (node2.getTreasure().size() > 0) {
          ArrayList<String> treasureList = new ArrayList<>();
          for (Itreasure t : node2.getTreasure()) {
            treasureList.add(t.getName());
          }
          this.allrecord.add("Treasure at (" + node2.getX() + ", " + node2.getY() + "): " 
                             + String.join(",", treasureList) + "\n");
         
        } else {
          this.allrecord.add("This node does not have any treasure.\n");
        }
        this.allrecord.add(describePlayer());
        this.allrecord.add("Possible directions: " + String.join(",", node2.getDirection()) 
                           + "\n");
        
        if (node2.getTreasure().size() > 0) {
          this.allrecord.add(this.player1.getName() + "picks up treasure at (" + node2.getX() 
                             + ", " + node2.getY() + ")\n");
          this.player1.setTreasure(node2.getTreasure());
          node2.clearTreasure();
        }
        this.allrecord.add("In the end, " + describePlayer() + "\n");
        this.allrecord.add("The player has traversed all nodes.\n");
        
        break;
      }
      
      node1 = node2;
      
    } while (this.nodeHistory.size() <= this.columns * this.rows);
    
    return this.allrecord;
  }
  
  /**
   * This method allocates monsters to some node.
   * There is always one monster at the end cave.
   */
  private void allocateMonster() {
    // allocate a monster to the end cave
    this.nodeList.get(this.end).setMonster();
    
    // allocate the rest of monsters to cave
    // indexList is the list of indexes of caves
    ArrayList<Integer> indexList = new ArrayList<>();
    for (int i = 0; i < this.nodeList.size(); i++) {
      if (this.nodeList.get(i).getType() == Type.cave) {
        indexList.add(i);
      }
    }

    // remove start cave, remove the end cave
    indexList.remove(Integer.valueOf(this.start1));
    if (!this.mode) {
      indexList.remove(Integer.valueOf(this.start2));
    }
    indexList.remove(Integer.valueOf(this.end));

    // get the minimum from monsterNum - 1 and cave number
    int loopNum = Math.min(this.monsterNum - 1, indexList.size());

    // allocate monsters to caves
    for (int i = 0; i < loopNum; i++) {
      int randomIndex = new Random().nextInt(indexList.size());
      this.nodeList.get(indexList.get(randomIndex)).setMonster();
      indexList.remove(randomIndex);
    }
    
    // record the indexes of caves that have monster in it 
    this.monsterIndexes = new ArrayList<>();
    for (int i = 0; i < this.nodeList.size(); i++) {
      if (this.nodeList.get(i).getMonster() != null) {
        this.monsterIndexes.add(i);
      }
    }
    
  }

  /**
   * This method chooses the start and end node cave.
   */
  private void chooseStartEnd() {
    
    // get a list of cave numbers
    ArrayList<Integer> caveNum = new ArrayList<>();
    for (int i = 0; i < this.nodeList.size(); i++) {
      if (this.nodeList.get(i).getType().toString().equals("cave")) {
        caveNum.add(i);
      }
    }
    int length = 0;
    int loopNum = 0;
    // randomly choose caves from the dungeon, calculate the length between 
    // start and end node, if the length >= 5, we choose the two nodes
    ArrayList<List<Integer>> caveNumber = new ArrayList<>(); 
    do {
      // randomly choose two caves
      int caveNum1 = new Random().nextInt(caveNum.size());
      int caveNum2 = new Random().nextInt(caveNum.size());
      
      // if the two caves are the same, choose again
      if (caveNum1 == caveNum2) {
        continue;
      } 
      // if the two caves are chosen before, choose again
      for (List list : caveNumber) {
        if (list.get(0).equals(caveNum1) && list.get(1).equals(caveNum2)) {
          continue;
        }
        if (list.get(0).equals(caveNum2) && list.get(1).equals(caveNum1)) {
          continue;
        }
      }
      
      List<Integer> combination = List.of(caveNum1, caveNum2);
      caveNumber.add(combination);
      
      Node start = this.nodeList.get(caveNum.get(caveNum1));
      Node end = this.nodeList.get(caveNum.get(caveNum2));
      for (Node n : this.nodeList) {
        n.setDistance(Double.POSITIVE_INFINITY);
      }
      length = getLength(start, end);
      if (length >= 5) {
        this.start1 = caveNum.get(caveNum1);
        this.end = caveNum.get(caveNum2);
        break;
      }
      loopNum += 1;
      if (loopNum > (caveNum.size() * (caveNum.size() - 1)) / 2) {
        throw new IllegalArgumentException("Do not find the available start and end node, "
                                           + "please try again or check whether the dungeon is"
                                           + " too samll.");
      }
    } while (length < 5);
  }

  /**
   * This method randomly chooses a direction for the given node.
   * @param node the node we deal with.
   * @return a string represents the direction.
   */
  private String chooseDirection(Node node) {
    int index = new Random().nextInt(node.getDirection().size());
    return node.getDirection().get(index);
  }
  
  /**
   * This method builds a dungeon.
   * @param wrapped  a boolean to determine whether the dungeon is wrapped, 
   *                 true means a wrapping dungeon, false otherwise
   * @param allEdges a set containing all possible edges in this dungeon 
   */
  private void buildDungeon(boolean wrapped, Set<Set<Node>> allEdges) {
    // the set to add interconnectivity
    Set<Set<Node>> leftover = new HashSet<>(); 
    // a list of sets storing temporary sets in this dungeon
    ArrayList<Set<Node>> tempSetList = new ArrayList<>(); 
    // a list of sets containing all edges in a non-wrapping dungeon
    ArrayList<Set<Node>> setList = new ArrayList<>(allEdges);
    
    // if row/column is 2, and it's a wrapped dungeon, then we must add missing edges to setList
    if (wrapped && this.rows == 2) {
      for (int i = 0; i < this.columns; i++) {
        Set<Node> missingEdge = new HashSet<>();
        Node first = this.nodeList.get(i);
        Node second = this.nodeList.get(i + this.columns);
        missingEdge.add(first);
        missingEdge.add(second);
        setList.add(missingEdge);
      }
    }
    if (wrapped && this.columns == 2) {
      for (int i = 0; i < this.rows; i++) {
        Set<Node> missingEdge = new HashSet<>();
        Node first = this.nodeList.get(2 * i);
        Node second = this.nodeList.get(2 * i + 1);
        missingEdge.add(first);
        missingEdge.add(second);
        setList.add(missingEdge);
      }
    }

    
    do {
      // randomly choose an edge
      int randomIndex = new Random().nextInt(setList.size());
      Set<Node> randomSet = setList.get(randomIndex);
      switch (getNumberOfNodes(randomSet, tempSetList)) {
        case 0:
          // if both nodes are not in any set in tempSetList, change the field direction 
          // in both nodes, add the set to tempSetList
          changeDirection(randomSet);
          tempSetList.add(randomSet);
          break;
        case 1:
          // if 1 node is in set "S" in tempSetList,change the field direction in both nodes
          // add the absent one to "S"
          changeDirection(randomSet);
          tempSetList = addOneNode(randomSet, tempSetList);
          break;
        case 2:
          // if two nodes are in different sets in tempSetList, merge the two sets
          changeDirection(randomSet);
          tempSetList = mergeSet(randomSet, tempSetList);
          break;
        case 3:
          // if two nodes are in the same set in tempSetList, add this set to leftover
          leftover.add(randomSet);
          break;
        default:
          // in default, do nothing
          break;    
      }
      setList.remove(randomIndex);
    } while (setList.size() > 0);
        
    // according to the interconnectivity, build extra edges
    ArrayList<Set<Node>> leftoverList = new ArrayList<>(leftover);
    int extraNum = 0;
    while (extraNum < Math.min(this.interconnect, leftoverList.size())) {
      int randomIndex = new Random().nextInt(leftoverList.size());
      Set<Node> randomSet = leftoverList.get(randomIndex); 
      changeDirection(randomSet);  
      leftoverList.remove(randomIndex);
      extraNum += 1;
    }
    
    // go through all the nodes, assign the type parameter and treasure to each node
    int caveNum = 0;
    ArrayList<Integer> indexList = new ArrayList<>();
    for (int i = 0; i < this.nodeList.size(); i++) {
      if (this.nodeList.get(i).getDirection().size() == 2) {
        this.nodeList.get(i).setType(Type.tunnel);
      } else {
        this.nodeList.get(i).setType(Type.cave);
        indexList.add(i);
        caveNum += 1;
      }
    }
    indexList.removeAll(Collections.singletonList(null));    
    int caveWithTreasure = caveNum * this.percentage / 100;
    int addedCaves = 0;
    do {
      int randomIndex = new Random().nextInt(indexList.size());
      // a cave can have at most 3 pieces of treasure
      int treasureNum = new Random().nextInt(3) + 1;
      for (int j = 0; j < treasureNum; j++) {
        int chooseType = new Random().nextInt(3);
        if (chooseType == 0) {
          Itreasure diamonds = new Diamonds();
          this.nodeList.get(indexList.get(randomIndex)).setTreasure(diamonds);
        }
        if (chooseType == 1) {
          Itreasure rubies = new Rubies();
          this.nodeList.get(indexList.get(randomIndex)).setTreasure(rubies);;
        }
        if (chooseType == 2) {
          Itreasure sapphires = new Sapphires();
          this.nodeList.get(indexList.get(randomIndex)).setTreasure(sapphires);
        }
      }
      addedCaves += 1;
      indexList.remove(randomIndex);
    } while (addedCaves < caveWithTreasure); 
    
    // record the indexes of caves that have treasure
    this.treasureRecord = new ArrayList<>();
    this.treasureIndexes = new ArrayList<>();
    for (int i = 0; i < this.nodeList.size(); i++) {
      if (this.nodeList.get(i).getTreasure().size() > 0) {
        this.treasureIndexes.add(i);
        this.treasureRecord.add(this.nodeList.get(i).getTreasure());
      }
    }
    
    // assign arrows to caves and tunnels, the number of arrows is calculated by
    // the percentage of caves with treasure
    int nodeWithArrow = this.nodeList.size() * this.percentage / 100;
    this.arrowIndexes = new ArrayList<>();
    do {
      int randomIndex = new Random().nextInt(this.nodeList.size());
      if (this.arrowIndexes.contains(randomIndex)) {
        continue;
      }
      this.arrowIndexes.add(randomIndex);    
    } while (this.arrowIndexes.size() < nodeWithArrow);
        
    for (int i : this.arrowIndexes) {
      this.nodeList.get(i).setArrow(1);
    }
    
  }
  
  /**
   * This method gets the number of nodes in the tempSetList.
   * @param inputSet    a random selected set, containing two nodes
   * @param tempSetList a list of sets, each set is a temporary set in the dungeon
   * @return the number of nodes in the tempSetList
   */
  private int getNumberOfNodes(Set<Node> inputSet, ArrayList<Set<Node>> tempSetList) {
    ArrayList<Node> inputList = new ArrayList<>(inputSet);
    Node one = inputList.get(0);
    Node two = inputList.get(1);
    int count = 0;

    for (Set<Node> j : tempSetList) {
      if (j.contains(one) && j.contains(two)) {
        return 3;
      }
      if (j.contains(one) | j.contains(two)) {
        count += 1;
      }
    }
    return count;
  }
  
  /**
   * This method is used when two nodes in the inputSet are in different set in tempSetList.
   * It merges the two set into one.
   * @param inputSet    a random selected set, containing two nodes
   * @param tempSetList a list of sets, each set is a temporary set in the dungeon
   * @return an ArrayList of merged set
   */
  private ArrayList<Set<Node>> mergeSet(Set<Node> inputSet, ArrayList<Set<Node>> tempSetList) {
    ArrayList<Node> inputList = new ArrayList<>(inputSet);
    ArrayList<Integer> indexes = new ArrayList<>();
    for (int i = 0; i < tempSetList.size(); i++) {
      if (tempSetList.get(i).contains(inputList.get(0)) 
          | tempSetList.get(i).contains(inputList.get(1)))  {
        indexes.add(i);
      }
    }
    Set<Node> set1 = tempSetList.get(indexes.get(0));
    Set<Node> set2 = tempSetList.get(indexes.get(1));
    Set<Node> setAdded = new HashSet<>();
    setAdded.addAll(set1);
    setAdded.addAll(set2);
    tempSetList.remove(set1);
    tempSetList.remove(set2);
    tempSetList.add(setAdded);
    return tempSetList;
  }
  

  /**
   * This method changes the direction field of nodes in the input set.
   * @param inputSet the input set.
   * @return a set of nodes.
   */
  private void changeDirection(Set<Node> inputSet) {
    ArrayList<Node> inputList = new ArrayList<>(inputSet);
    Node one = inputList.get(0);
    Node two = inputList.get(1);
    boolean changed = false;
    if (one.getX() - two.getX() == 1) {
      if (one.getDirection().contains("north")) {
        one.setDirection("south");
        two.setDirection("north");
      } else { // these two are original
        one.setDirection("north");
        two.setDirection("south");
      }
      changed = true;
    }
    if (one.getX() - two.getX() == -1) {
      if (one.getDirection().contains("south")) {
        one.setDirection("north");
        two.setDirection("south");
      } else {
        one.setDirection("south");
        two.setDirection("north");  
      }
      changed = true;
    }
    if (one.getY() - two.getY() == 1) {
      if (one.getDirection().contains("west")) {
        one.setDirection("east");
        two.setDirection("west");
      } else {
        one.setDirection("west");
        two.setDirection("east");  
      }
      changed = true;
    }
    if (one.getY() - two.getY() == -1) {
      if (one.getDirection().contains("east")) {
        one.setDirection("west");
        two.setDirection("east");
      } else {
        one.setDirection("east");
        two.setDirection("west");        
      }
      changed = true;
    }
    if (!changed) {
      if (one.getX() == 0 && two.getX() == this.rows - 1) {
        one.setDirection("north");
        two.setDirection("south");
      }
      if (one.getX() == this.rows - 1 && two.getX() == 0) {
        one.setDirection("south");
        two.setDirection("north");
      }
      if (one.getY() == 0 && two.getY() == this.columns - 1) {
        one.setDirection("west");
        two.setDirection("east");
      }
      if (one.getY() == this.columns - 1 && two.getY() == 0) {
        one.setDirection("east");
        two.setDirection("west");
      }
    }

    
  }
  
  
  /**
   * This method add one node in inputSet to the specific set (which contains the 
   * other node in inputSet) in tempSetList. 
   * @param inputSet    a random selected set, containing two nodes
   * @param tempSetList a list of sets, each set is a temporary set in the dungeon
   */
  private ArrayList<Set<Node>> addOneNode(Set<Node> inputSet, ArrayList<Set<Node>> tempSetList) {
    ArrayList<Node> inputList = new ArrayList<>(inputSet);
    Node one = inputList.get(0);
    Node two = inputList.get(1);
    for (Set<Node> set : tempSetList) {
      ArrayList<Node> list = new ArrayList<>(set);
      for (Node node : list) {
        if (node.getX() == one.getX() && node.getY() == one.getY()) {
          set.add(two);
        }
        if (node.getX() == two.getX() && node.getY() == two.getY()) {
          set.add(one);
        }
      }
    }
    return tempSetList;
  }

  @Override
  public int getPlayery2() {
    
    return this.player2.getY();
  }

  @Override
  public int getPlayerx2() {
    return this.player2.getX();
  }

}
