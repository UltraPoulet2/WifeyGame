package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/1/2016.
 */
public class WitchSkill extends AbsSkill {

    private double increase = 0.2;
    private int numUses = 0;
    private boolean magicCurrentTurn = false;

    public WitchSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Witch";
        this.description = "Desc";
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        magicCurrentTurn = true;
        return 1.0 + increase * numUses;
    }

    @Override
    public void startRound() {
        magicCurrentTurn = false;
    }

    @Override
    public void endRound() {
        if(magicCurrentTurn){
            numUses++;
        }
    }


    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
        multipliers[MAG_ATK] = 1.0 + increase * numUses;
        multipliers[SPEC_ATK] = 1.0;
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }
}
