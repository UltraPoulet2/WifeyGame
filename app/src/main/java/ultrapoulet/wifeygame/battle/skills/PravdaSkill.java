package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

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
    public void startBattle(BattleCharacter[] party){
        for(int i = 0; i < party.length; i++){
            if(party[i] != owner && party[i].hasSkill(PravdaSkill.class)){
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
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = multiplier;
        multipliers[MAG_ATK] = multiplier;
        multipliers[SPEC_ATK] = multiplier;
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
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
