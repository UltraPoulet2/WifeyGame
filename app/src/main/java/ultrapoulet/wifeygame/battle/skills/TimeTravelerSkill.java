package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/2/2016.
 */
public class TimeTravelerSkill extends AbsSkill {

    public TimeTravelerSkill(BattleCharacter owner){ super(owner); }

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
}
