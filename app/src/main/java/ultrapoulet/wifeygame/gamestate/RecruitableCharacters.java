package ultrapoulet.wifeygame.gamestate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 4/27/2017.
 */

public class RecruitableCharacters {

    private static Map<String, WifeyCharacter> recruits = new HashMap<>();

    public static void put(String key, WifeyCharacter recruit){
        recruits.put(key, recruit);
    }

    public static WifeyCharacter get(String key){
        return recruits.get(key);
    }

    public static ArrayList<WifeyCharacter> getArray(){
        ArrayList<WifeyCharacter> list = new ArrayList<>();
        list.addAll(recruits.values());
        return list;
    }

    public static void remove(String key){
        recruits.remove(key);
    }
}
