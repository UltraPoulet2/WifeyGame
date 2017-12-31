package ultrapoulet.wifeygame.gamestate;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 5/13/2016.
 */
public class Party {

    private static int currentPartyNum;

    public static final int MAX_PARTIES = 9;
    public static final int MAX_PARTY_SIZE = 7;

    private static List<List<WifeyCharacter>> parties = new ArrayList<>(Collections.nCopies(MAX_PARTIES, (List<WifeyCharacter>) null));

    private static SharedPreferences prefs;

    public static void init(SharedPreferences inPrefs){
        prefs = inPrefs;
    }

    public static void setParty(int index, List<WifeyCharacter> input) {
        List<WifeyCharacter> newParty = new ArrayList<>();
        for(int i = 0; i < input.size(); i++) {
            newParty.add(input.get(i));
        }
        for(int i = input.size(); i < MAX_PARTY_SIZE; i++) {
            newParty.add(null);
        }
        parties.set(index, newParty);
        saveParty(index);
    }

    //Get the character at index of Party[party]
    public static WifeyCharacter getIndex(int party, int index) {
        return parties.get(party).get(index);
    }

    public static int getCurrentPartySize(){
        return getPartySize(currentPartyNum);
    }

    public static int getPartySize(int index) {
        int size = 0;
        while(size < 7 && parties.get(index).get(size) != null) {
            size++;
        }
        return size;
    }

    public static List<WifeyCharacter> getCurrentParty() {
        return getParty(currentPartyNum);
    }

    public static List<WifeyCharacter> getParty(int index) {
        List<WifeyCharacter> temp = new ArrayList<>();
        List<WifeyCharacter> party = parties.get(index);
        for(int i = 0; i < MAX_PARTY_SIZE; i++){
            temp.add(party.get(i));
        }
        return temp;
    }
    public static List<BattleCharacter> getCurrentBattleParty(int size, Graphics g){
        List<BattleCharacter> battleParty = new ArrayList<>();
        for(int i = 0; i < getCurrentPartySize() && i < size; i++){
            battleParty.add(parties.get(currentPartyNum).get(i).getBattleCharacter(g));
        }
        return battleParty;
    }

    private static void saveParty(int partyNum) {
        String partyString = "";
        SharedPreferences.Editor editor = prefs.edit();
        for(int i = 0; i < MAX_PARTY_SIZE; i++){
            if(i > 0) {
                partyString += ",";
            }
            if(parties.get(partyNum).get(i) == null){
                partyString += "";
            }
            else{
                partyString += parties.get(partyNum).get(i).getHashKey();
            }
        }
        editor.putString("party_" + partyNum, partyString);
        editor.apply();
    }

    public static void setActivePartyNumber(int num) {
        currentPartyNum = num;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("currentParty", currentPartyNum);
        editor.apply();
    }

    public static int getActivePartyNumber() {
        return currentPartyNum;
    }
}
