package ultrapoulet.wifeygame.battle.skills.implementations;

import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 3/7/2017.
 */

public class PoliceSkill extends AbsSkill{

    private int totalBonusGold;
    private static Map<Class, Integer> criminalMap;

    public PoliceSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Police";
        if(criminalMap == null){
            setUpMap();
        }
    }
    
    @Override
    public void onEnemyDefeat(BattleCharacter enemy){
        totalBonusGold += enemyBonusGold(enemy);
    }

    private int enemyBonusGold(BattleCharacter enemy){
        int bonus = 0;
        for (Map.Entry<Class, Integer> criminalSkill : criminalMap.entrySet() ) {
            Class skill = criminalSkill.getKey();
            if(enemy.hasSkill(skill)){
                bonus += criminalSkill.getValue();
            }
        }
        return bonus;
    }

    @Override
    public int getBonusGold(){
        return totalBonusGold;
    }

    @Override
    public String getDescription(BattleCharacter enemy){
        StringBuilder desc = new StringBuilder();
        desc.append("Current Enemy Bonus Gold: " + enemyBonusGold(enemy) + "\n");
        desc.append("Current Total Bonus Gold: " + totalBonusGold + "\n\n");
        desc.append("Gives bonus gold by defeating an enemy with a criminal skill.");
        return desc.toString();
    }

    private void setUpMap(){
        criminalMap = new HashMap<>();
        criminalMap.put(KillerSkill.class, 200);
        criminalMap.put(BikerSkill.class, 50);
    }


}
