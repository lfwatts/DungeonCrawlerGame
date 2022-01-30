interface Entity{
  public String getName();
  public int getHealth();
  public int getDamage();
  public int getLevel();
  public String attack(Entity enemy, boolean defending);
  public boolean isAlive();
  public String getStats();
  public void levelUp();
  public void isDefending(boolean defending);
  public boolean getDefending();
  public void defend(int meleeDMG, int magicDMG, boolean defending);
  public String monsterDefend(int meleeDMG, int magicDMG);
  public String monsterDefend(String x);
  public void heal();
  public void heal(int amount);
  public void useItem(String item);
  public void nerf(String letter, int val);
  public void statReset();
}