package ultrapoulet.wifeygame.gamestate;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 2/10/2017.
 */

public class StoryArea {
    private String areaName;
    private List<BattleInfo> battles;

    public StoryArea(String name){
        this.areaName = name;
        battles = new ArrayList<>();
    }

    public String getAreaName(){
        return areaName;
    }

    public void addBattle(BattleInfo battle){
        battles.add(battle);
    }

    public List<BattleInfo> getBattles(){
        return battles;
    }

    public BattleInfo getBattle(int index){
        return battles.get(index);
    }

    public boolean isUnlocked() {
        for(BattleInfo battle : battles){
            if(battle.isUnlocked()){
                return true;
            }
        }
        return false;
    }
}
