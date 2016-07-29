package ultrapoulet.wifeygame.battle;

/**
 * Created by John on 7/13/2016.
 */
public interface BattleCharacter {
    public int getCurrentHP();
    public int getMaxHP();
    public void setCurrentHP(int hp);
    public boolean hasSkill(Class skillClass);
    public void startBattle(BattleCharacter[] party);
    public int PowerAttackDamage(BattleCharacter enemy);
    public int ComboAttackDamage(BattleCharacter enemy);
    public int MagicAttackDamage(BattleCharacter enemy);
    public int HealAmount(BattleCharacter target);
    public int SpecialAttackDamage(BattleCharacter enemy);
    public void onDamageDealt(int damage);
    public int takePhysicalDamage(int damage, BattleCharacter enemy);
    public int takeMagicalDamage(int damage, BattleCharacter enemy);
    public int takeSpecialDamage(int damage, BattleCharacter enemy);
    public int healDamage(int heal, BattleCharacter healer);
    public void onEnemyDefeat(BattleCharacter enemy);
    public void startWave();
    public void startRound();
    public void endRound();
}
