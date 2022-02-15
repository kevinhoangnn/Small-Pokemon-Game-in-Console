/**
* HpUp class
*/
public class HpUp extends PokemonDecorator {
  /**
   * Increase a pokemon’s hp and maxHp by
   * 1-2 points and concatenate ‘+HP’ in front of their name
   * @param p pokemon object
   */
  public HpUp(Pokemon p) {
    super(p, " +HP", (int) ((Math.random() * (2 - 1)) + 1));
  }
}