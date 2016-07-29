package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/29/2016.
 */
public class RacerSkill extends AbsSkill {

    public RacerSkill(BattleCharacter owner){ super(owner); }

    private double startMultiplier = 4.0;
    private double perTurn = 0.5;
    private double minMultiplier = 0.5;
    private double currentMultiplier;

    @Override
    public void startWave() {
        currentMultiplier = startMultiplier;
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return currentMultiplier;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return currentMultiplier;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return currentMultiplier;
    }

    @Override
    public void endRound(){
        currentMultiplier -= perTurn;
        if(currentMultiplier < minMultiplier){
            currentMultiplier = minMultiplier;
        }
    }
}
