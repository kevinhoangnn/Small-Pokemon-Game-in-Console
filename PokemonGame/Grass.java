import java.util.*;

/**
* Grass class
*/
public class Grass extends Pokemon { 

  /**
   * Base class for grass type pokemon objects
   * @param n name of the pokemon
   * @param h health randomly generated for the pokemon in PokemonGenerator
   * @param m max health of the pokemon
   */
  public Grass(String n, int h, int m) {
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
        attackMenu = "\n1. Vine Whip\n2. Razor Leaf\n3. Solar Beam";
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

    if (atkType == 1) {
      attackDescription = super.getAttackString(atkType, move);
    }
    
    else {
      attackDescription = "";
      switch(move) {
        case 1:
          attackDescription += "VINE-WHIPPED"; 
          break;

        case 2:
          attackDescription += "RAZOR-LEAFED";
          break;

        case 3:
          attackDescription += "SOLAR-BEAMED";
          break;
      }
    }

    return attackDescription;
  }

  /**
   * Returns the randomized damage for the chosen move
   * @param atkType attack type ranging between basic or special
   * @param move move performed by the pokemon object
   * @return randomly generated damage dealt based on the grass elemental type
   */
  @Override
  public int getAttackDamage(int atkType, int move) {
    Random rand = new Random();
    int damageDone = 0;

    if (atkType == 1) {
      damageDone = super.getAttackDamage(atkType, move);
    }
    
    else {
      switch(move) {
        // Vinewhip
        case 1:
          damageDone = rand.nextInt(3) + 1;
          break;

        // Razorleaf
        case 2:
          damageDone = rand.nextInt(3) + 2;
          break;

        // Solarbeam
        case 3:
          damageDone = rand.nextInt(6);
          break;
      }
    }

    return damageDone;    
  }

  /**
   * Returns the attack multiplier for all grass-based moves
   * @param p pokemon object
   * @param atkType attack type ranging between basic and special
   * @return corresponding attack multiplier for the pokemon
   */
  @Override
  public double getAttackMultiplier(Pokemon p, int atkType) {
    double attackMultiplier = 0;

    if (atkType == 1) {
      attackMultiplier = super.getAttackMultiplier(p, 1);
    }

    else {
      attackMultiplier = battleTable[this.getType()][p.getType()];
    }

    return attackMultiplier;
  }
}