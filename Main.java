import java.util.*;

class Main {

  //Global variables that multiple methods need access to
  private static boolean detour = false;
  private static int gameTime = 0;
  private static String location = "Dungeon Grounds";

  //Constant variables for text colour
  public static final String GREEN = "\u001B[32m";  //Colors
  public static final String RED = "\u001B[31m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String WHITE = "\u001B[37m";
  public static final String YELLOW = "\u001B[33m";
  public static final String CYAN = "\u001B[36m";
  public static final String CLEAR = "\u001b[1J"; //Clears console
  
  public static void main(String[] args) {  
    play(); //Function to start game
  }

  //Function of the main game
  public static void play(){
    Scanner game = new Scanner(System.in); //Creates scanner object

    //Introducion to the game
    System.out.println(GREEN+"\nWelcome to Dungeon Crawler"+GREEN);
    System.out.println(BLUE+"\nYou can adventure around the dungeon and on your way, you may find monsters to fight"+BLUE);

    Player userClass = chooseClass(); //Sets user object to equal returned user with chosen team
    userClass.printTeam(); //Print out their chosen party members
    gameTime = travel(gameTime, userClass); //Starting the player's journey in dungeon

    boolean inGame = true; //Boolean to continue game as long as it is running

    while(inGame == true){ //While the game is still going

      //Random number generator
      Random random = new Random(); //Create a random object
      int RNG = random.nextInt(101); //Create a variable that gets a random number between 0 and 100

      gameTime = travel(gameTime, userClass); //Continuing the progression of setting in the dungeon

      if(RNG > 60){ //If RNG is greater than 60 then continue to have the player move through the dungeon
        gameTime = travel(gameTime, userClass);
      }
      if(RNG <= 60){ //If RNG is less than or equal to 60 then a battle with a monster will occur
        System.out.println(RED+"\r\nA battle has started:"+GREEN);
        encounter(userClass); //Starts encounter function and input the users player object as the parameter

        //After a battle, the party members are iterated through to level up.
        for(int i = 0; i < (userClass.getTeam()).size(); i++){
          (userClass.getTeam()).get(i).levelUp();
        }
      }
      gain(userClass); //Function to potentially gain a new party member or find a treasure chest
    }
    
  }

  //Choose adventurers to join party
  public static Player chooseClass(){
    Scanner game = new Scanner(System.in); //Creates a scanner object

    //Initializing the player
    System.out.println(GREEN+"\nEnter your name:"+YELLOW); //Asks for your name
    String name = game.nextLine(); //Reads user input
    Player user = new Player(true, name); // Create a player object with their name

    //Asks the user which party member they want to join their party
    System.out.println(GREEN+"\nChoose the classes of your party members:"+YELLOW);
    System.out.println("1. Barbarian"+CYAN+" [Use for consistent damage]"+YELLOW);
    System.out.println("2. Rogue"+CYAN+" [Use for debuffs of enemies]"+YELLOW);
    System.out.println("3. Tank"+CYAN+" [Use to tank damage]"+YELLOW);
    System.out.println("4. Wizard"+CYAN+" [Use for splash damage]"+YELLOW);
    System.out.println("5. Cleric"+CYAN+" [Use for consistent healing]"+YELLOW);

    //Iterates through loop 3 times so that the player has a 3 starting party members
    for(int i = 1; i <= 3; i++){
      System.out.println(GREEN+"\r\nParty Member " + i + ":"+YELLOW);
      String choice = game.nextLine();

      if(choice.equals("1")){ //If user answer equals 1
        user.addParty(new Barbarian()); //Adds a new barbarian to the user's team
        System.out.println(YELLOW+"\r\nBarbarian was chosen");
      }
      else if(choice.equals("2")){ //If user answer equals 2
        user.addParty(new Rogue()); //Adds a new rogue to the user's team
        System.out.println(YELLOW+"\r\nRogue was chosen");
      }
      else if(choice.equals("3")){ //If user answer equals 3
        user.addParty(new Tank()); //Adds a new tank to the user's team
        System.out.println(YELLOW+"\r\nTank was chosen");
      }
      else if(choice.equals("4")){ //If user answer equals 4
        user.addParty(new Wizard()); //Adds a new wizard to the user's team
        System.out.println(YELLOW+"\r\nWizard was chosen");
      }
      else if(choice.equals("5")){ //If user answer equals 5
        user.addParty(new Cleric()); //Adds a new cleric to the user's team
        System.out.println(YELLOW+"\r\nCleric was chosen");
      }
      else{ //If the user answer is anything else
        System.out.println(RED+"\r\nYou did not pick an option"+YELLOW); //Indicate to user that option is not valid
        i--; //Gives another opportunity to pick a party member
      }
    }
    return user;
  }

  //Function to progress in the "dungeon" as time passes
  public static int travel(int time, Player userClass){
    Scanner game = new Scanner(System.in); //Creates a scanner object

    if(time == 0){ //When time is 0 display that they have entered the dungeon
      System.out.println(GREEN+"\n\n");
      System.out.println("============================");
      System.out.println("You have entered the dungeon");
      System.out.println("============================\n\n"+GREEN);
    }
    if(time > 0){ //When time is greater than 0 display 3 directions to explore
      System.out.println(GREEN+"\n--------------------------------------------------------"+YELLOW);
      System.out.println(GREEN+"Current Location - " +WHITE+ location +GREEN);
      System.out.println(GREEN+"Travel: "+YELLOW+"\r\n 1. Straight \r\n 2. Right \r\n 3. Left"+GREEN);
      System.out.println(GREEN+"--------------------------------------------------------"+YELLOW);
      String choice = game.nextLine(); //Gather user input on where they want to go
      
      if(choice.equals("1")){ //If user input equals 1 go straight
        System.out.println(YELLOW+"\r\nYou went straight"+GREEN);
      }
      else if(choice.equals("2")){ //If user input equals 2 go right
        System.out.println(YELLOW+"\r\nYou went right"+GREEN);
      }
      else if(choice.equals("3")){ //If user input equals 3 go left
        System.out.println(YELLOW+"\r\nYou went left"+GREEN);
      }
      else{ //Prints that the user has choosen a random direction
        System.out.println(YELLOW+"\r\nYou closed your eyes, and continued towards a random direction"+GREEN);
      }

      Random random = new Random();
      int RNG = random.nextInt(6);

      if(detour == true){ //If player runs into a detour time is subtracted to "pause" time
        time--;
      }
      if(detour == false){ //If their is no detour then check for an event
        events(time, userClass); 
      }
    }

    time++; //Add to time
    return time;
  }
  
  //Function to encounter enemies to battle
  public static void encounter(Player user){
    user.resetStats(); //Resets stat buffs and debuffs from last game
    Player enemy = new Player(false,"CPU", gameTime); //Create enemy player object, difficulty of the enemy is based on gameTime
    int turn = 0; //Initialises turn variable to keep track of whose turn it is

    //Create two variables to use to determine what happens next depending on which is true
    boolean winCondition = false; 
    boolean loseCondition = false;

    //Initialise variables to equal the user and enemy team
    ArrayList<Entity> playerTeam = user.getTeam();
    ArrayList<Entity> playerCPU = enemy.getTeam();

    //While neither the player or the enemy have won continue taking turns
    while(winCondition == false && loseCondition == false){
      if(turn == 0){ //When turn is 0 the enemy takes it's turn
        enemy.aiTakeTurn(user); //Enter the player user as a parameter and have the enemy take it's turn
        playerTeam = user.removeParty(); //Checks player team to remove a party member if their health is less than or equal to 0
        user.printTeam(); //Print the users team to show the change in health
        turn = 1; //Set turn to 1 so the player can take it's turn
      }
      else{ //When turn is not 0, like 1
        user.userTakeTurn(user, enemy); //Have the player take their turn
        playerCPU = enemy.removeParty(); //Remove any dead enemy party members
        enemy.printTeam(); //Print enemy team's stats
        turn = 0; //Set turn to 0 so that the enemy can play again
      }

      //Ending the battle
      if(turn == 0 && playerTeam.isEmpty()){ //If the arraylist for the user's party members is empty
        loseCondition = true; //Player has lost the battle
        System.out.println(RED+"You lost the battle... You blacked out!"+GREEN);
      }
      else if(turn == 1 && playerCPU.isEmpty()){ //If the enemies arraylist is empty
        winCondition = true; //Player has won
        System.out.println(PURPLE+"You emerged victorious!"+GREEN);
      }
    }
  }

  public static void gain(Player userClass){
    Scanner game = new Scanner(System.in);
    Random random = new Random();
    int RNG = random.nextInt(101);

    if(RNG <= 15){ //If the random number is less than 15
      if(userClass.getTeam().size() < 5){ //If the users team size is less than 5 then give them the option to add a new adventurer to their party

        System.out.println("As you explore, you stumble upon an adventurer,");
        System.out.println("The adventurer introduces themselves as ...");
        System.out.println("(Input a name for this lost soul)");
        String nickname = game.nextLine();

        //Initializing random character to indicate to user its name
        ArrayList<Entity> playerTeam = userClass.getTeam(); //Set variable to equal the arraylist of the player's team

        Entity randomPartyMember = userClass.getAdventurer(nickname); //Assign a variable to equal the character that was randomnly generated from getAdventurer

        //Ask if user wants lost adventurer to join party
        System.out.println(YELLOW+"Do you want to have "+CYAN+ randomPartyMember.getName() +CYAN+"("+GREEN+"Y = Yes "+CYAN+"| "+RED+"N = No"+CYAN+")"); //Ask if they want the adventurer to join the party

        String addCharacter = game.nextLine(); //Get user's response
        addCharacter = addCharacter.toLowerCase(); //Make the user's response lower case
        
        if(addCharacter.trim().equals("yes") || addCharacter.trim().equals("y")){ //If they say yes or y then...
          userClass.addParty(randomPartyMember); //Add the party member to the party
        }
      }
    }

    Random random2 = new Random();
    int RNG2 = random2.nextInt(101);
    
    if(RNG2 <= 10){
      System.out.println(YELLOW+"You found a treasure chest!"+GREEN);
      userClass.addInventory("Health Potion");
      System.out.println(BLUE+"You got a Health Potion item."+BLUE);
    }   
  }

  //Function for events to occur depending on how far the game has progressed
  public static void events(int time, Player userClass){

    //If the game time is less than a certain value, a random event will occur
    if(time < 10){ //If time is less than 10, a event may be chosen from a pool of 4
      Random random3 = new Random();
      int RNG3 = random3.nextInt(21); //A random number will be generated

      //The random number will pick an random event to execute
      if(RNG3 <= 7){
        location = "Hidden Library"; //The location is set
        System.out.println(YELLOW+"You enter into a hidden library"+GREEN);
        userClass.addInventory("Mana Buff"); //An item is added to the player's inventory
        System.out.println(BLUE+"You got a Mana Buff item."+BLUE);
        explore(2, 4, userClass); //Function to explore the current location for some turns
      }
      else if(RNG3 <= 12){
        location = "Underground Courtyard";
        System.out.println(YELLOW+"An underground courtyard reveals itself"+GREEN);
        userClass.addInventory("Health Potion");
        System.out.println(BLUE+"You got a Health Potion item."+BLUE);
        explore(2, 4, userClass);
      }
      else if(RNG3 <= 18){
        location = "Cavern";
        System.out.println(YELLOW+"You discover a cavern"+GREEN);
        explore(2, 4, userClass);
      }
      else if(RNG3 <= 20){
        location = "Labyrinth";
        System.out.println(YELLOW+"You set foot into a labyrinth"+GREEN);
        explore(5, 8, userClass);
      }

    }

    else if(time < 20){ //If time is less than 20, a event may be chosen from a new pool of 4
      Random random3 = new Random();
      int RNG3 = random3.nextInt(21);

      if(RNG3 <= 5){
        location = "Cove";
        System.out.println(YELLOW+"You step into a cove with a small bright light shining from above"+GREEN);
        userClass.addInventory("Defence Buff");
        System.out.println(BLUE+"You got a Attack Buff item."+BLUE);
        explore(3, 5, userClass);
      }
      else if(RNG3 <= 10){
        location = "Ravine";
        System.out.println(YELLOW+"You find into a wide ravine"+GREEN);
        userClass.addInventory("Attack Buff");
        System.out.println(BLUE+"You got a Mana Buff item."+BLUE);
        explore(3, 5, userClass);
      }
      else if(RNG3 <= 15){
        location = "Underground Lake";
        System.out.println(YELLOW+"You locate a great underground lake"+GREEN);
      }
      else if(RNG3 <= 20){
        location = "Chamber Room";
        System.out.println(YELLOW+"You notice a room of chambers"+GREEN);
        explore(1, 3, userClass);
      }
    }

    else if(time < 30){ //If time is less than 20, a event may be chosen from a pool of 3
      Random random3 = new Random();
      int RNG3 = random3.nextInt(16);

      if(RNG3 <= 5){
        location = "Lost City";
        System.out.println(YELLOW+"You uncover the ruins of a lost city"+GREEN);
        explore(5, 10, userClass);
        userClass.addInventory("Attack Buff");
        System.out.println(BLUE+"You got a Mana Buff item."+BLUE);
      }
      else if(RNG3 <= 10){
        location = "Colleseum";
        System.out.println(YELLOW+"You bring yourself into a gigantic colleseum"+GREEN);
        explore(5, 10, userClass);
        userClass.addInventory("Health Potion");
        System.out.println(BLUE+"You got a Health Potion item."+BLUE);
      }
      else if(RNG3 <= 15){
        location = "Old Palace";
        System.out.println(YELLOW+"You come across a magnificent old palace"+GREEN);
        explore(5, 10, userClass);
      }
    }
  }

  //Function to "explore" the location of the events
  public static void explore(int min, int max, Player userClass){
    detour = true; //Set detour to true so it does not find a new event in travel() function
    int moves = 0; //Sets amount of moves
    gameTime++; //Time i

    while(detour == true){ //While detour is true, travel through current location
      travel(gameTime, userClass);
      moves++;
      int length = (int)Math.floor(Math.random()*(max - min + 1) + min); //Random amount of moves to leave current location

      if(moves >= length){ //If move reaches length, you exit current location and detour ends
        System.out.println(YELLOW+"You exit " + location +GREEN);
        location = "Dungeon Grounds";
        detour = false;
      }
    }
  }
} 