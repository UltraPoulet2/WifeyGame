package ultrapoulet.wifeygame.gamestate;

import android.content.SharedPreferences;

import java.util.List;

import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.battle.BattleWifey;

/**
 * Created by John on 5/13/2016.
 */
public class Party {

    private static WifeyCharacter[] party = new WifeyCharacter[7];

    private static SharedPreferences prefs;

    public static void init(SharedPreferences inPrefs){
        prefs = inPrefs;
    }

    //Two version of setParty until cleanup
    public static void setParty(List<WifeyCharacter> input){
        for(int i = 0; i < input.size(); i++){
            party[i] = input.get(i);
        }
        for(int i = input.size(); i < 7; i++){
            party[i] = null;
        }
        saveParty();
    }

    public static void setParty(WifeyCharacter[] input){
        for(int i = 0; i < input.length; i++){
            party[i] = input[i];
        }
        for(int i = input.length; i < 7; i++){
            party[i] = null;
        }
        saveParty();
    }

    public static boolean inParty(WifeyCharacter inputChar){
        //Returns true if in party, false otherwise
        for(int i = 0; i < party.length; i++){
            if(party[i] == inputChar){
                return true;
            }
        }
        return false;
    }

    public static void setIndex(int index, WifeyCharacter inputChar){
        if(inParty(inputChar) || index < 0 || index >= 7){
            return;
        }
        party[index] = inputChar;
        saveParty();
    }

    public static void swapIndex(int index1, int index2){
        if(index1 == index2){
            return;
        }
        WifeyCharacter temp = party[index2];
        party[index2] = party[index1];
        party[index1] = temp;
        saveParty();
    }

    public static WifeyCharacter getIndex(int index){
        return party[index];
    }

    public static int getWifeyIndex(WifeyCharacter input){
        for(int i = 0; i < 7 && party[i] != null; i++){
            if(party[i] == input){
                return i;
            }
        }
        return -1;
    }

    public static void removeIndex(int index){
        if(index < 0 || index >= 7){
            return;
        }
        for(int i = index; i + 1 < 7; i++){
            party[i] = party[i+1];
        }
        party[6] = null;
        saveParty();
    }

    public static void clearParty(){
        for(int i = 0; i < party.length; i++){
            party[i] = null;
        }
        saveParty();
    }

    public static int partySize(){
        int size = 0;
        while(size < 7 && party[size] != null){
            size++;
        }
        return size;
    }

    public static WifeyCharacter[] getParty(){
        return party;
    }

    public static BattleWifey[] getBattleParty(){
        BattleWifey[] battleParty = new BattleWifey[partySize()];
        for(int i = 0; i < battleParty.length; i++){
            battleParty[i] = party[i].getBattleCharacter();
        }
        return battleParty;
    }

    private static void saveParty(){
        SharedPreferences.Editor editor = prefs.edit();
        for(int i = 0; i < 7; i++){
            if(party[i] == null){
                editor.putString("party_" + i, "");
            }
            else{
                editor.putString("party_" + i, party[i].getHashKey());
            }
        }
        editor.commit();
    }
}
