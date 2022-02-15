/**
 * @author Hector Reyes (#1)
 * @author Kevin Nguyen (#2)
 * @author Khirby Calma (#3)
 */
 
import java.util.Random;
import java.util.*;
import java.awt.Point;

class Main { //beginning of class Main

  public static void main(String[] args) { //beginning of method main

    Scanner scnr = new Scanner(System.in);
    
    System.out.println("Prof. Oak: Hello there new trainer, what is your name?");
    String userName = CheckInput.getString();

    System.out.println("Nice to meet you " + userName+ "!");

    System.out.println("Choose your first pokemon: \n1. Charmander\n2. Squirtle\n3. Bulbasaur");
    int pokemonSelection = CheckInput.getIntRange(1, 3);

    PokemonGenerator pokemonFactory = PokemonGenerator.getInstance();
    Pokemon starter = null;
    
    switch(pokemonSelection) {
      case 1:
        starter = pokemonFactory.getPokemon("Charmander");
        break;
      case 2:
        starter = pokemonFactory.getPokemon("Squirtle");
        break;
      case 3:
        starter = pokemonFactory.getPokemon("Bulbasaur");
        break;
    }

    Map newMap = Map.getInstance();
    newMap.loadMap(1);
    int level = 1;

    Trainer trainer = new Trainer(userName, starter);

    int mainMenuChoice = 0;
    while (mainMenuChoice != 5) {
      
      if (trainer.getHP() <= 0) {
        System.out.println("You have fainted.\nGame over!");
        break;
      }

      System.out.println(trainer);
      char playerLoc = 'B';
      mainMenuChoice = mainMenu();

      if (mainMenuChoice == 5) {
        System.out.println("Thanks for playing!");
        break;
      }
      
      switch(mainMenuChoice) {
        case 1:
          newMap.reveal(trainer.getLocation());
          playerLoc = trainer.goNorth();
          break;
      
        case 2:
          newMap.reveal(trainer.getLocation());
          playerLoc = trainer.goSouth();
          break;
      
        case 3:
          newMap.reveal(trainer.getLocation());
          playerLoc = trainer.goEast();
          break;

        case 4:
          newMap.reveal(trainer.getLocation());
          playerLoc = trainer.goWest();
          break;
      }

      Point END_OF_MAP_AREA_1 = new Point(4, 4);
      Point END_OF_MAP_AREA_2 = new Point(0, 3);
      Point END_OF_MAP_AREA_3 = new Point(2, 0);
      
      switch(playerLoc) {

        case 'i':
          double rand = randRange(0, 1);

          if (rand == 0) {
            trainer.receivePokeball();
            System.out.println("You stumbled upon a pokeball!\n");
          }

          else if (rand == 1) {
            trainer.receivePotion();
            System.out.println("You miraculously found a potion laying in the grass!\n");
          }

          newMap.removeCharAtLoc(trainer.getLocation());
          break;

        case 'f':
          int areaNum = 0;

          if (trainer.getLocation().equals(END_OF_MAP_AREA_1)) {
            areaNum = 1;
          }

          else if (trainer.getLocation().equals(END_OF_MAP_AREA_2)) {
            areaNum = 2;
          }

          else if (trainer.getLocation().equals(END_OF_MAP_AREA_3)) {
            areaNum = 3;
          }

          System.out.println("Area " + areaNum + " Gym Leader: You've finally made it! Are you ready?\nYou challenge Area " + areaNum + " Gym Leader!\n");
          Pokemon gymPokemon = pokemonFactory.generateRandomPokemon(level + 2);

          if (!checkUsablePokemon(trainer)) {
            System.out.println("You have no healthy Pokemon.");
            System.out.println("Area " + areaNum + " Gym Leader: Looks like you aren't ready. Come back whenever you are!");
            System.out.println("You exit the gym.\n");
            break;
          }

          System.out.println(gymPokemon.toString() + "\nWhat do you want to do?");
          System.out.println("1. Fight\n2. Use Potion\n3. Throw Poke Ball\n4. Run Away");
          
          int choice = CheckInput.getIntRange(1, 4);

          while (choice != 5){

            if (choice == 1) {
              gymPokemon = trainerAttack(trainer, gymPokemon);

              if (gymPokemon.getHP() <= 0) {
                int reward = randRange(10, 15);
                System.out.println("You have been rewarded with $" + reward + ".\nYour pokemon have all been buffed for the next stage!\nArea " + areaNum + " Gym Leader: You're pretty good! Here is your badge, and good luck in your future battles!");
                trainer.receiveMoney(reward);
                trainer.buffAllPokemon();
                newMap.removeCharAtLoc(trainer.getLocation());

                if (trainer.getLocation().equals(END_OF_MAP_AREA_1)) {
                  newMap.loadMap(2);
                  newMap.reveal(trainer.getLocation());
                  level++;
                }

                else if (trainer.getLocation().equals(END_OF_MAP_AREA_2)) {
                  newMap.loadMap(3);
                  newMap.reveal(trainer.getLocation());
                  level++;
                }

                else if (trainer.getLocation().equals(END_OF_MAP_AREA_3)) {
                  newMap.loadMap(1);
                  newMap.reveal(trainer.getLocation());
                  level++;
                }

                break;
              }

              if (!checkUsablePokemon(trainer)) {
                System.out.println("You were overwhelmed by your defeat.");
                System.out.println("Area " + areaNum + " Gym Leader: Get stronger and try again!");
                break;
              }
            } 

            else if (choice == 2) { 
              if (trainerheal(trainer)) {
                int trainerPokemon = randRange(0, trainer.getNumPokemon() - 1);
                Pokemon battlePoke = enemyTurn(gymPokemon, trainer.getPokemon(trainerPokemon));
                pokemonListReorganizer(trainer, battlePoke, trainerPokemon);
              }
            } 

            else if (choice == 3) { //pokeball
              System.out.println("Area " + areaNum + " Gym Leader: Hey, You can't do that!\nYou cannot use this at this time.\n");
            } 

            else if (choice == 4) {
              System.out.println("Area " + areaNum + " Gym Leader: You'll have to finish this battle before you can leave!\n");
            }

            System.out.println(gymPokemon.toString() + "\n");
            System.out.println("What do you want to do?");
            System.out.println("1. Fight\n2. Use Potion\n3. Throw Poke Ball\n4. Run Away");

            choice = CheckInput.getIntRange(1, 4);
          }

        case 'n':
          System.out.println("There's nothing here...\n");
          break;

        case 'w':
          Pokemon randomPokemon = pokemonFactory.generateRandomPokemon(level);
          System.out.println("A wild " + randomPokemon.getName() + " has appeared!\n");

          if (!checkUsablePokemon(trainer)) {
            System.out.println("You have no healthy Pokemon.");
            System.out.println(randomPokemon.getName() + " HITS you and flees the scene.");
            System.out.println("You take 5 damage.\n");
            trainer.takeDamage(5);
            break;
          }

          System.out.println(randomPokemon.toString() + "\n");
          System.out.println("What do you want to do?");
          System.out.println("1. Fight\n2. Use Potion\n3. Throw Poke Ball\n4. Run Away");
          
          int encounterChoice = CheckInput.getIntRange(1, 4);

          while (encounterChoice != 5){

            if (encounterChoice == 1) {
              randomPokemon = trainerAttack(trainer, randomPokemon);

              if (randomPokemon.getHP() <= 0) {
                int reward = randRange(1, 3);
                System.out.println("You have been rewarded with $" + reward + ".\n");
                trainer.receiveMoney(reward);
                newMap.removeCharAtLoc(trainer.getLocation());
                break;
              }

              if (!checkUsablePokemon(trainer)) {
                System.out.println("You were overwhelmed by your defeat.");
                System.out.println(randomPokemon.getName() + " HITS you and leaves the battle.");
                System.out.println("You take 5 damage.\n");
                trainer.takeDamage(5);
                break;
              }
            } 

            else if (encounterChoice == 2) { 
              if (trainerheal(trainer)) {
                int trainerPokemon = randRange(0, trainer.getNumPokemon() - 1);
                Pokemon battlePoke = enemyTurn(randomPokemon, trainer.getPokemon(trainerPokemon));
                pokemonListReorganizer(trainer, battlePoke, trainerPokemon);
              }
            }  
            else if (encounterChoice == 3) {
              if (trainer.hasPokeball()) {
                if (trainer.catchPokemon(randomPokemon)) {
                  System.out.println("Shake...Shake...Shake");
                  System.out.println("You caught " + randomPokemon.getName() + "\n");
                  if (trainer.getNumPokemon() < 7){
                    newMap.removeCharAtLoc(trainer.getLocation());
                    break;
                  }

                  else{
                    System.out.println("You can only have 6 Pokemons in your party!");
                    System.out.println("Choose one of your pokemon to remove from your party:");
                    System.out.println(trainer.getPokemonList());
                    int pokemonRemoveChoice = CheckInput.getIntRange(1, trainer.getNumPokemon()) - 1;
                    trainer.removePokemon(pokemonRemoveChoice);
                    newMap.removeCharAtLoc(trainer.getLocation());
                    break;
                  }

                }

                else {
                  System.out.println("Shake...Shake...Shake");
                  System.out.println("Oh no! The Pokemon broke free!\n");

                  int trainerPokemon = randRange(0, trainer.getNumPokemon() - 1);
                  Pokemon battlePoke = enemyTurn(randomPokemon, trainer.getPokemon(trainerPokemon));

                  pokemonListReorganizer(trainer, battlePoke, trainerPokemon);
                }

              } 
              else {
                System.out.println("You do not have any Pokeballs.\n");
              }
            }
            else if (encounterChoice == 4) {
              System.out.println("You have ran away...\n");
              runRandDirection(trainer);
              break;
            }

            System.out.println(randomPokemon.toString() + "\n");
            System.out.println("What do you want to do?");
            System.out.println("1. Fight\n2. Use Potion\n3. Throw Poke Ball\n4. Run Away");

            encounterChoice = CheckInput.getIntRange(1, 4);
            
          }
          break;

        case 'p':
          double randomEvent = randRange(0, 4);
          if (randomEvent == 0) {
            trainer.receivePokeball();
            System.out.println("You stumbled upon a trainer who gives you a pokeball!\n");
            newMap.removeCharAtLoc(trainer.getLocation());
          } 

          else if (randomEvent == 1) {
            trainer.receivePotion();
            System.out.println("You run into Nurse Joy.\nNurse Joy: Looks like you might need this.\nYou receive a potion from her.\n");
            newMap.removeCharAtLoc(trainer.getLocation());
          } 

          else if (randomEvent == 2) {
            trainer.receiveMoney(5);
            System.out.println("You stumbled upon a trainer who gives you money!\nYou receive $5.\n");
            newMap.removeCharAtLoc(trainer.getLocation());
          } 

          else if (randomEvent == 3) {
            System.out.println("Team Rocket Grunt 1: Hand over your money or else.\nTeam Rocket Grunt 2: OR ELSE!.\n");
            System.out.println("1. Give all your money. (Give $" + trainer.getMoney() + ")\n2. Fight");

            choice = CheckInput.getIntRange(1, 2);

            if (choice == 1) {
              trainer.spendMoney(trainer.getMoney());
              System.out.println("You empty your pockets.\nTeam Rocket Grunt 1: Good choice!\nThe Team Rocket Grunts take their leave.\n");
              newMap.removeCharAtLoc(trainer.getLocation());
            } 

            else {
              System.out.println("You chose to fight.\nRocket Grunt 1 & 2: Prepare to lose!\n");
              randomPokemon = pokemonFactory.generateRandomPokemon(level);

              System.out.println("The Rocket Grunts sent out " + randomPokemon.getName() + "!\n");

              if (!checkUsablePokemon(trainer)) {
                int money = randRange(1, 10);
                trainer.spendMoney(money);
                System.out.println("You have no usable Pokemon.\nThe Rocket Grunts beat you up and take some money.");
                System.out.println("You take 5 damage.\nYou lost $" + money + ".\n");
                trainer.takeDamage(5);
                break;
              }

              System.out.println(randomPokemon.toString() + "\nWhat do you want to do?");
              System.out.println("1. Fight\n2. Use Potion\n3. Throw Poke Ball\n4. Run Away");
          
              choice = CheckInput.getIntRange(1, 4);

              while (choice != 5){
                if (choice == 1) {
                  randomPokemon = trainerAttack(trainer, randomPokemon);

                  if (randomPokemon.getHP() <= 0) {
                    int reward = randRange(5, 10);
                    System.out.println("You have defeated Team Rocket Grunts 1 & 2 and have been rewarded with $" + reward + ".\n");
                    trainer.receiveMoney(reward);
                    newMap.removeCharAtLoc(trainer.getLocation());
                    break;
                  }

                  if (!checkUsablePokemon(trainer)) {
                    int money = randRange(1, 10);
                    trainer.spendMoney(money);
                    System.out.println("You were overwhelmed by your defeat.");
                    System.out.println("The Rocket Grunts beat you up and take some money.");
                    System.out.println("You take 5 damage.\nYou lost $" + money + ".\n");
                    trainer.takeDamage(5);
                    break;
                  }
                } 

                else if (choice == 2) { 
                  if (trainerheal(trainer)) {
                    int trainerPokemon = randRange(0, trainer.getNumPokemon() - 1);
                    Pokemon battlePoke = enemyTurn(randomPokemon, trainer.getPokemon(trainerPokemon));
                    pokemonListReorganizer(trainer, battlePoke, trainerPokemon);
                  }
                } 

                else if (choice == 3) { //pokeball
                  System.out.println("Rocket Grunt 1: What do you think you're trying to do?\nYou cannot use this at this time.\n");
                } 

                else if (choice == 4) {
                  System.out.println("Rocket Grunt 2: Where do you think you're going?\nYou are blocked from running.\n");
                }

                System.out.println(randomPokemon.toString() + "\n");
                System.out.println("What do you want to do?");
                System.out.println("1. Fight\n2. Use Potion\n3. Throw Poke Ball\n4. Run Away");

                choice = CheckInput.getIntRange(1, 4);
              }
            }
          } 
          else {
            int dmgTaken = randRange(3, 5);
            trainer.takeDamage(dmgTaken);
            System.out.println("You run into Misty.\nMisty: Where's my bike, twerp!\nMisty SMACKS you for " + dmgTaken + " damage.\n");
            newMap.removeCharAtLoc(trainer.getLocation());
          }
          break;
          
        case 'c':
          System.out.println("You've entered the city.");
          System.out.println("Where would you like to go?\n1. Store\n2. Pokemon Hospital");
          
          int citySelection = CheckInput.getIntRange(1, 2);
          if (citySelection == 1){
            store(trainer);
          }
          
          else{
            System.out.println("Hello! Welcome to the Pokemon Hospital.");
            System.out.println("I'll fix your poor pokemon up in a jiffy!\n\nBing..Bing...Bing Bing Bing...\n");
            System.out.println("There you go! See you again soon.\n");
            trainer.healAllPokemon();
          }
          break;

        case 'B':
          System.out.println("You cannot go that way.\n");
          break;
      } 
    }
  } //end of method main

