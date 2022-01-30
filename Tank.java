import java.util.*;

public class Tank extends Adventurer{
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
  
  public Tank(){
    this.name = "Tank";
    this.baseHealth = 200;
    this.baseMana = 0;
    this.baseDamage = 30;
    this.baseDefense = 5;
    this.health = 200;
    this.mana = 0;
    this.damage = 30;
    this.level = 0;
    this.damageDF = 5;
    this.manaDF = 2;
    this.isDefended = false;
  }

  //Overloading constructor to add nickname
  public Tank(String nickname){
    this.name = nickname + " the Tank";
    this.baseHealth = 200;
    this.baseMana = 0;
    this.baseDamage = 30;
    this.baseDefense = 5;
    this.health = 200;
    this.mana = 0;
    this.damage = 30;
    this.level = 0;
    this.damageDF = 5;
    this.manaDF = 2;
    this.isDefended = false;
  }

  public String attack(Entity enemy, boolean defending){

    //Giving the player three options to attack
    Scanner scan = new Scanner(System.in);
    System.out.println(GREEN+"Decision:"+GREEN);
    System.out.println(YELLOW+"1. Strike [A regular attack]"+GREEN);
    System.out.println(YELLOW+"2. Kamikaze [Sacrifice 100 health for massive damage]"+GREEN);
    System.out.println(YELLOW+"3. Intimidate [Lower the defense of the enemy]"+GREEN);
    String attackType = scan.nextLine();
    System.out.println("\n");

    String item = "";
    if (enemy != null){
      if(attackType.equals("1")){ //If player chooses 1, they use listed ability
        System.out.println(YELLOW+ this.name + " strikes the enemy"+GREEN);
        item = enemy.monsterDefend(this.damage, this.mana);
        this.xp += 25;   
      }
      else if(attackType.equals("2")){ //If player chooses 2,they use listed ability
        System.out.println(YELLOW+ this.name + " aggressively charges at the enemy"+GREEN);
        this.damage += 80;
        this.health -= 100;
        item = enemy.monsterDefend(this.damage, this.mana);
        this.damage -= 80;
      }
      else if(attackType.equals("3")){ //If player chooses 3, they use listed ability
        System.out.println(YELLOW+ this.name + " starts singing,\nThe enemy's initimidated"+GREEN);
        item = enemy.monsterDefend("D");
        this.damage -= 80;
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

  public void heal(){
    if(health < baseHealth){
      health += 2;
      if(health > baseHealth){
        health = baseHealth;
      }
    }
  }

  
  public void levelUp(){
    if(xp > 100){
      level += 1;
      this.baseHealth += 15; 
      this.baseMana += 1;
      this.baseDamage += 10;
      xp = 0;
    }
    System.out.println(PURPLE+"Leveling Up Tank"+GREEN);
  }

  public boolean isAlive(){
    if(health <= 0){
      return false;
    }
    return true;
  }
}