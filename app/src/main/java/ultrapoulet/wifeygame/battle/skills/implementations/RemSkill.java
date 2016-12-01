package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 8/29/2016.
 */
public class RemSkill extends AbsSkill {

    private double multiplier = 2.0;

    public RemSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Rem";
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
    public double healPercentage(BattleCharacter partyMember) {
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
        desc.append("Attack Multiplier: " + multiplier + "\n");
        desc.append("Healing Multiplier: " + multiplier + "\n\n");
        desc.append("Multiplies damage dealt by 4.0x at the start of a wave. Multiplier decreases by 0.5x for each turn the wave lasts, to a minimum of 0.5x.");
        return desc.toString();
    }

    @Override
    public void onDamageReceived(int damage) {
        if(owner.getCurrentHP() <= 0){
            WifeyCharacter rem = RecruitedCharacters.get("TEST-RREM");
            int index = Party.getWifeyIndex(rem);
            if(index != -1){
                Party.removeIndex(index);
            }
            RecruitedCharacters.remove("TEST-RREM");
        }
    }
}
