package ultrapoulet.wifeygame.battle.skills.basicskills;

import java.util.HashMap;
import java.util.Map;

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

    private double perAttack = 0.5;

    private class CharacterStatus{
        public boolean attackedRobot;
        public double multiplier = 1.0;
    }
    private Map<BattleCharacter, CharacterStatus> damageMultipliers = new HashMap<>();

    @Override
    public int startRound() {
        for(CharacterStatus status : damageMultipliers.values()) {
            if (status.attackedRobot) {
                status.multiplier += perAttack;
            }
            status.attackedRobot = false;
        }
        return 0;
    }

    private void attackCharacter(BattleCharacter enemy) {
        if(!enemy.hasSkill(RobotSkill.class)){
            return;
        }
        CharacterStatus status = damageMultipliers.get(enemy);
        if(status == null){
            status = new CharacterStatus();
            damageMultipliers.put(enemy, status);
        }
        status.attackedRobot = true;
    }

    private double getDamageMultiplier(BattleCharacter enemy) {
        if(!enemy.hasSkill(RobotSkill.class)){
            return 1.0;
        }
        else {
            CharacterStatus status = damageMultipliers.get(enemy);
            if(status == null) {
                return 1.0;
            }
            else {
                return status.multiplier;
            }
        }
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        attackCharacter(enemy);
        return getDamageMultiplier(enemy);
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        attackCharacter(enemy);
        return getDamageMultiplier(enemy);
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        attackCharacter(enemy);
        return getDamageMultiplier(enemy);
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        double mult = getDamageMultiplier(enemy);
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(mult);
        returnValue.setMagAtk(mult);
        returnValue.setSpecAtk(mult);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        double mult = getDamageMultiplier(enemy);
        desc.append("Attack Multiplier: " + String.format("%1$.2f", mult) + "x\n\n");
        desc.append("Increases damage dealt multiplier by 0.5x to a Robot for each round that it has been attacked.");
        return desc.toString();
    }
}
