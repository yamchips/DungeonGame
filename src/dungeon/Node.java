package dungeon;

import java.util.ArrayList;
import java.util.List;
import treasure.Itreasure;

/**
 * This class represents a node in the dungeon.
 * @author Fan Wu
 * @field x          the x coordinate
 * @field y          the y coordinate
 * @field treasure   the treasure in this node
 * @field type       the type of this node
 * @field directions a list of directions
 * @field distance   the distance between this node and start node
 * @field monster    the monster in this node
 * @field arrowNum   the number of arrows in this node
 *
 */
public class Node implements Inode {
  private int positionx;
  private int positiony;
  private List<Itreasure> treasure;
  private Type type;
  private List<String> directions;
  private double distance;
  private Imonster monster;
  private int arrowNum;
  
  /**
   * Constructor.
   * @param x the x coordinate.
   * @param y the y coordiante.
   */
  public Node(int x, int y) {
    this.positionx = x;
    this.positiony = y;
    this.directions = new ArrayList<>();
    this.treasure = new ArrayList<>();
    this.distance = Double.POSITIVE_INFINITY;
    this.arrowNum = 0;
  }
  
  @Override
  public int getX() {
    return this.positionx;
  }
  
  @Override
  public int getY() {
    return this.positiony;
  }
  
  @Override
  public void setDirection(String str) {
    this.directions.add(str);
  }
  
  @Override
  public List<String> getDirection() {
    return this.directions;
  }
  
  @Override
  public void setType(Type type) {
    this.type = type;
  }
  
  @Override
  public Type getType() {
    return this.type;
  }
  
  @Override
  public void setTreasure(Itreasure treasure) {
    this.treasure.add(treasure);
  }
  
  @Override
  public void clearTreasure() {
    this.treasure = new ArrayList<>();
  }
  
  @Override
  public List<Itreasure> getTreasure() {
    return this.treasure;
  }

  @Override
  public double getDistance() {
    return this.distance;
  }

  @Override
  public void setDistance(double distance) {
    this.distance = distance;
  }

  @Override
  public void setMonster() {
    this.monster = new Monster();    
  }

  @Override
  public Imonster getMonster() {
    return this.monster;
  }

  @Override
  public void setArrow(int num) {
    this.arrowNum = num;    
  }

  @Override
  public int getArrow() {
    return this.arrowNum;
  }
}
