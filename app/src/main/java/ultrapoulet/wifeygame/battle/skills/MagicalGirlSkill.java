package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/1/2016.
 */
public class MagicalGirlSkill extends AbsSkill {

    public MagicalGirlSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Magical Girl";
        this.description = "Desc";
    }

    private double baseMultiplier = 1.5;
    private double perGirl = 0.25;

    private double multiplier;

    @Override
    public void startBattle(BattleCharacter[] party) {
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
}
