package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/25/2016.
 */
public class ProtagonistSkill extends AbsSkill {

    boolean revived = false;

    public ProtagonistSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Protagonist";
    }

    @Override
    public void onDamageReceived(int damage) {
        if(owner.getCurrentHP() <= 0 && !revived){
            owner.setCurrentHP(0);
            owner.healDamage(owner.HealAmount(owner), owner);
            revived = true;
        }
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Lethal Damage Prevented: ");
        desc.append(this.revived ? "Yes\n\n" : "No\n\n");
        desc.append("When this wifey suffers lethal damage the first time, prevent the death and heal self.");
        return desc.toString();
    }
}
