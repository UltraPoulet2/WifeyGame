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
}
