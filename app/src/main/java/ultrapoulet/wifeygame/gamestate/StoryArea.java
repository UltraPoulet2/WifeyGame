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
    private boolean unlocked = false;

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

    public void unlock(){
        unlocked = true;
    }

    public boolean isUnlocked() {
        return unlocked;
    }
}
