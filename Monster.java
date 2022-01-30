public abstract class Monster implements Entity{

  //Variables that all subclasses need
  protected String name;
  protected int baseHealth;
  protected int baseMana;
  protected int baseDamage;
  protected int health;
  protected int mana;
  protected int damage;
  protected int level;
  protected int damageDF;
  protected int manaDF;
  public static boolean isDefended;
  public static int difficulty;

  //Constant variables for text colour
  public static final String GREEN = "\u001B[32m";  //Colors
  public static final String RED = "\u001B[31m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String WHITE = "\u001B[37m";
  public static final String YELLOW = "\u001B[33m";
  public static final String CYAN = "\u001B[36m";
  public static final String CLEAR = "\u001b[1J"; //Clears console

  public Monster(){ 
  
  }

  public abstract String attack(Entity enemy, boolean defending);
  public abstract String monsterDefend(int meleeDMG, int magicDMG);

  public String monsterDefend(String x){
    if(x.equals("D")){
      this.damageDF = this.damageDF - 1;
      this.manaDF = this.manaDF - 1;
    }
    if(x.equals("D")){
      this.damage -= 1;
    }
    return "";
  }
  
  public abstract void heal();
  public abstract void heal(String partyMember);

  public void isDefending(boolean defending){
    this.isDefended = defending;
  }

  public boolean getDefending(){
    return this.isDefended;
  }
  
  public void defend(int meleeDMG, int magicDMG, boolean defending){
  }

  public void heal(int amount){
    if(health < baseHealth){
      health += amount;
      if(health > baseHealth){
        health = baseHealth;
      }
    }
  }

  public int getHealth(){
    return this.health;
  }

  public int getDamage(){
    return this.damage;
  }

  public int getLevel(){
    return this.level;
  }

  public void useItem(String item){
    //Do nothing for monters as they are stupid and don't know how to use items
  }
  
  public String getName(){
    return this.name;
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
    //Monster only disappear, no stat reset
  }

  public String getStats(){
    return YELLOW+"Name: " + name +CYAN+" | "+RED+"Health: " + health +CYAN+" | "+BLUE+ "Mana: " + mana +CYAN+" | "+WHITE+"Damage: " + damage +CYAN+" | "+PURPLE+"Level: " + difficulty +CYAN+"\n"+GREEN;
  }
}