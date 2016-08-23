package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

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
    public void startRound() {
        roundHealth = owner.getCurrentHP();
    }

    @Override
    public void onDamageReceived(int damage) {
        if(owner.getCurrentHP() <= 0 && !revived){
            owner.setCurrentHP(roundHealth);
            revived = true;
        }
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Lethal Damage Prevented: ");
        desc.append(this.revived ? "Yes\n\n" : "No\n\n");
        desc.append("When this wifey suffers lethal damage the first time, prevent the death and set health to the wifey's health at the start of the round.");
        return desc.toString();
    }
}
