package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/29/2016.
 */
public class SlugabedSkill extends AbsSkill {

    public SlugabedSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Slugabed";
        this.description = "Desc";
    }

    private double currentMultiplier = 6.0;
    private double minMultiplier = 0.25;
    private double perTurn = 0.25;

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
