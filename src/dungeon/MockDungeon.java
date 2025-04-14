package dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import player.Iplayer;
import player.Player;

/**
 * This is a mock dungeon model for initializing and testing.
 * @author Fan Wu
 *
 */
public class MockDungeon implements Idungeon {
  private StringBuffer log;
  private List<Node> nodeList;
  private Iplayer player1;
  
  public MockDungeon(StringBuffer log) {
    this.log = log;
  }
  
  @Override
  public String shoot(Node node, int distance, String direction) {
    this.log.append("shoot()\n");
    return "test";
  }

  @Override
  public Iplayer getPlayer() {
    this.player1 = new Player("testPlayer");
    this.playerEnter("testPlayer");
    
    return this.player1;
  }

  @Override
  public Node getLastNodeFromPath() {
    this.log.append("getLastNodeFromPath()\n");
    this.nodeList = getNodeList();
    return this.nodeList.get(0);
  }

  @Override
  public int getLength(Node start, Node end) {
    return 1;
  }

  @Override
  public List<Node> getNodeList() {
    this.nodeList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        Node newNode = new Node(i, j);
        this.nodeList.add(newNode);
      }
    }
    
    return this.nodeList;
  }

  @Override
  public void moveOneStep(Node node1, Node node2) {
    this.log.append("moveOneStep()\n");
  
  }

  @Override
  public void moveOneStep(Node node1, String direction) {
    

  }

  
  @Override
  public int getColumns() {
    return 10;
  }

  @Override
  public int getRows() {
    return 10;
  }

  @Override
  public List<Node> getNodePath() {
    return null;
  }

  @Override
  public String checkCurrentNode(Node node) {
    return null;
  }

  @Override
  public String detectMonster(Node node) {
    return null;
  }

  @Override
  public String getNodeTreasure(Node node) {
    return null;
  }

  @Override
  public String getPlayerName() throws IOException {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public String describePlayer() {
    return null;
  }

  @Override
  public Set<Node> getNodeHistory() {
    return null;
  }

  @Override
  public Node getStart() {
    return null;
  }

  @Override
  public Node getEnd() {
    return null;
  }

  @Override
  public int getCaveWithTreasureNum() {
    return 0;
  }

  @Override
  public int getCaveNum() {
    return 0;
  }

  @Override
  public int getPlayery1() {
    return 0;
  }

  @Override
  public int getPlayerx1() {
    return 0;
  }

  @Override
  public Idungeon startGameAgain() {
    
    return null;
  }

  @Override
  public Idungeon retrieveOriginModel() {
    
    return null;
  }

  @Override
  public void buildDungeonHelper(int rows, int columns, int inter, boolean wrap, int percent,
      int monsterNum, boolean mode) {
    

  }


  @Override
  public Node moveToNext(Node node, String direction) {
    
    return null;
  }

  @Override
  public void playerEnter(String playerName) {
    

  }

  @Override
  public List<String> movePlayerAll() {
    
    return null;
  }

  @Override
  public List<String> movePlayerToEnd() {
    
    return null;
  }

  @Override
  public void playerEnterTwo(String name1, String name2) {
   
    
  }

  @Override
  public boolean getMode() {
    return true;
  }

  @Override
  public Turn getTurn() {
    
    return Turn.A;
  }

  @Override
  public void setTurn(Turn turn) {
    
    
  }

  @Override
  public int getPlayery2() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getPlayerx2() {
    // TODO Auto-generated method stub
    return 0;
  }


}
