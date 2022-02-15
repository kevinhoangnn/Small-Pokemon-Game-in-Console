import java.util.*;

/**
* Water class
*/
public class Water extends Pokemon {
  /**
   * Base class for water type pokemon objects
   * @param n name of the pokemon
   * @param h health randomly generated for the pokemon in PokemonGenerator
   * @param m max health of the pokemon
   */
  public Water(String n, int h, int m) {
    super(n, h, m);
  }

  /**
   * Returns the basic and special attack menu string depending on the attack type
   * @param atkType attack type ranging between basic and special
   * @return attack menu interface based on the selected attack type
   */
  @Override
  public String getAttackMenu(int atkType) {
    String attackMenu = "";

    switch(atkType) {
      case 1:
        attackMenu = super.getAttackMenu(1); 
        break;
      case 2:
        attackMenu = "\n1. Water Gun\n2. Bubble Beam\n3. Waterfall";
        break;
    }
    return attackMenu;
  }

  /**
   * Returns the number of moves in the basic / special attack menu
   * @param atkType attack type ranging between basic and special
   * @return number of moves in the special menu (3)
   */
  @Override
  public int getNumAttackMenuItems(int atkType) {
    return 3;
  }

  /**
   * Returns the partial string for the chosen move
   * @param atkType attack type ranging between basic or special
   * @param move attack move performed by the pokemon object
   * @return partial string for each special move
   */
  @Override
  public String getAttackString(int atkType, int move) {
    String attackDescription = "";

    // Basic Attack
    if (atkType == 1) {
      attackDescription = super.getAttackString(atkType, move); // ex. slammed, tackled, punched
    }
    
    else {
      // Integrate type specific moves
      switch(move) {
        case 1:
          attackDescription += "WATER-GUNNED"; 
          break;

        case 2:
          attackDescription += "BUBBLE-BEAMED";
          break;

        case 3:
          attackDescription += "WATER-FALLED";
          break;
      }
    }

    return attackDescription;
  }

  /**
   * Returns the randomized damage for the chosen move
   * @param atkType attack type ranging between basic or special
   * @param move move performed by the pokemon object
   * @return randomly generated damage dealt based on the water elemental type
   */
  @Override
  public int getAttackDamage(int atkType, int move) {
    Random rand = new Random();
  
    int damageDone = 0;

    // Check for basic attack
    if (atkType == 1) {
      damageDone = super.getAttackDamage(atkType, move);
    }
    
    // Check for special attack
    else {
      switch(move) {
        // Watergun
        case 1:
          damageDone = rand.nextInt(4) + 2;
          break;

        // Bubblebeam
        case 2:
          damageDone = rand.nextInt(3) + 1;
          break;

        // Waterfall
        case 3:
          damageDone = rand.nextInt(4) + 1;
          break;
      }
    }

    return damageDone;
  }

  /**
   * Returns the attack multiplier for all water-based moves
   * @param p pokemon object
   * @param atkType attack type ranging between basic and special
   * @return corresponding attack multiplier for the pokemon
   */
  @Override
  public double getAttackMultiplier(Pokemon p, int atkType) {
    double attackMultiplier = 0;

    if (atkType == 1) {
      attackMultiplier = super.getAttackMultiplier(p, 1); // Note: atkType = 1 = basic attack
    }
    else {
      attackMultiplier = battleTable[this.getType()][p.getType()];
    }

    return attackMultiplier;
  }
}

