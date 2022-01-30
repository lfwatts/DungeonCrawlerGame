class Witch extends Monster{
  protected int xp = 0;
  protected String myItem = "Mana Buff";
  
  Witch(){
    this.name = "Witch";
    this.baseHealth = 80;
    this.baseMana = 10;
    this.baseDamage = 10;
    this.health = 80;
    this.mana = 10;
    this.damage = 10;
    this.level = 0;
    this.damageDF= 2;
    this.manaDF = 3;
    this.isDefended = false;
    this.difficulty = 1;
  }

  Witch(int difficulty){
    int x =  1 + (difficulty / 10);
    this.difficulty = difficulty;
    this.name = "Witch";
    this.baseHealth = 80 * x;
    this.baseMana = 10 * x;
    this.baseDamage = 10 * x;
    this.health = 80 * x;
    this.mana = 10 * x;
    this.damage = 10 * x;
    this.level = 0 * x;
    this.damageDF= 2 * x;
    this.manaDF = 3 * x;
    this.isDefended = false;

  }

  public String attack(Entity enemy, boolean defending){
    enemy.defend(this.damage, this.mana, defending);
    this.xp += 25;
    return "";
  }

  public String monsterDefend(int meleeDMG, int magicDMG){
    float h = this.health - (meleeDMG / this.damageDF) - (magicDMG / this.manaDF);
    this.health = (int)h;
    if (this.isAlive()){
      return "";
    }
    else{
      return this.myItem;
    }
  }
  
  public void levelUp(){
    if(xp >= 50){
      level += 1;
      this.baseHealth += 10; 
      this.baseMana += 10;
      this.baseDamage += 10;
      xp = 0;
    }
  }

  public void heal(){
    if(health < baseHealth){
      health += 15;
    }
  }

  public void heal(String partyMember){

  }

  public boolean isAlive(){
    if(health <= 0){
      return false;
    }
    return true;
  }
}