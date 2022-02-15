/**
* HpDown class
*/
public class HpDown extends PokemonDecorator {
  /**
   * Decrease a pokemon’s hp and maxHp by
   * 1 point and concatenate ‘-HP’ in front of their name
   * @param p pokemon object
   */
  public HpDown(Pokemon p) {
    super(p, " -HP", -1);
  }
}