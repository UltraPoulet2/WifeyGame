package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/3/2016.
 */
public class SurvivalSkill extends AbsSkill {

    public SurvivalSkill(BattleCharacter owner, boolean pres){
        super(owner);
        this.isPresident = pres;
        if(pres){
            perTurn = 0.02;
        }
    }

    private boolean isPresident;
    private double defense = 0.0;
    private double perTurn = 0.01;

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return defense;
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        return defense;
    }

    @Override
    public double receiveSpecialAttackPercentage(BattleCharacter enemy) {
        return defense;
    }

    @Override
    public void endRound() {
        double damagePercent = 1 - defense;
        defense += damagePercent * perTurn;
    }
}
