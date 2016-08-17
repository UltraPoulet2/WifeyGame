package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/11/2016.
 */
public class ProgrammerSkill extends AbsSkill {

    public ProgrammerSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Programmer";
        this.description = "Desc";
    }

    private double multiplier;
    private double perRound = 0.75;

    private boolean attackedRobot = false;

    @Override
    public void startWave() {
        multiplier = 1;
    }

    @Override
    public void startRound(){
        attackedRobot = false;
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        if(enemy.hasSkill(RobotSkill.class)) {
            attackedRobot = true;
            return multiplier;
        }
        return 1;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        if(enemy.hasSkill(RobotSkill.class)) {
            attackedRobot = true;
            return multiplier;
        }
        return 1;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        if(enemy.hasSkill(RobotSkill.class)) {
            attackedRobot = true;
            return multiplier;
        }
        return 1;
    }

    @Override
    public void endRound() {
        if(attackedRobot){
            multiplier += perRound;
        }
    }
}
