package ultrapoulet.wifeygame.gamestate;

import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.character.EnemyCharacter;

/**
 * Created by John on 6/13/2016.
 */
public class Enemies {
    private static Map<String, EnemyCharacter> enemies = new HashMap<>();

    public static void put(String key, EnemyCharacter enemy){
        enemies.put(key, enemy);
    }

    public static EnemyCharacter get(String key){
        return enemies.get(key);
    }
}
