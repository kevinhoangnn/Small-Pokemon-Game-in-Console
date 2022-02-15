/**
* AttackDown class
*/
public class AttackDown extends PokemonDecorator {
  /**
   * Decrease a pokemon's attack points by 1
   * @param p the pokemon object
   */
  public AttackDown(Pokemon p) {
    super(p, " -ATK", 0);
  }

  /**
   * Decrease the pokémon’s damage by 1 and concatenate
   * ‘-ATK’ in front of their name
   * @param atkType attack type ranging between basic or special
   * @return reduced atttack bonus
   */
  @Override
  public int getAttackBonus(int atkType) {
    // Subject to change: decreases the pokémon’s damage by 1 
    return super.getAttackBonus(atkType) - 1; 
  }
}