class Goblin extends Monster{
  protected int xp = 0;
  protected String myItem = "Health Potion";
  
  Goblin(){
    this.name = "Goblin";
    this.baseHealth = 50;
    this.baseMana = 0;
    this.baseDamage = 12;
    this.health = 50;
    this.mana = 0;
    this.damage = 12;
    this.level = 0;
    this.damageDF= 2;
    this.manaDF = 1;
    this.isDefended = false;
    this.difficulty = 1;
  }
  
  Goblin(int difficulty){
    int x =  1 + (difficulty / 10);
    this.difficulty = difficulty;
    this.name = "Goblin";
    this.baseHealth = 50 * x;
    this.baseMana = 0 * x;
    this.baseDamage = 12 * x;
    this.health = 50 * x;
    this.mana = 0 * x;
    this.damage = 12 * x;
    this.level = 0 * x;
    this.damageDF= 2 * x;
    this.manaDF = 1 * x;
    this.isDefended = false;
  }

  public String attack(Entity enemy, boolean defending){
    if(enemy != null){
      enemy.defend(this.damage, this.mana, defending);
      this.xp += 10;
    }
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
      this.baseHealth += 5; 
      this.baseMana += 1;
      this.baseDamage += 15;
      xp = 0;
    }
  }

  public void heal(){
    if(health < baseHealth){
      health += 2;
    }
  }

  public void heal(String partyMember){
    //TODO fill in implementation
  }

  public boolean isAlive(){
    if(health <= 0){
      return false;
    }
    else{
      return true;
    }
  }
}