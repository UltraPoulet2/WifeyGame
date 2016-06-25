package ultrapoulet.wifeygame.gamestate;

import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 5/15/2016.
 */
public class RecruitedCharacters {

    private static Map<String, WifeyCharacter> recruits = new HashMap<>();

    public static void put(String key, WifeyCharacter recruit){
        recruits.put(key, recruit);
    }

    public static WifeyCharacter get(String key){
        return recruits.get(key);
    }

    public static WifeyCharacter[] getArray(){
        WifeyCharacter[] chars = new WifeyCharacter[recruits.size()];
        return recruits.values().toArray(chars);
    }
}
