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

    public List<BattleInfo> getUnlockedBattles() {
        List<BattleInfo> result = new ArrayList<>();
        for(BattleInfo battle: battles) {
            if(battle.isUnlocked()) {
                result.add(battle);
            }
        }
        return result;
    }

    public boolean isUnlocked() {
        for(BattleInfo battle : battles){
            if(battle.isUnlocked()){
                return true;
            }
        }
        return false;
    }

    public boolean isNew() {
        for(BattleInfo battle: battles){
            if(battle.getNumAttempts() > 0){
                return false;
            }
        }
        return true;
    }

    public boolean isCompleted() {
        for(BattleInfo battle: battles){
            if(battle.getNumComplete() == 0){
                return false;
            }
        }
        return true;
    }
}
