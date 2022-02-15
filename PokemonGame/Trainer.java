import java.util.*;
import java.awt.Point;

/**
* Trainer class
*/
public class Trainer extends Entity { //beginning of class Trainer

  private int money;
  private int potions;
  private int pokeballs;
  private Point loc;
  private ArrayList<Pokemon> pokemon;

  /**
   * Initialize Trainer with a name, money, potions, pokeballs, a pokemon, and location
   * @param n trainer name
   * @param p starter pokemon
   */
  public Trainer(String n, Pokemon p){ //beginning of method Trainer (constructor)
    super(n, 25, 25);
    this.money = 25;
    this.potions = 1;
    this.pokeballs = 5;
    pokemon = new ArrayList<Pokemon>();
    pokemon.add(p);
    this.loc = Map.getInstance().findStart();
  } //end of method Trainer (constructor)

  /**
   * Returns money the trainer currently has
   * @return trainer's current ammount of money
   */
  public int getMoney(){ //beginning of method getMoney
    return money; 
  } //end of method getMoney

  /**
   * Returns true if trainer can spend money on given item, false otherwise
   * @param amt cost of the item
   * @return true if trainer can spend money on given item, false otherwise
   */
  public boolean spendMoney(int amt){ //beginning of method spendMoney
     if (money >= amt) {
       money -= amt;
       return true;
     }
     else {
       return false;
     }
  } //end of method spendMoney

  /**
   * Adds given amount of money to trainer's current amount of money
   * @param amt given amount of money
   */
  public void receiveMoney(int amt){ //beginning of method receiveMoney
    money += amt; 
  } //end of method receiveMoney

  /**
   * Returns true if trainer has potion to use, false otherwise
   * @return true if trainer has potion to use, false otherwise
   */
  public boolean hasPotion(){ //beginning of method hasPotion
    return (potions > 0); 
  } //end of method hasPotion

  /**
   * Adds 1 potion to trainer's current amount of potions
   */
  public void receivePotion(){ //beginning of method receivePotion
    potions += 1; //adds one potion to user's inventory
  } //end of method receivePotion

  /**
   * Removes 1 potion from trainer's current amount of potions to heal specified pokemon from trainer
   * @param pokeIndex specify trainer's pokemon to heal
   */
  public void usePotion(int pokeIndex){ //beginning of method usePotion
    pokemon.get(pokeIndex).heal(); 
    potions -= 1;
  } //end of method usePotion

  /**
   * Returns true if trainer has pokeballs to use, false otherwise
   * @return true if trainer has pokeball to use, false otherwise
   */
  public boolean hasPokeball(){ //beginning of method hasPohasPokeballtion
    return (pokeballs > 0);
  } //end of method hasPokeball

  /**
   * Adds 1 pokeball to current trainer's amount of pokeballs
   */
  public void receivePokeball(){ //beginning of method receivePokeball
    pokeballs += 1; 
  } //end of method receivePokeball

  /**
   * Returns true if trainer catches wild pokemon
   * @param p wild pokemon to be caught
   * @return true if trainer caught the wild pokemon, false otherwise
   */
  public boolean catchPokemon(Pokemon p){ //beginning of method catchPokemon
    Random rand = new Random();
    double catchChance = rand.nextDouble();
    double pokemonHP = 1 - ((double)p.getHP() / (double)p.getMaxHP());
    
    if (catchChance <= pokemonHP) {
      pokemon.add(p);
      pokeballs -= 1;
      return true;
    }
    pokeballs -= 1;
    return false;
  }

  /**
   * Returns current point of trainer on the map
   * @return loc the trainer's current location of the map
   */
  public Point getLocation(){ //beginning of method getLocation
    return loc; 
  } //end of method getLocation

  /**
   * Returns character the trainer will be on when going north
   * @return the character the trainer will be on when going north
   */
  public char goNorth(){ //beginning of method goNorth
    Point trainerLoc = this.getLocation();

    char updatedChar;
    int column = trainerLoc.x;
    int row = trainerLoc.y;

    Point updatedLocation = new Point(column - 1, row);
    updatedChar = Map.getInstance().getCharAtLoc(updatedLocation);

    if (updatedChar == 'B'){
      return updatedChar;
    }
    else {
      Map.getInstance().reveal(loc);
      loc = updatedLocation;
      return updatedChar;
    }
  } //end of method goNorth
  
  /**
   * Returns character the trainer will be on when going south
   * @return the character the trainer will be on when going south
   */
  public char goSouth(){ //beginning of method goSouth
    Point trainerLoc = this.getLocation();

    char updatedChar;
    int column = trainerLoc.x;
    int row = trainerLoc.y;

    Point updatedLocation = new Point(column + 1, row);
    updatedChar = Map.getInstance().getCharAtLoc(updatedLocation);

    if (updatedChar == 'B'){
      return updatedChar;
    }
    else { 
      Map.getInstance().reveal(loc);
      loc = updatedLocation;
      return updatedChar;
    }
  } //end of method goSouth

