/**
* Pokemon Decorator class
*/
public abstract class PokemonDecorator extends Pokemon{
  private Pokemon pokemon;

  /* Note to self: override methods of the base decorator and 
  execute their behavior b4/after calling the parent method*/

  /**
   * Adds additional qualities/decorations to pokemons that have been buffed/debuffed
   * @param p pokemon object
   * @param extraName buff indicator
   * @param extraHp specifies if the pokemon object has gained a HP buff/debuff
   */
  public PokemonDecorator(Pokemon p, String extraName, int extraHp) {
    super(p.getName() + extraName, p.getHP() + extraHp, p.getMaxHP() + extraHp);
    pokemon = p;
  }

  /**
   * Calls on the getAttackMenu from pokemon to return the appropriate menu options for a pokemon based on the  elemental type
   * @param atkType attack type ranging between basic or special
   * @return menu with a list of possible moves based on the type of the pokemon
   */
  @Override
  public String getAttackMenu(int atkType) {
    return pokemon.getAttackMenu(atkType);
  }

  /**
   * Displays the number of items in the menu
   * @param atkType attack type ranging between basic or special
   * @return the number of menu items from attack menu
   */
  @Override
  public int getNumAttackMenuItems(int atkType) {
    return pokemon.getNumAttackMenuItems(atkType);
  }

  /**
   * Calls on the appropiate partial string based on the attack tyoe
   * @param atkType attack type ranging between basic or special
   * @param move attack move chosen
   * @return the corresponding partial string of the attack
   */
  @Override
  public String getAttackString(int atkType, int move) {
    return pokemon.getAttackString(atkType, move);
  }

  /**
   * Calculate damage dealt
   * @param atkType attack type ranging between basic or special
   * @param move attack move chosen
   * @return the damage dealt based on the attack type and move selected
   */
  @Override
  public int getAttackDamage(int atkType, int move) {
    return pokemon.getAttackDamage(atkType, move);
  }

  /**
   * Returns the attack multiplier for that class’s moves
   * @param p pokemon object
   * @param type elemental type of the pokemon object
   * @return attack multiplier
   */
  @Override
  public double getAttackMultiplier(Pokemon p, int type) {
    return pokemon.getAttackMultiplier(p, type);
  }

  /**
   * Returns the attack bonus that will be added to the total damage dealt
   * @param type elemental type of the pokemon object
   * @return attack bonus based on the type of the pokemon
   */
  @Override
  public int getAttackBonus(int type) {
    return pokemon.getAttackBonus(type);
  }

  /**
   * Returns the integer corresponding to the elemental type of the pokémon
   * @return an integer between 1 - 3 (Fire = 0, Water = 1, Grass = 2)
   */
  @Override
  public int getType() {
    return pokemon.getType();
  }
}