  /**
   * Display a menu interface indicating which direction the player would like to go
   * @return direction chosen as an integer value
   */
  public static int mainMenu(){ //beginning of method mainMenu
    System.out.println("Main Menu:\n1. Go North\n2. Go South\n3. Go East\n4. Go West\n5. Quit");
    return CheckInput.getIntRange(1,5);
  } //end of method mainMenu
  
  /**
   * Displays a battle interface that allows the user to select a  pokemon in their collection
   * and check if they're still able to fight
   * @param t the player / trainer
   * @param wild the enemy pokemon
   */
  public static Pokemon trainerAttack(Trainer t, Pokemon wild) { // beginning of method trainerAttack
    Random rand = new Random();

    System.out.println("Choose a Pokemon:\n" + t.getPokemonList());
    int pokeChoice = CheckInput.getIntRange(1, t.getNumPokemon()) - 1;
    double debuffChance = rand.nextDouble();
    PokemonGenerator pokemonFactory = PokemonGenerator.getInstance();

    boolean usablePoke = false;
    while (usablePoke == false) {
      if (t.getPokemon(pokeChoice).getHP() > 0) {
        usablePoke = true;
      }
      else {
        System.out.println("You can't use a fainted Pokemon.\n");
        System.out.println("Choose a Pokemon:");
        System.out.println(t.getPokemonList());
        pokeChoice = CheckInput.getIntRange(1, t.getNumPokemon()) - 1;
      }
    }

    Pokemon battlePokemon = t.getPokemon(pokeChoice);
    System.out.println(battlePokemon.getName() + ", I choose you!");

    System.out.println("\n" + battlePokemon.getAttackTypeMenu());
    int atkType = CheckInput.getIntRange(1, battlePokemon.getNumAttackTypeMenuItems());

    System.out.println(battlePokemon.getAttackMenu(atkType));
    int move = CheckInput.getIntRange(1, battlePokemon.getNumAttackMenuItems(atkType));

    System.out.println(battlePokemon.attack(wild, atkType, move));

    if (wild.getHP() <= 0) {
      System.out.println(wild.getName() + " has fainted.\n");
      return wild;
    }


    if (debuffChance <= 0.25) {
      wild = pokemonFactory.addRandomDebuff(wild);
      System.out.println(wild.getName() + " is hit with a debuff!\n");

      battlePokemon = enemyTurn(wild, battlePokemon);
      pokemonListReorganizer(t, battlePokemon, pokeChoice);

      return wild;
    }
    else {
      battlePokemon = enemyTurn(wild, battlePokemon);
      pokemonListReorganizer(t, battlePokemon, pokeChoice);

      return wild;
    }
  } //end of method trainerAttack

