package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/27/2016.
 */
public class MechanicSkill extends AbsSkill {

    public MechanicSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Mechanic";
        this.description = "Desc";
    }

    @Override
    public double healPercentage(BattleCharacter partyMember) {
        if(partyMember.hasSkill(RobotSkill.class)){
            //Increase this if 10000 not enough
            return 10000.0;
        }
        else{
            return 0.0;
        }
    }
}
