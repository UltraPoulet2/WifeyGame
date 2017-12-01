package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/4/2016.
 */
public class MusicianSkill extends AbsSkill {

    public MusicianSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Musician";
    }

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

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        double mult = (attackNum == 7) ? multiplier : 1.0;
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(mult);
        returnValue.setMagAtk(mult);
        returnValue.setSpecAtk(mult);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        double mult = (attackNum == 7) ? multiplier : 1.0;
        String desc = "Attack Multiplier: " + String.format("%1$.2f", mult) + "x\n" +
                "Hit Counter: " + attackNum + "\n\n" +
                "Multiplies damage dealt by 4.00x every eighth hit.";
        return desc;
    }
}
