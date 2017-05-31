package ultrapoulet.wifeygame.gamestate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ultrapoulet.wifeygame.character.SkillsEnum;
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

    //Returns the number of wifeys recruited
    public static int getNumberRecruited(){
        return recruits.size();
    }

    //Returns the number of wifeys with a ceratin skill
    public static int getNumberRecruited(SkillsEnum skill){
        int total = 0;
        for (WifeyCharacter wifey: recruits.values() ) {
            if(wifey.getSkills().contains(skill)){
                total++;
            }
        }
        return total;
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
}
