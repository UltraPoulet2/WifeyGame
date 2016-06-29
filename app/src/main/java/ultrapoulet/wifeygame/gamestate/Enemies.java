package ultrapoulet.wifeygame.gamestate;

import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.battle.BattleEnemy;

/**
 * Created by John on 6/13/2016.
 */
public class Enemies {
    private static Map<String, BattleEnemy> enemies = new HashMap<>();

    public static void put(String key, BattleEnemy battleEnemy){
        enemies.put(key, battleEnemy);
    }

    public static BattleEnemy get(String key){
        return enemies.get(key);
    }
}
