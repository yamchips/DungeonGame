package dungeon;

/**
 * This class represents the monster in the cave.
 * @author Fan Wu
 * @field injury The number of injury that the monster has.
 */
public class Monster implements Imonster {
  private int injury;
  
  /**
   * Constructor.
   */
  public Monster() {
    this.injury = 0;
  }
    
  @Override
  public int getInjury() {
    return this.injury;
  }

  @Override
  public void addInjury() {
    this.injury += 1;
  }

}
