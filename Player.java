import java.util.*;

class Player{

  //Instance variables for the class to use, private to each player
  private boolean playerControlled;
  private ArrayList<Entity> team = new ArrayList<Entity>(3); //Create an arraylist with 3 indexes
  private ArrayList<String> inventory; //Create an arraylist for items to be stored
  private String playerName; 

  //Constant variables for text colour
  public static final String GREEN = "\u001B[32m";  //Colors
  public static final String RED = "\u001B[31m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String WHITE = "\u001B[37m";
  public static final String YELLOW = "\u001B[33m";
  public static final String CYAN = "\u001B[36m";
  public static final String CLEAR = "\u001b[1J"; //Clears console

  //Initializes the player object
  public Player(boolean playerControlled, String playerName){ 
    this.playerControlled = playerControlled;
    this.playerName = playerName;
    this.team = new ArrayList<Entity>(3);
    this.inventory = new ArrayList<String>(5);
  }

  //Initializes the player object
  public Player(boolean playerControlled, String playerName, int difficulty){ 
    this.playerControlled = playerControlled;
    this.playerName = playerName;
    this.team = new ArrayList<Entity>(3);
    this.inventory = new ArrayList<String>(5);
    if(! this.playerControlled){ //If it is not controlled by the player get random party members 
      this.team = PlayerUtils.getMonsterTeam(3, difficulty);
    }    
  }

  public void addInventory(String inv){ //Method to add to inventory
    this.inventory.add(inv);
  }

  public void addParty(Entity choice){ //Method to add a party member to the party
    this.team.add(choice);
  }

  public ArrayList<Entity> getTeam(){ //Access method to get the arraylist of the Player object's party members
    return this.team;
  }

  public void resetStats(){
    for(int i = 0; i < team.size(); i++){
      Entity p = team.get(i);
      p.statReset();
    }
  }

  
  public ArrayList<String> getInventory(){ //Access method to get the arraylist of items
    return this.inventory;
  }

  //Enemy attacking AI
  public void aiTakeTurn(Player p1){ //Method to determine how the monster will attack
    if(! this.playerControlled){
      Entity m;
      Entity p;
      Entity pFinal = null;
      
      Iterator<Entity> iter = team.iterator();
      while(iter.hasNext()){ //Iteration through monsters so that each of them can attack
        m = iter.next(); //Set m to the entity that the while loop is iterating through
        System.out.println(GREEN+"\r\nMonster " + m.getName() + " is Attacking!!"+YELLOW);

        //Choose which Adventurer to attack
        Iterator<Entity> iter2 = p1.getTeam().iterator();
        int highestHealth = 0; //Set highest health to 0
        int highestDamage = 0; //Set highest damage to 0

        Random rand = new Random(); //Create the object Random
        int randomNumber = rand.nextInt(2);

        if(randomNumber == 0){ //If the random number is 0 then pick a random index from the list of opponents and attack
          int randomIndex = rand.nextInt(p1.getTeam().size());
          pFinal = p1.getTeam().get(randomIndex);
        }
        else{ //If the random number is 1 then target opponent based on damage they do or health they have
          while(iter2.hasNext()){
            p = iter2.next(); //Iterate through the user's team
            if (pFinal == null){
              pFinal = p; //pFinal is used to determine which party member is attacked
            }
            //Decide if we want to attack p or check the next one...
            if(p.getHealth() < 20){ //If the party member has less than 20 health, then they are selected to be attacked
              pFinal = p;
            }
            else{ //If no one is below 20 hp
              if(p.getDamage() > highestDamage){ //If party member's attack is greater than the ones before it
                highestDamage = p.getDamage(); //Set highest damage to the party member's damage 
                highestHealth = p.getHealth(); //Set highest health to the party member's health
                pFinal = p; //Select them to be attacked
              }
              else if(p.getDamage() == highestDamage){ //If the damage of the party member is equal to the highestDamage
                if(p.getHealth() > highestHealth){ //Then compare to see if which one of the high damage party members has more health
                  highestHealth = p.getHealth(); //Set highest health to the party member's health
                  pFinal = p; //Select the party member to be attacked
                }
              }
            }
          }
        }
        System.out.println(YELLOW+m.getName() + " attacking " + pFinal.getName()+GREEN);
        m.attack(pFinal, pFinal.getDefending()); //Attack the party member that was selected above and find if they are defended
        p1.removeParty(); //Do a check to see if a party member on the user's team has died 
      }
    }
  }

  //Player Fight UI
  public void userTakeTurn(Player p1, Player p2){
    Scanner scan = new Scanner(System.in);
    Entity p;

    for(int x = 0; x < (p1.getTeam()).size(); x++){ //Iteration through party members so that each of them can attack
      p = (p1.getTeam()).get(x); //Get the entity that is being used in the current iteration
      p.isDefending(false);//Set defending variable is this entity to false so that an entity would not take less damage if they were doing an action other than defend
      System.out.println(YELLOW+"\n" + p.getName() +CYAN+" | "+PURPLE+"Level: " + p.getLevel() +CYAN+" | "+RED+"Health: " + p.getHealth() +GREEN);
      System.out.println(GREEN+"\n" + p.getName() + ", what action will you perform:"+YELLOW);
      String actionSelection = "";
      System.out.println(YELLOW+"1. Attack \n2. Defend \n3. Heal \n4. Item"+YELLOW); 
      while(!actionSelection.equals("1") && !actionSelection.equals("2") && !actionSelection.equals("3") && !actionSelection.equals("4")){ //While they haven't chosen an option, check for user input 
        actionSelection = scan.nextLine();
      }

      //If player picks to attack
      if(actionSelection.equals("1")){ //Player choose to attack
        ArrayList<Entity> e = p2.getTeam();
        System.out.println("\n");
        String droppedItem = "";

        //Depending on the adventurer name, their abilities may or may not changes. The comments in Barbarian's attack will explain the other subclasses as well.
        if(p.getName().contains("Barbarian")){ //Barbarian Attack

          //Display enemy to attack
          for(int i = 0; i < e.size(); i++){
            System.out.println(GREEN+ (i+1) + ". > " + e.get(i).getStats() +GREEN);
          }

          //Pick enemy to attack
          int maxNum = e.size()+1;
          System.out.println(YELLOW+maxNum + ". > Back"+YELLOW);

          System.out.print(YELLOW+"\n\nType the number of the monster to attack, " +p.getName()+ ": "+YELLOW);

          try{ //Picking enemy
            int enemySelected = scan.nextInt(); //Asks user which enemy to deal damage

            if(enemySelected == maxNum){ //Last option is to go back in menu
              x--;
            }
            else if(enemySelected < maxNum && enemySelected > 0){ //Chosen enemy gets damage dealt onto it
              droppedItem = p.attack(e.get(enemySelected-1), p.getDefending());
            }
            else{ //Indicates if number is not a choice
              System.out.print(RED+"You entered an invalid response."+YELLOW); 
              x--;
            }
          }
          catch(Exception ex){ //If the player inputs anything not an int, they are prompted to select a number
            System.out.println(RED+"\nPlease select a number only"+YELLOW);
            scan.next();
            continue;
          }
        }
        if(p.getName().contains("Wizard")){ //Wizard Attack
          
          //Display enemy to attack
          for(int i = 0; i < e.size(); i++){
            System.out.println(GREEN+ (i+1) + ". > " + e.get(i).getStats() +GREEN);
          }

          System.out.println(GREEN+"Do you want to target a single enemy or attack the entire party?\n(1 = Single Enemy | 2 = Party)");
          String typeOfAttack = scan.nextLine();

          if(typeOfAttack.equals("1")){

            //Pick enemy to attack
            int maxNum = e.size()+1;
            System.out.println(YELLOW+maxNum + ". > Back"+YELLOW);

            System.out.print(GREEN+"\n\nType the number of the monster to attack, " +p.getName()+ ": "+YELLOW);

            try{ //Picking enemy
              int enemySelected = scan.nextInt(); //Asks user which enemy to deal damage

              if(enemySelected == maxNum){ //Last option is to go back in menu
                x--;
              }
              else if(enemySelected < maxNum && enemySelected > 0){ //Chosen enemy gets damage dealt onto it
                p.nerf("D", 20);
                droppedItem = p.attack(e.get(enemySelected-1), p.getDefending());
                p.nerf("D", -20);
              }
              else{ //Indicates if number is not a choice
                System.out.print(RED+"You entered an invalid response."+YELLOW); 
                x--;
              }
            }
            catch(Exception ex){ //If the player inputs anything not an int, they are prompted to select a number
              System.out.println(RED+"\nPlease select a number only"+YELLOW);
              scan.next();
              continue;
            }    
          }
          if(typeOfAttack.equals("2")){
            p.nerf("M", 4);
            for(int i = 1; i < e.size(); i++){
              droppedItem = p.attack(e.get(i), p.getDefending());
            }
          }
          else{
            System.out.print(RED+"You entered an invalid response."+YELLOW);
            x--;
          }
        }
        if(p.getName().contains("Tank")){ //Tank Attack
          for(int i = 0; i < e.size(); i++){
            System.out.println(GREEN+ (i+1) + ". > " + e.get(i).getStats() +GREEN);
          }
          int maxNum = e.size()+1;
          System.out.println(YELLOW+maxNum + ". > Back"+YELLOW);

          System.out.print(GREEN+"\n\nType the number of the monster to attack, " + p.getName() + ": "+YELLOW);

          try{
            int enemySelected = scan.nextInt();

            if(enemySelected == maxNum){ 
              x--;
            }
            else if(enemySelected < maxNum && enemySelected > 0){
              droppedItem = p.attack(e.get(enemySelected-1), p.getDefending());
            }
            else{
              System.out.print(RED+"You entered an invalid response."+YELLOW);
              x--;
            }
          }
          catch(Exception ex){
            System.out.println(RED+"\nPlease select a number only"+YELLOW);
            scan.next();
            continue;
          }
        }
        if(p.getName().contains("Rogue")){
          for(int i = 0; i < e.size(); i++){
            System.out.println(GREEN+ (i+1) + ". > " + e.get(i).getStats() +GREEN);
          }
          int maxNum = e.size()+1;
          System.out.println(YELLOW+maxNum + ". > Back"+YELLOW);

          System.out.print(GREEN+"\n\nType the number of the monster to attack, " + p.getName() + ": "+YELLOW);

          try{
            int enemySelected = scan.nextInt();

            if(enemySelected == maxNum){ 
              x--;
            }
            else if(enemySelected < maxNum && enemySelected > 0){
              droppedItem = p.attack(e.get(enemySelected-1), p.getDefending());
            }
            else{
              System.out.print(RED+"You entered an invalid response."+YELLOW);
              x--;
            }
          }
          catch(Exception ex){
            System.out.println(RED+"\nPlease select a number only"+YELLOW);
            scan.next();
            continue;
          }
        }
        if(p.getName().contains("Cleric")){
          for(int i = 0; i < e.size(); i++){
            System.out.println(GREEN+ (i+1) + ". > " + e.get(i).getStats() +GREEN);
          }
          int maxNum = e.size()+1;
          System.out.println(YELLOW+maxNum + ". > Back"+YELLOW);

          System.out.print(GREEN+"\n\nType the number of the monster to attack, " + p.getName() + ": "+YELLOW);

          try{
            int enemySelected = scan.nextInt();

            if(enemySelected == maxNum){ 
              x--;
            }
            else if(enemySelected < maxNum && enemySelected > 0){
              droppedItem = p.attack(e.get(enemySelected-1), p.getDefending());
            }
            else{
              System.out.print(RED+"You entered an invalid response."+YELLOW);
              x--;
            }
          }
          catch(Exception ex){
            System.out.println(RED+"\nPlease select a number only"+YELLOW);
            scan.next();
            continue;
          }
        }
        p2.removeParty(); //Check to see if any of the enemy's health is equal to zero and remove them from the arraylist
        if (! droppedItem.equals("")){ //If the string does not equal anything then add it to the inventory
          p1.addInventory(droppedItem);
        }
        System.out.println("\n");
        if((p2.getTeam()).isEmpty()){ //If enemy team is empty then break out of the loop
          break;
        }
      }

      //If player picks to defend
      else if(actionSelection.equals("2")){ //Player choose to defend
        p.isDefending(true); //Change the variable isDefending to true for this party member
      }

      //If player picks to heal
      else if(actionSelection.equals("3")){ //Player choose to heal
        if((p.getName()).contains("Cleric")){ //if the Cleric is healing then the party member is healed by 20
          System.out.println("The cleric brings everybody some apple juice and everyone recovers a bit of health");
          for(int i = 0; i < team.size(); i++){
            team.get(i).heal(25 * p.getLevel());
          }
        }
        else{ //Anyone that isn't cleric is healed normally by 2 
          System.out.print("\n");
          for(int i = 0; i < team.size(); i++){
            System.out.println((i+1) + ". > " + team.get(i).getName()); //Display players that you can heal
          }
          int maxNum = team.size()+1; 
          System.out.println(YELLOW+maxNum + ". > Back"+YELLOW); //Print out the option to go back
          System.out.print(GREEN+"Who do you want to heal, " + p.getName() + ": "+YELLOW);
          int partyMemberHealed = scan.nextInt(); //Checks their response
          if(partyMemberHealed == maxNum){ //If their response is equal to the number displayed beside back, skip over the code for this action
            x--; //Use this so that this character's turn is repeated again
          }
          team.get(partyMemberHealed-1).heal();
        }
      }
      
      //If player picks to use item
      else if(actionSelection.equals("4")){ //If the player choose to look at items
        System.out.println(GREEN+"Inventory: "+YELLOW + inventory +YELLOW);
        for(int i = 0; i < inventory.size(); i++){ //Iterate through each item option
          System.out.println((i+1) + ". > " + inventory.get(i));
        }
        int maxNum = inventory.size()+1;
        System.out.println(YELLOW+ maxNum + ". > Back"+YELLOW);
        System.out.println(GREEN+ p.getName() + ", what item will you use:"+YELLOW);
        int selectedItem = scan.nextInt();
        if(selectedItem == maxNum){
          x--;
        }
        else{
          String item = inventory.get(selectedItem-1); //Initialize variable as the name of the selected item
          System.out.println(YELLOW+"Item: " + item +YELLOW);
          p.useItem(item); //Use the item on the current party member being used
          p1.removeItem(item); //Remove the item that was used from inventory
        }
      }

      else{
        System.out.print(RED+"You entered an invalid response."+YELLOW);
      }
    } 
  }

  
  //If a party member or enemy member's health is less than or equal to zero they are removed from the arraylist, so that they cannot be attacked once they are dead and so that we can determine if the player has won or lost the encounter based on which arraylist is empty.
  public ArrayList<Entity> removeParty(){
    for(int i = 0; i < team.size(); i++){
      Entity objectAtIndex = team.get(i);
      if(objectAtIndex.getHealth() <= 0){
        this.team.remove(objectAtIndex);
        i--;
      }
    }
    return this.team;
  }

  //Method to remove items
  public void removeItem(String item){
    for(int i = 0; i < inventory.size(); i++){ //Iterate through the list of Strings
      if(inventory.get(i) == item){
        inventory.remove(i);
        break;
      }
    }
  }

  //Method to print team info
  public void printTeam(){
    System.out.println(GREEN+"\n\nPlayer: " + this.playerName +GREEN); //Displays who's team it is
    System.out.println(GREEN+"_______________________________\n"+GREEN);
    System.out.println(GREEN+"The Team Characters are: "+GREEN);
    for(int i = 0; i < team.size(); i++){ //Iterates through each member of the team and prints out their stats
      Entity objectAtIndex = team.get(i);
      System.out.println(objectAtIndex.getStats());
    }
    System.out.println(GREEN+"_______________________________"+GREEN);
  }

  //Method to get random adventurer
  public Entity getAdventurer(String nickname){
    Random rand = new Random();
    int randomNumber = rand.nextInt(5);
    Entity character;
    switch(randomNumber){
    case 0:
      character = new Barbarian(nickname);
      break;
    case 1:
      character = new Rogue(nickname);
      break;
    case 2:
      character = new Cleric(nickname);
      break;
    case 3:
      character = new Tank(nickname);
      break;
    case 4:
      character = new Wizard(nickname);
      break;
    default:
      character = null;
      System.out.println(RED+"Error making a character."+YELLOW);
    }
    return character;
  }

    //Method  to build team of monsters for testing
    static class PlayerUtils{
      public static ArrayList<Entity> getMonsterTeam(int numMonsters, int difficulty){
        ArrayList<Entity> mTeam = new ArrayList<Entity>(numMonsters);
        for(int i = 0; i < numMonsters; i++){
          mTeam.add(PlayerUtils.getMonster(difficulty));
        }
        return mTeam;
      }
  
    //Method to get a random enemy
    public static Entity getMonster(int difficulty){
      Random rand = new Random();
      int randomNumber = rand.nextInt(4);
      Entity character;
      switch(randomNumber){
      case 0:
        character = new Goblin(difficulty);
        break;
      case 1:
        character = new Witch(difficulty);
        break;
      case 2:
        character = new Slime(difficulty);
        break;
      case 3:
        character = new Golem(difficulty);
        break;
      default:
        character = null;
        System.out.println(RED+"Error making a character."+YELLOW);
      }
      return character;
    }
  }
}