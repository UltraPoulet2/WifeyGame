package ultrapoulet.wifeygame.battle.skills.basicskills;

import java.util.List;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/13/2016.
 */
public class FujoshiSkill extends AbsSkill {

    public FujoshiSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Fujoshi";
    }

    private double multiplier;
    private double perTrap = 1.0/3.0;
    private double twoTrapBonus = 1.0;

    @Override
    public void startBattle(List<BattleCharacter> party) {
        setMultiplier(party);
    }

    @Override
    public void updateParty(List<BattleCharacter> party){
        setMultiplier(party);
    }

    private void setMultiplier(List<BattleCharacter> party){
        multiplier = 1.0;
        int count = 0;
        for(int i = 0; i < party.size(); i++){
            if(party.get(i) != owner && party.get(i).hasSkill(TrapSkill.class)){
                count++;
            }
        }
        multiplier += count * perTrap;
        if(count >= 2){
            multiplier += twoTrapBonus;
        }
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
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(multiplier);
        returnValue.setMagAtk(multiplier);
        returnValue.setSpecAtk(multiplier);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Attack Multiplier: " + String.format("%1$.2f", multiplier) + "x\n\n");
        desc.append("Increases damage dealt multiplier by 0.33x for each Trap wifey in the party. Damage increased by a bonus 1.00x if there are more than two Trap wifeys.");
        return desc.toString();
    }
}