  /**
   * Returns character the trainer will be on when going east
   * @return the character the trainer will be on when going east
   */
  public char goEast(){ //beginning of method goEast
    Point trainerLoc = this.getLocation();

    char updatedChar;
    int column = trainerLoc.x;
    int row = trainerLoc.y;

    Point updatedLocation = new Point(column, row + 1);
    updatedChar = Map.getInstance().getCharAtLoc(updatedLocation);

    if (updatedChar == 'B'){
      return updatedChar;
    }
    else { 
      Map.getInstance().reveal(loc);
      loc = updatedLocation;
      return updatedChar;
    }
  } //end of method goEast

  /**
   * Returns character the trainer will be on when going west
   * @return the character the trainer will be on when going west
   */
  public char goWest(){ //beginning of method goWest
    Point trainerLoc = getLocation();

    char updatedChar;
    int column = trainerLoc.x;
    int row = trainerLoc.y;

    Point updatedLocation = new Point(column, row - 1);
    updatedChar = Map.getInstance().getCharAtLoc(updatedLocation);

    if (updatedChar == 'B'){
      return updatedChar;
    }
    else {
      Map.getInstance().reveal(loc);
      loc = updatedLocation;
      return updatedChar;
    }
  } //end of method goWest

  /**
   * Returns number of pokemon trainer currently has
   * @return number of pokemon trainer currently has
   */
  public int getNumPokemon(){ //beginning of method getNumPokemon
    return pokemon.size(); 
  } //end of method getNumPokemon

  /**
   * Heals all pokemon the trainer currently has
   */
  public void healAllPokemon(){ //beginning of method healAllPokemon
    for (int i = 0; i < pokemon.size(); i++){
      pokemon.get(i).heal(); 
    }
  } //end of method healAllPokemon
  
  /**
   * buffs all pokemon the trainer currently has with more attack or hp randomly
   */
  public void buffAllPokemon(){ //beginning of method buffAllPokemon
  
    Random randomBuff = new Random();
    for (int i = 0; i < pokemon.size(); i++){
      int randBuffInt = randomBuff.nextInt(2 + 1 - 1) + 1;
      if (randBuffInt == 1){
        pokemon.set(i, new AttackUp(this.getPokemon(i)));
      }
      else{
        pokemon.set(i, new HpUp(this.getPokemon(i)));
      }
    }
  } //end of method buffAllPokemon

  /**
   * debuffs one of trainer's pokemon with less attack or hp randomly
   * @param index the index of pokemon in trainer's pokemon arraylist to debuff
   */
  public void debuffPokemon(int index){ //beginning of method debuffPokemon
    Random randomBuff = new Random();
    int randBuffInt = randomBuff.nextInt(2 + 1 - 1) + 1;
    if (randBuffInt == 1){
      pokemon.set(index, new AttackDown(this.getPokemon(index)));
    }
    else {
      pokemon.set(index, new HpDown(this.getPokemon(index)));
    }
  } //end of method debuffPokemon

  /**
   * Returns pokemon that the trainer specifies and has 
   * @param index the trainer's selected pokemon
   * @return pokemon that the trainer specifies and has 
   */
  public Pokemon getPokemon(int index){ //beginning of method getPokemon
    return pokemon.get(index);
  } //end of method getPokemon

  /**
   * Returns list of pokemon trainer curretnly has with the pokemon's name and hp
   * @return list of pokemon trainer curretnly has with the pokemon's name and hp
   */
  public String getPokemonList(){ //beginning of method getPokemonList
    String pokemonList = "";
    for (int i = 0; i < pokemon.size(); i++){ 
      // 1. Pokemon Name HP: 
      pokemonList += (i + 1) + ". " + getPokemon(i).getName() + " HP: " + getPokemon(i).getHP() + "/" + getPokemon(i).getMaxHP() + "\n"; 
    }
    return pokemonList; //returns all pokemon user has in arraylist
  } //end of method getPokemonList

  /**
   * removes pokemon indicated
   * @param index the index of pokemon in trainer's pokemon arraylist to remove
   */
  public Pokemon removePokemon(int index){ //beginning of method removePokemon
    return pokemon.remove(index);
  } //end of method removePokemon

  /**
   * Generates string for the object Trainer that includes their name, hp, money, potions, pokeballs, pokemon list, and map
   * @return string for the object Trainer that includes their name, hp, money, potions, pokeballs, pokemon list, and map
   */
  @Override
  public String toString(){ //beginning of method toString
    String pokemonInfo = "";
    pokemonInfo += super.toString() + "\n";
    pokemonInfo += "Money: " + money + "\n";
    pokemonInfo += "Potions: " + potions + "\n";
    pokemonInfo += "Poke Balls: " + pokeballs + "\n";
    pokemonInfo += "Pokemon\n" + "--------------------\n";
    pokemonInfo += getPokemonList() + "\n";
    pokemonInfo += "Map: \n";
    pokemonInfo += Map.getInstance().mapToString(loc);
    return pokemonInfo;
  } //end of method getPokemonList

}//end of class Trainer