package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/10/2016.
 */
public class BikerSkill extends AbsSkill {

    public BikerSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Biker";
        this.description = "Desc";
    }

    private int extraHits = 0;

    @Override
    public void startBattle(BattleCharacter[] party) {
        for(int i = 0; i < party.length; i++){
            if(party[i] != owner && party[i].hasSkill(BikerSkill.class)){
                extraHits++;
            }
        }
    }

    @Override
    public int getBonusHits() {
        return extraHits;
    }
}
