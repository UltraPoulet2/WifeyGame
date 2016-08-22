package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/31/2016.
 */
public class MaidSkill extends AbsSkill {

    public MaidSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Maid";
    }

    private boolean healLastTurn = false;
    private boolean healCurrentTurn = false;

    @Override
    public void startRound() {
        healLastTurn = healCurrentTurn;
        healCurrentTurn = false;
    }

    @Override
    public double healPercentage(BattleCharacter partyMember) {
        healCurrentTurn = true;
        return 1.25;
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        if(healLastTurn) {
            return 2.0;
        }
        else {
            return 1.0;
        }
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        if(healLastTurn) {
            return 2.0;
        }
        else {
            return 1.0;
        }
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        if(healLastTurn) {
            return 2.0;
        }
        else {
            return 1.0;
        }
    }


    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multiplier = healLastTurn ? 2.0: 1.0;

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
        desc.append("Attack Multiplier: ");
        desc.append(healLastTurn ? "2.0x\n\n" : "1.0x\n\n");
        desc.append("Multiplies damage dealt by 2.0x if this wifey healed the previous turn.");
        return desc.toString();
    }
}
