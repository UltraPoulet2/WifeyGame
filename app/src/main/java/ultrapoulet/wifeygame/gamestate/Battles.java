package ultrapoulet.wifeygame.gamestate;

import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 6/20/2016.
 */
public class Battles {
    private static Map<String, BattleInfo> battles = new HashMap<>();

    public static void put(String key, BattleInfo battle){ battles.put(key, battle); }

    public static BattleInfo get(String key){
        return battles.get(key);
    }
}
