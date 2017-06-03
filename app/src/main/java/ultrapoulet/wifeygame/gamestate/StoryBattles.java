package ultrapoulet.wifeygame.gamestate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 2/10/2017.
 */

public class StoryBattles {

    private static List<StoryArea> storyBattles = new ArrayList<>();
    private static Map<String, BattleInfo> storyBattleMap = new HashMap<>();

    public static void addArea(StoryArea area){
        storyBattles.add(area);
        List<BattleInfo> infoList = area.getBattles();
        for(BattleInfo info : infoList){
            storyBattleMap.put(info.getKey(), info);
        }
    }

    public static List<StoryArea> getBattles(){
        return storyBattles;
    }

    public static BattleInfo getBattle(String key){
        return storyBattleMap.get(key);
    }
}
