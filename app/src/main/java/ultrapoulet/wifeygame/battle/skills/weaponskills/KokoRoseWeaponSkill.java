package ultrapoulet.wifeygame.battle.skills.weaponskills;

import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsWeaponSkill;

/**
 * Created by John on 6/3/2017.
 */

public class KokoRoseWeaponSkill extends AbsWeaponSkill {

    private final double perAttack = 0.05;
    private final double minDefense = 0.5;

    private class CharacterStatus{
        public boolean attacked;
        public double multiplier = 1.0;
    }
    private Map<BattleCharacter, CharacterStatus> damageMultipliers = new HashMap<>();

    public KokoRoseWeaponSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Koko's Roses";
    }

    //@Override
    /*
    public void startWave() {
        for(CharacterStatus : damageMultipliers.keySet()) {
            multiplier = startMultiplier;
            attackUsed = false;
        }
    }
    */

    @Override
    public int startRound() {
        for(CharacterStatus status : damageMultipliers.values()) {
            if (status.attacked && status.multiplier > minDefense) {
                status.multiplier -= perAttack;
            }
            status.attacked = false;
        }
        return 0;
    }

    private void attackCharacter(BattleCharacter enemy){
        CharacterStatus status = damageMultipliers.get(enemy);
        if(status == null){
            status = new CharacterStatus();
            damageMultipliers.put(enemy, status);
        }
        status.attacked = true;
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        attackCharacter(enemy);
        return 1.0;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        attackCharacter(enemy);
        return 1.0;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        attackCharacter(enemy);
        return 1.0;
    }

    private double getDefense(BattleCharacter enemy){
        CharacterStatus status = damageMultipliers.get(enemy);
        return (status == null) ? 1.0 : status.multiplier;
    }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return getDefense(enemy);
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        return getDefense(enemy);
    }

    @Override
    public double receiveSpecialAttackPercentage(BattleCharacter enemy) {
        return getDefense(enemy);
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysDef(getDefense(enemy));
        returnValue.setMagDef(getDefense(enemy));
        returnValue.setSpecDef(getDefense(enemy));
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Defense Multiplier: " +  String.format("%1$.2f", getDefense(enemy)) + "x\n\n");
        desc.append("Decreases physical, magical, and special damage taken multiplier by " + String.format("%1$.2f", perAttack) + "x from an enemy for each round that it has been attacked, to a minimum of 0.50x.");
        return desc.toString();
    }
}
