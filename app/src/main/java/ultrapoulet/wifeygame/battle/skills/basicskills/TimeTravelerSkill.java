package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/2/2016.
 */
public class TimeTravelerSkill extends AbsSkill {

    public TimeTravelerSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Time Traveler";
    }

    private boolean revived = false;
    private int roundHealth;

    @Override
    public int startRound() {
        roundHealth = owner.getCurrentHP();
        return 0;
    }

    @Override
    public boolean canPreventDeath() {
        return !revived;
    }

    @Override
    public int preventDeath() {
        if(owner.getCurrentHP() == 0 && !revived) {
            owner.setCurrentHP(roundHealth);
            revived = true;
            return roundHealth;
        }
        return 0;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Lethal Damage Prevented: " +
                (this.revived ? "Yes\n\n" : "No\n\n") +
                "When this wifey suffers lethal damage the first time, prevent the death and set health to the wifey's health at the start of the round.";
        return desc;
    }
}
