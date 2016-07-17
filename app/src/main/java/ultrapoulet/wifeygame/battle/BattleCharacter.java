package ultrapoulet.wifeygame.battle;

/**
 * Created by John on 7/13/2016.
 */
public interface BattleCharacter {
    public int getCurrentHP();
    public int getMaxHP();
    public void battleStart();
    public int PowerAttackDamage(BattleCharacter enemy);
    public int ComboAttackDamage(BattleCharacter enemy);
    public int MagicAttackDamage(BattleCharacter enemy);
    public int HealAmount();
    public int SpecialAttackDamage(BattleCharacter enemy);
    public void onDamageDealt(int damage);
    public int takePhysicalDamage(int damage);
    public int takeMagicalDamage(int damage);
    public int takeSpecialDamage(int damage);
    public int healDamage(int heal);
    public void onEnemyDefeat(BattleCharacter enemy);
}