  /**
   * Display an interactive menu containing the items in the store, gets the user's inputs, validates funds and indicates whether the trainer has sufficient funds to purchase said items
   * @param t trainer controlled by the user
   */
  public static void store(Trainer t) { // beginning of method store

    int itemSelection = 0;
    while(itemSelection != 3) {
      System.out.println("Hello! What can I help you with?");
      System.out.println("1. Buy Potions - $5\n2. Buy Poke Balls - $3\n3. Exit\n\nCurrent Balance: $" + t.getMoney());
      itemSelection = CheckInput.getIntRange(1, 3);

      if (itemSelection == 1) {
        if (t.spendMoney(5)) {
          t.receivePotion();
          System.out.println("Here's your potion!\n");
        }
        else {
          System.out.println("Insufficent funds for potions!\n");
        }
      }
      else if (itemSelection == 2){
        if (t.spendMoney(3)) {
          t.receivePokeball();
          System.out.println("Here's your pokeball!\n");
        }
        else {
          System.out.println("Insufficent funds for pokeballs!\n");
        }
      }
      else{
        break;
      }
    }

  } // end of method store
    
  /**
   * Generates a random number within a specified range
   * @param start takes in starting point
   * @param finish takes in the cut off point in the range
   * @return a random generated number
   */
  public static int randRange ( int start, int finish) {
    Random rand = new Random();

    int randInt = rand.nextInt(finish + 1 - start) + start;

    return randInt;
  }
  
