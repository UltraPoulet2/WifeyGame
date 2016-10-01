package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/1/2016.
 */
public class MagicalGirlSkill extends AbsSkill {

    public MagicalGirlSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Magical Girl";
    }

    private double baseMultiplier = 1.5;
    private double perGirl = 0.25;

    private double multiplier;

    @Override
    public void startBattle(BattleCharacter[] party) {
        setMultiplier(party);
    }

    @Override
    public void updateParty(BattleCharacter[] party){
        setMultiplier(party);
    }

    private void setMultiplier(BattleCharacter[] party){
        multiplier = baseMultiplier;
        for(int i = 0; i < party.length; i++){
            if(party[i] != owner && party[i].hasSkill(MagicalGirlSkill.class)){
                multiplier += perGirl;
            }
        }
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double healPercentage(BattleCharacter partyMember) {
        return multiplier;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }


    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
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
        desc.append("Magic Attack Multiplier: " + multiplier + "\n");
        desc.append("Special Attack Multiplier: " + multiplier + "\n");
        desc.append("Healing Multiplier: " + multiplier + "\n\n");
        desc.append("Multiplies magical damage dealt, healing, and special attack damage dealt by 1.5x. Multiplier increases by 0.25x for each other Magical Girl wifey in the party.");
        return desc.toString();
    }
}
