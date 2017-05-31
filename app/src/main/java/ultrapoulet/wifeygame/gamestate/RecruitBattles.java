package ultrapoulet.wifeygame.gamestate;

import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 5/21/2017.
 */

public class RecruitBattles {

    private static Map<String, BattleInfo> recruitBattles = new HashMap<>();

    public static void put(String key, BattleInfo battle){
        recruitBattles.put(key, battle);
    }

    public static BattleInfo getBattle(String key){
        return recruitBattles.get(key);
    }
}