  /**
   * Controls the player's movement in the 2D map
   * @param t  the trainer/player
   */
  public static void runRandDirection(Trainer t) {
    int randNum = randRange(0, 3);

    switch(randNum) {
    case 0:
      if (t.goNorth() != 'B') {
        break;
      }
    case 1:
      if (t.goSouth() != 'B') {
        break;
      }
    case 2:
      if (t.goEast() != 'B') {
        break;
      }             
    case 3:
      if (t.goWest() != 'B') {
        break;
      }
      if (t.goNorth() != 'B') {
        break;
      }
      if (t.goSouth() != 'B') {
        break;
      }
      if (t.goEast() != 'B') {
        break;
      }     
    }
  }

  /**
   * Check if the trainer's pokemon has enough health
   * @param t the trainer in the game controlled by player
   * @return a boolean value indicating whether the trainer can continue to use their current pokemon
   */
  public static boolean checkUsablePokemon(Trainer t) {
    int usablePokemon = t.getNumPokemon();
    for (int i = 0; i < t.getNumPokemon(); i++) {
      if (t.getPokemon(i).getHP() <= 0) {
        usablePokemon -= 1;
      }
    }
    if (usablePokemon == 0) {
      return false;
    }
    else {
      return true;
    }
  }

  /**
   * Randomly generate a move for the enemy pokemon ranging between their basic and special attack
   * @param enemyPokemon enemy pokemon object
   * @param trainerPokemon player's pokemon object
   * @return the trainerPokemon to overwrite with debuff
   */
  public static Pokemon enemyTurn(Pokemon enemyPokemon, Pokemon trainerPokemon) {
    Random rand = new Random();
    double debuffChance;
    PokemonGenerator pokemonFactory = PokemonGenerator.getInstance();

    int wildAtkType = rand.nextInt(enemyPokemon.getNumAttackTypeMenuItems()) + 1;

    int wildMove = rand.nextInt(enemyPokemon.getNumAttackMenuItems(wildAtkType)) + 1;

    System.out.println(enemyPokemon.attack(trainerPokemon, wildAtkType, wildMove) + "\n");

    debuffChance = rand.nextDouble();

    if (trainerPokemon.getHP() <= 0) {
      System.out.println(trainerPokemon.getName() + " has fainted.\n");
      return trainerPokemon;
    }

    if (debuffChance <= 0.1) {

      trainerPokemon = pokemonFactory.addRandomDebuff(trainerPokemon);
      System.out.println(trainerPokemon.getName() + " is hit with a debuff!\n");

      return trainerPokemon;
    }

    return trainerPokemon;
  }

