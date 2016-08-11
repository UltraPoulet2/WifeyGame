package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/10/2016.
 */
public class DetectiveSkill extends AbsSkill {

    private double startMultiplier = 0.5;
    private double perTurn = 0.5;
    private double multiplier;

    public DetectiveSkill(BattleCharacter owner) { super(owner); }

    @Override
    public void startWave() {
        multiplier = startMultiplier;
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public void endRound() {
        multiplier += perTurn;
    }
}
