package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/11/2016.
 */
public class ProgrammerSkill extends AbsSkill {

    public ProgrammerSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Programmer";
    }

    private double multiplier;
    private double perRound = 0.75;

    private boolean attackedRobot = false;

    @Override
    public void startWave() {
        multiplier = 1;
    }

    @Override
    public int startRound(){
        attackedRobot = false;
        return 0;
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

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        double mult = enemy.hasSkill(RobotSkill.class) ? multiplier : 1.0;
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(mult);
        returnValue.setMagAtk(mult);
        returnValue.setSpecAtk(mult);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        double mult = enemy.hasSkill(RobotSkill.class) ? multiplier : 1.0;
        desc.append("Attack Multiplier: " + mult + "x\n\n");
        desc.append("Increases damage dealt multiplier by 0.75x each time this wifey attacks a Robot. Resets at the start of the round.");
        return desc.toString();
    }
}
