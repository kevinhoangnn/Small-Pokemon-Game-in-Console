/**
* AttackUp class
*/
public class AttackUp extends PokemonDecorator {
  /**
   * Raise the pokemon's attack by 1 - 2 points
   * @param p pokemon object
   */
  public AttackUp(Pokemon p) {
    super(p, " +ATK", 0);
  }

  /**
   * Increases a pokemon’s damage by 1-2 and concatenates
   * ‘+ATK’ in front of their name
   * @param atkType type of attack ranging between basic or special
   * @return an integer between 1 and 2 that will be added to the attack bonus dealt by the pokemon
   */
  @Override
  public int getAttackBonus(int atkType) {
    // Subject to change: increases a pokémon’s damage by 1-2
    return super.getAttackBonus(atkType) + (int) ((Math.random() * (2)) + 1);
  }  
}