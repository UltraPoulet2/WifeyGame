package ultrapoulet.wifeygame.gamestate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 4/16/2017.
 */

public class Characters {
    private static Map<String, WifeyCharacter> characters = new HashMap<>();

    public static void put(String key, WifeyCharacter character){
        characters.put(key, character);
    }

    public static WifeyCharacter get(String key){
        return characters.get(key);
    }

    public static ArrayList<String> getKeys(){
        ArrayList<String> list = new ArrayList<>();
        list.addAll(characters.keySet());
        return list;
    }

    public static ArrayList<WifeyCharacter> getArray(){
        ArrayList<WifeyCharacter> list = new ArrayList<>();
        list.addAll(characters.values());
        return list;
    }

    //For temporary purposes
    public static void remove(String key){
        characters.remove(key);
    }
}
