package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/28/2016.
 */
public class NurseSkill extends AbsSkill {

    public NurseSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Nurse";
    }

    @Override
    public double healPercentage() {
        return 2.0;
    }

    @Override
    public double healPercentage(BattleCharacter partyMember) {
        return 2.0;
    }

    @Override
    public double startRoundPartyRegenPercentage() {
        return 0.1;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Healing Multiplier: 2.00x\n\n" +
                "Multiplies healing by 2.00x. Heals the party slightly at the start of the round.";
        return desc;
    }
}
