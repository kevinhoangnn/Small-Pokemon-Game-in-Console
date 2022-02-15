import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Pokemon Generator class
*/
public class PokemonGenerator{ //beginning of PokemonGenerator
  
  private HashMap<String, String> pokemon;
  private static PokemonGenerator instance = null;
  
  /**
  * Initialize the Pokemon Generator constructor to read given PokemonList of Pokemon names and their types into a hashmap
  */
  private PokemonGenerator(){ //beginning of pokemonGenerator constructor
    try {
      pokemon = new HashMap<String, String>();
      File pokemonList = new File("PokemonGame/PokemonList.txt");
      Scanner filescan = new Scanner(pokemonList);

      String regularExpr = "(\\w+),(\\w+)";
      Pattern p = Pattern.compile(regularExpr);
      Matcher match;

      while (filescan.hasNextLine()){
        String currentLine = filescan.nextLine();
        match = p.matcher(currentLine);
        if (match.find()) {
          pokemon.put(match.group(1), match.group(2));
        }
      }
      filescan.close();
    }
    catch (FileNotFoundException fnf) {
      System.out.println("Something went wrong: File not found");
    }  
  } //ending of pokemonGenerator constructor

  /**
  * Makes Pokemon Generator a singleton design
  * @return PokemonGenerator returns only one instance of a pokemon generator
  */
  public static PokemonGenerator getInstance(){  //beginning of getInstance
    if (instance == null) {
      instance = new PokemonGenerator();
    }
    return instance;
  }  //ending of getInstance

  /**
  * Generates random pokemon from pokemon hashmap and buffs generated pokemon based on level
  * @param level the level the player is currently on
  * @return Pokemon returns randomly generated pokemon 
  */
  public Pokemon generateRandomPokemon(int level){ //beginning of generateRandomPokemon
    Random rand = new Random(); 
    int randomPokemonName = rand.nextInt(pokemon.size());

    String[] pokemonNames = pokemon.keySet().toArray(new String[pokemon.size()]);
    String getPokemonName = pokemonNames[randomPokemonName];
    Pokemon pokemonConstructed = getPokemon(getPokemonName);

    for (int i = 1; i < level; i++) {
      pokemonConstructed = addRandomBuff(pokemonConstructed);
    }

    return pokemonConstructed; 
  } //ending of generateRandomPokemon
  
  /**
  * Generates pokemon based on name to find in pokemon hashmap
  * @param name the name of the pokemon to generate
  * @return Pokemon returns generated pokemon based off name
  */
  public Pokemon getPokemon(String name){ //beginning of getPokemon
    
    Pokemon constructedPokemon = null;

    Random rand = new Random();
    int randHP = rand.nextInt(25 - 20) + 20;

    switch(pokemon.get(name)) {
      case "Fire":
        constructedPokemon = new Fire(name, randHP, randHP);
        break;
      case "Water":
        constructedPokemon = new Water(name, randHP, randHP);
        break;
      case "Grass":
        constructedPokemon = new Grass(name, randHP, randHP);
        break;
    } 

    return constructedPokemon;
  }//ending of getPokemon

  /**
  * Adds random buff of health or attack to given pokemon
  * @param p the pokemon to randomly buff 
  * @return Pokemon returns new randomly buffed pokemon
  */
  public Pokemon addRandomBuff(Pokemon p){ //beginning of addRandomBuff

    Pokemon newBuffedPokemon = p;

    Random rand = new Random();
    int randBuff = rand.nextInt(2) + 1;

    switch(randBuff) {
      case 1:
        newBuffedPokemon = new HpUp(newBuffedPokemon);
        break;
      case 2:
        newBuffedPokemon = new AttackUp(newBuffedPokemon);
        break;
    }

    return newBuffedPokemon;
  } //ending of addRandomBuff

  /**
  * Adds random debuff of health or attack to given pokemon
  * @param p the pokemon to randomly debuff 
  * @return Pokemon returns new randomly debuffed pokemon
  */
  public Pokemon addRandomDebuff(Pokemon p){ //beginning of addRandomDebuff

    Pokemon newDebuffedPokemon = p;

    Random rand = new Random();
    int randDebuff = rand.nextInt(2) + 1;

    switch(randDebuff) {
      case 1:
        newDebuffedPokemon = new HpDown(newDebuffedPokemon);
        break;
      case 2:
        newDebuffedPokemon = new AttackDown(newDebuffedPokemon);
        break;
    }

    return newDebuffedPokemon;
  } //ending of addRandomDebuff

} //ending of PokemonGenerator