  /**
   * Reorganizes the Pokemon list after adding a buff or debuff
   * @param t trainer in game controlled by player
   * @param newPoke Pokemon being reorganized
   * @param startIndex Index where newPoke was moved from
   */
  public static void pokemonListReorganizer(Trainer t, Pokemon newPoke, int startIndex) {
    int hpDiff = 0;
    hpDiff = newPoke.getMaxHP() - newPoke.getHP();
    newPoke.takeDamage(newPoke.getHP());
    t.catchPokemon(newPoke);
    t.removePokemon(startIndex);
    t.receivePokeball();
    t.usePotion(t.getNumPokemon() - 1);
    t.receivePotion();
    t.getPokemon(t.getNumPokemon() - 1).takeDamage(hpDiff);

    int pokeIndex = t.getNumPokemon() - 1;

    while (pokeIndex != startIndex) {
      newPoke = t.getPokemon(startIndex);
      hpDiff = newPoke.getMaxHP() - newPoke.getHP();
      newPoke.takeDamage(newPoke.getHP());
      t.catchPokemon(newPoke);
      t.removePokemon(startIndex);
      t.receivePokeball();
      t.usePotion(t.getNumPokemon() - 1);
      t.getPokemon(t.getNumPokemon() - 1).takeDamage(hpDiff);
      t.receivePotion();
      pokeIndex--;
    }
  }

