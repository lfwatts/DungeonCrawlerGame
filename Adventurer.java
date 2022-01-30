public abstract class Adventurer implements Entity{

  //Variables that all subclasses need
  protected String name;
  protected int baseHealth;
  protected int baseMana;
  protected int baseDamage;
  protected int baseDefense;
  protected int health;
  protected int mana;
  protected int damage;
  protected int level;
  protected int damageDF;
  protected int manaDF;
  public boolean isDefended;

  //Constant variables for text colour
  public static final String GREEN = "\u001B[32m";  //Colors
  public static final String RED = "\u001B[31m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String WHITE = "\u001B[37m";
  public static final String YELLOW = "\u001B[33m";
  public static final String CYAN = "\u001B[36m";
  public static final String CLEAR = "\u001b[1J"; //Clears console

  public Adventurer(){
    
  }

  public abstract String attack(Entity Enemy, boolean defending);
  public abstract void levelUp(); //Creates an abstract class for leveling up as each child class levels up differently and different values are changed.
  
  public void heal(){
    this.health += 10;
  }
  
  //Access method gets the value of the object's health
  public int getHealth(){
    return this.health;
  }
  
  //Access method gets the value of the object's name
  public String getName(){
    return this.name;
  }

  //Access method gets the value of the object's damage
  public int getDamage(){
    return this.damage;
  }

  //Access method gets the value of the object's level
  public int getLevel(){
    return this.level;
  }

  //Access method gets the value to see if the adventurer is defending themself
  public boolean getDefending(){
    return this.isDefended;
  }

  //Changes the variable that tells the adventurer to defend themself or not
  public void isDefending(boolean defending){
    this.isDefended = defending;
  }

  //Access method to defend attacks from enemies
  public void defend(int meleeDMG, int magicDMG, boolean defending){
    if(defending == false){ //If they aren't defending themselves
      //Health is subtracted by the melee damage and mana damage that is totalled up after accounting for the adventurer's defense against it.
      float h = this.health - ((meleeDMG / this.damageDF) + (magicDMG / this.manaDF));
      this.health = (int)h; //Sets health to h
      System.out.println(RED+"Damage done: " + ((meleeDMG / this.damageDF) + (magicDMG / this.manaDF))+GREEN); //Prints out damage done to the adventurer
    }
    else{ //If they are defending
      float h = this.health - ((meleeDMG / this.damageDF) + (magicDMG / this.manaDF)/2); //reduce damage by dividing damage done by a half
      this.health = (int)h;
      System.out.println(RED+"Damage done: " + ((meleeDMG / this.damageDF) + (magicDMG / this.manaDF))/2+GREEN); //display damage done to the adventurer
    }
  }

  //Adventurers are not monsters so they don't need this, but it has to be in here so that it can be used and found in entity
  public String monsterDefend(int meleeDMG, int magicDMG){
    return "";
  }

  public String monsterDefend(String x){
    return "";
  }

  //Method to recover health for adventurer subclasses
  public void heal(int amount){
    if(health < baseHealth){ //If health is less than base health of the adventurer
      health += amount; //Add the amount of health signified by the parameter
      if(health > baseHealth){ //If health is greater than base health then set the health to equal the base health of the adventurer.
        health = baseHealth;
      }
    }
  }

  //Method to use items with varying effects depending on the item
  public void useItem(String item){
    System.out.println(YELLOW+"Trying to use " +item +GREEN);

    //Checks if item equal Health Potion, Defence Buff, Attack Buff or Mana Buff
    if (item.equals("Health Potion")){
      this.health += 20; //Increases health of adventurer by 20
      if(health > baseHealth){ //If health is greater than base health then set the health to equal the base health of the adventurer.
        health = baseHealth;
      }
      System.out.println(RED+"Health increased by 20"+GREEN);
    }
    else if (item.equals("Defence Buff")){
      this.damageDF += 1;//Increases defence against damage by 1
      this.manaDF += 1; //Increases defence against mana by 1
      System.out.println(CYAN+"Defense increased"+GREEN);
    }
    else if(item.equals("Attack Buff")){
      this.damage += 10; //Increases amount of damage by 10
      System.out.println(WHITE+"Damage increased by 10"+GREEN);
    }
    else if(item.equals("Mana Buff")){
      this.mana += 10; //Increases amount of mana by 10
      System.out.println(BLUE+"Mana increased by 10"+GREEN);
    }
  }

  public void nerf(String x, int y){
    if(x.equals("D")){
      this.damage -= y;
    }
    if(x.equals("M")){
      this.mana -= y;
    }
  }

   public void statReset(){
    this.damage = this.baseDamage;
    this.damageDF = this.baseDefense;
  }

  //Access method to display the stats of the each adventurer
  public String getStats(){
    return YELLOW+"Name: " + name +RED+"\nHealth: "+ health +BLUE+"\nMana: " + mana +WHITE+"\nDamage: " + damage +PURPLE+"\nLevel:"+ level + "\n" +GREEN;
  }
}