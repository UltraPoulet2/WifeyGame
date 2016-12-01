package ultrapoulet.wifeygame.battle.skills.implementations;

import java.util.List;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/26/2016.
 */
public class PravdaSkill extends AbsSkill {

    private boolean isPresident;

    public PravdaSkill(BattleCharacter owner, boolean president) {
        super(owner);
        this.isPresident = president;
        this.skillName = "Pravda";
        if(this.isPresident){
            this.skillName += " President";
        }
    }

    private double multiplier = 1.0;

    @Override
    public void startBattle(List<BattleCharacter> party){
        setMultiplier(party);
    }

    @Override
    public void updateParty(List<BattleCharacter> party){
        setMultiplier(party);
    }

    private void setMultiplier(List<BattleCharacter> party){
        for(int i = 0; i < party.size(); i++){
            if(party.get(i) != owner && party.get(i).hasSkill(PravdaSkill.class)){
                multiplier += 1.0;
                if(isPresident){
                    multiplier += 1.0;
                }
            }
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
        desc.append("Attack Multiplier: " + multiplier + "x\n\n");
        desc.append("Increases damage dealt multiplier by ");
        desc.append(this.isPresident ? "2.0x" : "1.0x");
        desc.append(" for each other Pravda wifey.");
        return desc.toString();
    }
}