  /**
   * Displays a heal interface that allows the user to select an available pokemon in their collection and applies a buff after healing the selected pokemon
   * @param t trainer in game controlled by player
   * @return returns a bool if the heal worked
   */
  public static boolean trainerheal(Trainer t) {
    PokemonGenerator pokemonFactory = PokemonGenerator.getInstance();
    if (!t.hasPotion()) {
      System.out.println("You do not have any potions to use.\n");

      return false;
    }
    else {
      boolean fullHP = true;
      for (int i = 0; i < t.getNumPokemon(); i++){
        if (t.getPokemon(i).getHP() != t.getPokemon(i).getMaxHP()) {
          fullHP = false;
        }
      }
      if (fullHP) {
        System.out.println("All of your Pokemons are healthy.\n");

        return false;
      }
      else {
        System.out.println("Choose a Pokemon to heal:");
        System.out.println(t.getPokemonList());
        int healChoice = CheckInput.getIntRange(1, t.getNumPokemon()) - 1;
      
        boolean healablePoke = false;
        while (healablePoke == false) {
          if (t.getPokemon(healChoice).getHP() >= t.getPokemon(healChoice).getMaxHP()) {
            System.out.println("You can't heal a Pokemon with full HP.\n");
            System.out.println("Choose a Pokemon to heal:");
            System.out.println(t.getPokemonList());
            healChoice = CheckInput.getIntRange(1, t.getNumPokemon()) - 1;
          }
          else if (t.getPokemon(healChoice).getHP() > 0) {
            healablePoke = true;
          }
          else {
            System.out.println("You can't heal a fainted Pokemon.\n");
            System.out.println("Choose a Pokemon to heal:");
            System.out.println(t.getPokemonList());
            healChoice = CheckInput.getIntRange(1, t.getNumPokemon()) - 1;
          }
        }
        Pokemon pokeChoice = pokemonFactory.addRandomBuff(t.getPokemon(healChoice));
        pokemonListReorganizer(t, pokeChoice, healChoice);
        t.usePotion(healChoice);
        
        System.out.println(t.getPokemon(healChoice).getName() + " is successfully healed to full health and received a buff!\n");

        return true;
      }
    }
  }
} //end of class Main