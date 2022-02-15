import java.util.Random;

/**Pokemon class*/
public abstract class Pokemon extends Entity{

  public static final double [][] battleTable = {{1, 0.5, 2}, {2, 1, 0.5}, {0.5, 2, 1}};

  /**
  * Initialize the constructor to make pokemon
  * @param n pokemon name
  * @param h current HP of pokemon
  * @param m maxHP of pokemon
  */
  public Pokemon(String n, int h, int m){
    super(n, h, m);
  }

  /**
   * Get the attack menu for the basic and special attack
   * @return a basic menu option for basic and special attack
   */
  public String getAttackTypeMenu() {
    return "1. Basic Attack\n2. Special Attack";
  }

  /**
   * Get the number of attack selections (basic or special)
   * @return int the number of attack selections (basic or special)
   */
  public int getNumAttackTypeMenuItems() {
    return 2;
  }

  /**
   * Get the attack menu for the basic attacks
   * Gets overridden based on atkType
   * @return a basic menu option for basic, unless overridden
   */
  public String getAttackMenu(int atkType) {
    String attackMenu = "\n1. Slam\n2. Tackle\n3. Punch";
    return attackMenu;
  }

  /**
   * Get the number of attack selections within the attack type of basic or special
   * @param atkType selection b/w basic or special
   * @return int the number of attack selections within the attack type of basic or special
   */
  public int getNumAttackMenuItems(int atkType) {
    return 3;
  }

  /**
   * Get the damage amount based on original damage, bonus damage from buffs, and attack multipliers from battle table
   * @param p the pokemon
   * @param atkType either basic or special attack
   * @param move the move within basic or special to make
   * @return int damage done by pokemon based on atkType and move within the atkType
   */
  public String attack(Pokemon p, int atkType, int move) {
    String attackDescription = "";

    // Calculate total damage
    int damageDone = this.getAttackDamage(atkType, move);
    int attackBonusDamage = this.getAttackBonus(atkType);

    
    double attackMultiplier = this.getAttackMultiplier(p, atkType);

    double totalDamageDone = (damageDone + attackBonusDamage) * attackMultiplier;
    if (totalDamageDone <= 0) {
      totalDamageDone = 0;
    }
    
    p.takeDamage((int)totalDamageDone);

    // Structure attack description
    attackDescription = p.getName() + " is " + this.getAttackString(atkType, move) + " by " + this.getName() + " and takes " + (int)totalDamageDone + " damage!";

    if (attackMultiplier > 1) {
      attackDescription += "\nIt's super effective!\n";
    }
    else if (attackMultiplier < 1) {
      attackDescription += "\nIt's not very effective...\n";
    }
    return attackDescription;
  }

  /**
   * Gets the atkType (basic or special) and if basic, gives strings of basic moves
   * @param atkType either basic or special attack
   * @param move the move within basic or special to make
   * @return string the string from selected move from basic attacks
   */
  public String getAttackString(int atkType, int move){

    String partialString = "";
    
    // Basic Attack
    if (atkType == 1) {
      switch(move) {
        case 1:
          partialString += "SLAMMED";
          break;

        case 2:
          partialString += "TACKLED";
          break;

        case 3:
          partialString += "PUNCHED";
          break;
      }
    }

    return partialString;
  }

  /**
   * Gets the atkType (basic or special) and if basic, randomizes damages of basic moves
   * @param atkType either basic or special attack
   * @param move the move within basic or special to make
   * @return int the damage from selected move from basic attacks
   */
  public int getAttackDamage(int atkType, int move) {
    Random rand = new Random();

    int attackDamage = 0;

    if (atkType == 1) {
      switch(move) {
      // Slam: 0 - 5
      case 1:
        attackDamage = rand.nextInt(6);
        break;

      // Tackle: 2 - 3
      case 2:
        attackDamage = rand.nextInt(3 - 2) + 2;
        break;

      // Punch: 1 - 4
      case 3:
        attackDamage = rand.nextInt(4 - 1) + 1;
        break;
      }
    }

    return attackDamage;
  }

  public double getAttackMultiplier(Pokemon p, int atkType){
    return 1;
  }

  public int getAttackBonus(int atkType){
    return 0;
  }

  /**
   * Gets the elemental type of the pokemon used in battle table
   * Fire = 0, Water = 1, Grass = 2
   * @return the integer corresponding to the elemental type of the Pokemon
   */
  public int getType(){
    int type = 0;

    if (this instanceof Fire) {
      type = 0;
    }

    else if (this instanceof Water) {
      type = 1;
    }

    else if (this instanceof Grass) {
      type = 2;
    }
        
    return type;
  }
}