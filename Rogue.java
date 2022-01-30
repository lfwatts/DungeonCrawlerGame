import java.util.*;

public class Rogue extends Adventurer{
  public int xp = 0;

  //Constant variables for text colour
  public static final String GREEN = "\u001B[32m";  //Colors
  public static final String RED = "\u001B[31m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String WHITE = "\u001B[37m";
  public static final String YELLOW = "\u001B[33m";
  public static final String CYAN = "\u001B[36m";
  public static final String CLEAR = "\u001b[1J"; //Clears console
  
  public Rogue(){
    this.name = "Rogue";
    this.baseHealth = 85;
    this.baseMana = 0;
    this.baseDamage = 35;
    this.baseDefense = 2;
    this.health = 85;
    this.mana = 0;
    this.damage = 35;
    this.level = 0;
    this.damageDF = 2;
    this.manaDF = 2;
    this.isDefended = false;  
  }

  //Overloading constructor to add nickname
  public Rogue(String nickname){
    this.name = nickname + " the Rogue";
    this.baseHealth = 85;
    this.baseMana = 0;
    this.baseDamage = 35;
    this.baseDefense = 2;
    this.health = 85;
    this.mana = 0;
    this.damage = 35;
    this.level = 0;
    this.damageDF = 2;
    this.manaDF = 2;
    this.isDefended = false;  
  }

  public String attack(Entity enemy, boolean defending){
    
    //Giving the player three options to attack
    Scanner scan = new Scanner(System.in);
    System.out.println(GREEN+"Decision:"+GREEN);
    System.out.println(YELLOW+"1. Pierce [Chance to deal more damage]"+GREEN);
    System.out.println(YELLOW+"2. Poison [Reduces damage of enemy]"+GREEN);
    System.out.println(YELLOW+"3. Tickle [Reduces defense of enemy]"+GREEN);
    String attackType = scan.nextLine();
    System.out.println("\n");

    String item = "";
    if (enemy != null){
      if(attackType.equals("1")){ //If player chooses 1,they use listed ability
        Random random = new Random();
        int RNG = random.nextInt(101);
        if(RNG < 33){
          this.damage += 25;
          System.out.println(RED+"Critical Hit!"+GREEN);
        }
        System.out.println(YELLOW+ this.name + " swiftly hits the enemy"+GREEN);
        item = enemy.monsterDefend(this.damage, this.mana);
        if(RNG < 33){
          this.damage -= 25;
        }   
      }
      else if(attackType.equals("2")){ //If player chooses 2,they use listed ability
        System.out.println(YELLOW+ this.name + " pinches the enemy with a peculiar needle"+GREEN);
        item = enemy.monsterDefend("O");
      }
      else if(attackType.equals("3")){ //If player chooses 2,they use listed ability
        System.out.println(YELLOW+ this.name + " tickles the enemy"+GREEN);
        item = enemy.monsterDefend("D");
      }
      else{
        System.out.println(YELLOW+ this.name + " stumbles and gently taps on the monster"+GREEN); //If player chooses anything else, they attack weakly
        int temp = this.damage;
        this.damage = 1;
        item = enemy.monsterDefend(this.damage, this.mana);
        this.damage += temp;
      }
      this.xp += 25; 
    }
    return item;
  }
  
  public void levelUp(){
    if(xp >= 100){
      level += 1;
      this.baseHealth += 5; 
      this.mana += 1;
      this.damage += 15;
      xp = 0;
    }
    System.out.println(PURPLE+"Leveling Up Rogue"+GREEN);
  }

  public void heal(){
    if(health < baseHealth){
      health += 2;
      if(health > baseHealth){
        health = baseHealth;
      }
    }
  }

  public boolean isAlive(){
    if(health <= 0){
      return false;
    }
    return true;
  }
}