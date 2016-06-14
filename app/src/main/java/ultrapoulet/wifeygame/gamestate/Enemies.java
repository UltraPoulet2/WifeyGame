package ultrapoulet.wifeygame.gamestate;

import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.battle.Enemy;

/**
 * Created by John on 6/13/2016.
 */
public class Enemies {
    private static Map<String, Enemy> enemies = new HashMap<>();

    public static void put(String key, Enemy enemy){
        enemies.put(key, enemy);
    }

    public static Enemy get(String key){
        return enemies.get(key);
    }
}
