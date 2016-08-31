package ultrapoulet.wifeygame.battle.skills.implementations;

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
    public double[] getMultipliers(BattleCharacter enemy) {
        double mult = (attackNum == 7) ? multiplier : 1.0;

        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = mult;
        multipliers[MAG_ATK] = mult;
        multipliers[SPEC_ATK] = mult;
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        double mult = (attackNum == 7) ? multiplier : 1.0;
        desc.append("Attack Multiplier: " + mult + "x\n");
        desc.append("Hit Counter: " + attackNum + "\n\n");
        desc.append("Multiplies damage dealt by 4.0x every eighth hit.");
        return desc.toString();
    }
}