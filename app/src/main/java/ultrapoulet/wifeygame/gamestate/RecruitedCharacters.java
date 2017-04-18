package ultrapoulet.wifeygame.gamestate;

import java.util.ArrayList;
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

    public static ArrayList<WifeyCharacter> getArray(){
        ArrayList<WifeyCharacter> list = new ArrayList<>();
        list.addAll(recruits.values());
        return list;
    }

    //For temporary purposes
    public static void remove(String key){
        recruits.remove(key);
    }

    public static void recruit(String key){
        recruits.put(key, Characters.get(key));
        Characters.get(key).recruit();
    }
}
