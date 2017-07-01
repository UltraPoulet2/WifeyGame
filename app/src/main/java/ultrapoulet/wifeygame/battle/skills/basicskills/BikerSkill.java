package ultrapoulet.wifeygame.battle.skills.basicskills;

import java.util.List;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/10/2016.
 */
public class BikerSkill extends AbsSkill {

    public BikerSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Biker";
    }

    private int extraHits;

    @Override
    public void startBattle(List<BattleCharacter> party) {
        setExtraHits(party);
    }

    @Override
    public void updateParty(List<BattleCharacter> party){
        setExtraHits(party);
    }

    private void setExtraHits(List<BattleCharacter> party){
        extraHits = 0;
        for(int i = 0; i < party.size(); i++){
            if(party.get(i) != owner && party.get(i).hasSkill(BikerSkill.class)){
                extraHits++;
            }
        }
    }

    @Override
    public int getBonusHits() {
        return extraHits;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Bonus Combo Hits: " + extraHits + "\n\n");
        desc.append("Increases combo hits by 1 for each other Biker wifey in the party.");
        return desc.toString();
    }
}
