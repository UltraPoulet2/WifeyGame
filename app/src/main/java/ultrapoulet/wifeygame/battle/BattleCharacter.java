package ultrapoulet.wifeygame.battle;

/**
 * Created by John on 7/13/2016.
 */
public interface BattleCharacter {
    public int getCurrentHP();
    public int getMaxHP();
    public void battleStart();
    public int PowerAttackDamage();
    public int ComboAttackDamage();
    public int MagicAttackDamage();
    public int HealAmount();
    public int SpecialAttackDamage();
    public int takePhysicalDamage(int damage);
    public int takeMagicalDamage(int damage);
    public int takeSpecialDamage(int damage);
    public int healDamage(int heal);
}
