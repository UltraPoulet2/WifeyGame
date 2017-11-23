package ultrapoulet.wifeygame.battle.skills.basicskills;

import java.util.List;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/3/2016.
 */
public class SportsManagerSkill extends AbsSkill {

    public SportsManagerSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Sports Manager";
    }

    private double multiplier = 1.0;
    private double perGirl = 0.05;

    @Override
    public void startBattle(List<BattleCharacter> party) {
        setMultiplier(party);
    }

    @Override
    public void updateParty(List<BattleCharacter> party){
        setMultiplier(party);
    }

    private void setMultiplier(List<BattleCharacter> party){
        for(int i = 0; i < party.size(); i++){
            if(party.get(i) != owner && party.get(i).hasSkill(AthleteSkill.class)){
                multiplier += perGirl;
                party.get(i).giveSkillBonus(1.0, SportsManagerSkill.class, AthleteSkill.class);
            }
        }
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }


    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(multiplier);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Physical Attack Multiplier: " + String.format("%1$.2f", multiplier) + "x\n\n" +
                "Increases physical damage dealt multiplier by 0.05x for each Athlete wifey. Increases the physical damage multiplier of each Athlete wifey by 1.00x. This does not stack.";
        return desc;
    }
}
