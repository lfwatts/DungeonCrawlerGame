import java.util.*;

public class Cleric extends Adventurer{
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
  
  public Cleric(){
    this.name = "Cleric";
    this.baseHealth = 70;
    this.baseMana = 100;
    this.baseDamage = 30;
    this.baseDefense = 1;
    this.health = 70;
    this.mana = 0;
    this.damage = 30;
    this.level = 0;
    this.damageDF = 1;
    this.manaDF = 2;
    this.isDefended = false;
  }

    //Overloading constructor to add nickname
  public Cleric(String nickname){
    this.name = nickname + " the Cleric";
    this.baseHealth = 70;
    this.baseMana = 100;
    this.baseDamage = 30;
    this.baseDefense = 1;
    this.health = 70;
    this.mana = 0;
    this.damage = 30;
    this.level = 0;
    this.damageDF = 1;
    this.manaDF = 2;
    this.isDefended = false;
  }

  public String attack(Entity enemy, boolean defending){
    System.out.println(YELLOW+ this.name + " punches the enemy"+GREEN);
    String item = "";
    if (enemy != null){
      item = enemy.monsterDefend(this.damage, this.mana);
      this.xp += 25;
    }
    return item;
  }
  
  public void levelUp(){
    if(xp > 100){
      level += 1;
      this.baseHealth += 15; 
      this.mana += 1;
      this.damage += 5;
      xp = 0;
    }
    System.out.println(PURPLE+"Leveling Up Cleric"+GREEN);
  }

  public void heal(){
    if(health < baseHealth){
      health += 20;
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