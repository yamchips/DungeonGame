package player;

import java.util.ArrayList;
import java.util.List;
import treasure.Itreasure;

/**
 * This class represents a player.
 * @author Fan Wu
 * @field name        the name of the player.
 * @field CoordinateX the x coordinate.
 * @field CoordinateY the y coordinate.
 * @field treasure    a list of treasure collected by the player.
 * @field arrowNum    the number of arrows that the player has
 * @field state       the state of player, alive or dead
 *
 */
public class Player implements Iplayer {
  private String name;
  private int positionx;
  private int positiony;
  private List<Itreasure> treasure;
  private int arrowNum;
  private State state;
  
  /**
   * Constructor.
   * @param name the name of the player.
   */
  public Player(String name) {
    this.name = name;
    this.treasure = new ArrayList<>();
    this.arrowNum = 3;
    this.state = State.alive;
  }
  
  @Override
  public void setX(int x) {
    this.positionx = x;
  }
  
  @Override
  public void setY(int y) {
    this.positiony = y;
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
  public String getName() {
    return this.name;
  }
  
  @Override
  public List<String> getTreasure() {
    ArrayList<String> treasureList = new ArrayList<>();
    for (Itreasure t : this.treasure) {
      treasureList.add(t.getName());
    }
    return treasureList;
  }
  
  @Override
  public void setTreasure(List<Itreasure> treasure) {
    this.treasure.addAll(treasure);
  }

  @Override
  public void pickupArrow() {
    this.arrowNum += 1;    
  }

  @Override
  public int getArrowNum() {
    return this.arrowNum;
  }

  @Override
  public void shootArrow() {
    this.arrowNum -= 1;
  }

  @Override
  public void setStateDead() {
    this.state = State.dead;
  }

  @Override
  public State getState() {
    return this.state;
  }
}
