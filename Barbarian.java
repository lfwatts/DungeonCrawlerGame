import java.util.*;

public class Barbarian extends Adventurer{
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

  public Barbarian(){
    this.name = "Barbarian";
    this.baseHealth = 100;
    this.baseMana = 0;
    this.baseDamage = 50;
    this.baseDefense = 3;
    this.health = 100;
    this.mana = 0;
    this.damage = 50;
    this.level = 0;
    this.damageDF = 3;
    this.manaDF = 1;
    this.isDefended = false;
  }

  //Overloading constructor to add nickname
  public Barbarian(String nickname){
    this.name = nickname + " the Barbarian" ;
    this.baseHealth = 100;
    this.baseMana = 0;
    this.baseDamage = 50;
    this.baseDefense = 3;
    this.health = 100;
    this.mana = 0;
    this.damage = 50;
    this.level = 0;
    this.damageDF = 3;
    this.manaDF = 1;
    this.isDefended = false;
  }

  public String attack(Entity enemy, boolean defending){
    
    //Giving the player three options to attack
    Scanner scan = new Scanner(System.in);
    System.out.println(GREEN+"Decision:"+GREEN);
    System.out.println(YELLOW+"1. Assault [Regular attack]"+GREEN);
    System.out.println(YELLOW+"2. Adrenaline [Reduce defense for slight damage buff]"+GREEN);
    System.out.println(YELLOW+"3. Resiliency [Reduce damage for slight defense buff]"+GREEN);
    String attackType = scan.nextLine();
    System.out.println("\n");

    String item = "";
    if (enemy != null){
      if(attackType.equals("1")){ //If player chooses 1,they use listed ability
        System.out.println(YELLOW+ this.name + " attacks the enemy"+GREEN);
        item = enemy.monsterDefend(this.damage, this.mana);
      }
      else if(attackType.equals("2")){ //If player chooses 2,they use listed ability
        System.out.println(YELLOW+ this.name + " hypes himself up"+GREEN);
        this.damage += 10;
        this.damageDF -= 1;
      }
      else if(attackType.equals("3")){ //If player chooses 2,they use listed ability
        System.out.println(YELLOW+ this.name + " focuses his mind"+GREEN);
        this.damage -= 10;
        this.damageDF += 1;
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
    if(xp > 100){
      level += 1;
      this.baseHealth += 15; 
      this.baseMana += 1;
      this.baseDamage += 15;
      xp = 0;
    }
    System.out.println(PURPLE+"Leveling Up Barbarian"+GREEN);
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