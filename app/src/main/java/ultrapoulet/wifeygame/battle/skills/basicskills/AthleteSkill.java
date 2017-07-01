package ultrapoulet.wifeygame.battle.skills.basicskills;

import java.util.List;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/3/2016.
 */
public class AthleteSkill extends AbsSkill {

    public AthleteSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Athlete";
    }

    private double baseMultiplier = 1.5;
    private double multiplier = baseMultiplier;
    private double perGirl = 0.5;

    private boolean managerFound = false;

    @Override
    public void startBattle(List<BattleCharacter> party) {
        setMultiplier(party);
    }

    @Override
    public void resetValues(){
        multiplier = baseMultiplier;
        managerFound = false;
    }

    @Override
    public void updateParty(List<BattleCharacter> party){
        setMultiplier(party);
    }

    private void setMultiplier(List<BattleCharacter> party){
        for(int i = 0; i < party.size(); i++){
            if(party.get(i) != owner && party.get(i).hasSkill(AthleteSkill.class)){
                multiplier += perGirl;
            }
        }
    }

    @Override
    public void receiveBonus(double multiplier, Class givingSkill){
        if(givingSkill == SportsManagerSkill.class && !managerFound){
            this.multiplier += multiplier;
            managerFound = true;
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
        StringBuilder desc = new StringBuilder();
        desc.append("Physical Attack Multiplier: " + String.format("%1$.2f", multiplier) + "x\n\n");
        desc.append("Multiplies physical damage dealt by 1.50x. Increases multiplier by 0.50x for each other Athlete wifey in the party.");
        return desc.toString();
    }
}
