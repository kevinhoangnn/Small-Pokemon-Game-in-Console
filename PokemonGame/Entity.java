/**
 * Representation of a generic type of entity
 */
abstract class Entity {
    private String name;
    private int hp;
    private int maxHP;

    /**
     * Initialize entity constrcutor
     * @param n takes in the name of an object
     * @param h takes in the current health of the object
     * @param mHP takes in the max health of the object
     */
    public Entity (String n, int h, int m) {
        this.name = n;
        this.hp = h;
        this.maxHP = m;
    }

    /**
     * Access the value of the instance variable for HP
     * @return the object's hp as of data type int
     */
    public int getHP() {
      if (hp <= 0) {
        return 0;
      }
      else {
        return hp;
      }
    }

    /**
     * Access the value of the instance variable for max HP
     * @return the object's max hp of data type int
     */
    public int getMaxHP() {
        return maxHP;
    }

    /**
     * Calculates the health of the object after taking damage
     * @param d takes in damage sustained
     */
    public void takeDamage(int d) {
        hp = hp - d;

        //Correction: Check if the entity's method is below 0
        if (hp < 0) {
          hp = 0;
        }
    }

    /**
     * Heal the object accordingly
     */
    public void heal() {
        // Reset hp to maximum hp
        hp = maxHP;
    }

    /**
     * Access the string stored in instance variable name
     * @return the name of the object of data type string
     */
    public String getName() {
        return name;
    }


    /**
     * Generate a string representation of an object
     * @return a string representation of an object
     */
    public String toString() {
        // Ex: Ash HP: 14/25
        return getName() + " HP: " + getHP() + "/" + getMaxHP();
    }
}