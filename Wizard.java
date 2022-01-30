import java.util.*;

public class Wizard extends Adventurer{
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
  
  public Wizard(){
    this.name = "Wizard";
    this.baseHealth = 70;
    this.baseMana = 15;
    this.baseDamage = 40;
    this.baseDefense = 2;
    this.health = 70;
    this.mana = 15;
    this.damage = 40;
    this.level = 0;
    this.damageDF = 2;
    this.manaDF = 4;
    this.isDefended = false;
  }

  //Overloading constructor to add nickname
  public Wizard(String nickname){
    this.name = nickname + " Wizard";
    this.baseHealth = 70;
    this.baseMana = 15;
    this.baseDamage = 40;
    this.baseDefense = 2;
    this.health = 70;
    this.mana = 15;
    this.damage = 40;
    this.level = 0;
    this.damageDF = 2;
    this.manaDF = 4;
    this.isDefended = false;
  }


  public String attack(Entity enemy, boolean defending){

    //Giving the player three options to attack
    Scanner scan = new Scanner(System.in);
    System.out.println(GREEN+"Decision:"+GREEN);
    System.out.println(YELLOW+"1. Lightning [Targets a single enemy]"+GREEN);
    System.out.println(YELLOW+"2. Recharge [Regenerate 2 mana]"+GREEN);
    String attackType = scan.nextLine();
    System.out.println("\n");

    String item = "";
    if (enemy != null){
      if(this.mana > 0){
        if(attackType.equals("1")){ //If player chooses 1,they use listed ability
          System.out.println(YELLOW+ this.name + " zaps the opposition!"+GREEN);
          item = enemy.monsterDefend(this.damage, this.mana);
          this.mana -= 2;
        }
        else if(attackType.equals("2")){ //If player chooses 2,they use listed ability
          System.out.println(YELLOW+ this.name + " experiences tranquility..."+GREEN);
          this.mana += 2;
        }
        else{
          System.out.println(YELLOW+ this.name + " stumbles and gently taps on the monster"+GREEN); //If player chooses anything else, they attack weakly
          int temp = this.damage;
          this.damage = 1;
          item = enemy.monsterDefend(this.damage, this.mana);
          this.damage += temp;
        }
      }
      if(this.mana <= 0){
        System.out.println("No more mana, go recharge next turn");
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
    System.out.println(PURPLE+"Leveling Up Wizard"+GREEN);
  }

  public boolean isAlive(){
    if(health <= 0){
      return false;
    }
    return true;
  }
}