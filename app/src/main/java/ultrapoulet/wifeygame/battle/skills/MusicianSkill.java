package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/4/2016.
 */
public class MusicianSkill extends AbsSkill{

    public MusicianSkill(BattleCharacter owner) { super(owner); }

    private int attackNum;
    private double multiplier = 4.0;

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        if(attackNum == 7){
            return multiplier;
        }
        return 1.0;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        if(attackNum == 7){
            return multiplier;
        }
        return 1.0;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        if(attackNum == 7){
            return multiplier;
        }
        return 1.0;
    }

    @Override
    public void onDamageDealt(int damage) {
        attackNum = (attackNum + 1) % 8;
    }
}
