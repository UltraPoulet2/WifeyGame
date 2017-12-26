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

    //private static ArrayList<WifeyCharacter> party = new ArrayList<>(Collections.nCopies(7, (WifeyCharacter) null));

    private static SharedPreferences prefs;

    public static void init(SharedPreferences inPrefs){
        prefs = inPrefs;
    }

    //@Deprecated
    /*
    public static void setParty(List<WifeyCharacter> input){
        for(int i = 0; i < input.size(); i++){
            party.set(i, input.get(i));
        }
        for(int i = input.size(); i < 7; i++){
            party.set(i, null);
        }
        saveParty();
    }
    */

    public static void setParty(int index, List<WifeyCharacter> input) {
        //Fill in tomorrow
        List<WifeyCharacter> newParty = new ArrayList<>();
        for(int i = 0; i < input.size(); i++) {
            newParty.add(input.get(i));
        }
        for(int i = input.size(); i < 7; i++) {
            newParty.add(null);
        }
        parties.set(index, newParty);
        saveParty(index);
    }

    /*
    public static boolean inParty(WifeyCharacter inputChar){
        //Returns true if in party, false otherwise
        return party.contains(inputChar);
    }
    */

    /*
    public static void setIndex(int index, WifeyCharacter inputChar){
        if(inParty(inputChar) || index < 0 || index >= 7){
            return;
        }
        party.set(index, inputChar);
        saveParty();
    }
    */

    /*
    public static void swapIndex(int index1, int index2){
        if(index1 == index2){
            return;
        }
        WifeyCharacter temp = party.get(index2);
        party.set(index2, party.get(index1));
        party.set(index1, temp);
        saveParty();
    }
    */

    /*public static WifeyCharacter getIndex(int index){
        return party.get(index);
    }*/

    //Get the character at index of Party[party]
    public static WifeyCharacter getIndex(int party, int index) {
        return parties.get(party).get(index);
    }

    /*
    public static int getWifeyIndex(WifeyCharacter input){
        return party.indexOf(input);
    }
    */

    /*
    public static void removeIndex(int index){
        party.remove(index);
        party.add(null);
        saveParty();
    }
    */

    /*
    public static void clearParty(){
        for(int i = 0; i < party.size(); i++){
            party.set(i, null);
        }
        saveParty();
    }
    */

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

    @Deprecated
    public static List<WifeyCharacter> getCurrentParty(int size){
        return getParty(currentPartyNum, size);
        /*
        List<WifeyCharacter> temp = new ArrayList<>();
        int end = size > getCurrentPartySize() ? getCurrentPartySize() : size;
        for(int i = 0; i < end; i++){
            temp.add(parties.get(currentPartyNum).get(i));
        }
        for(int i = end; i < 7; i++){
            temp.add(null);
        }
        return temp;
        */
    }

    @Deprecated
    public static List<WifeyCharacter> getParty(int num, int size) {
        List<WifeyCharacter> temp = new ArrayList<>();
        List<WifeyCharacter> party = parties.get(num);
        int end = size > getCurrentPartySize() ? getCurrentPartySize() : size;
        for(int i = 0; i < end; i++){
            temp.add(party.get(i));
        }
        for(int i = end; i < 7; i++){
            temp.add(null);
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

    @Deprecated
    private static void saveParty(){
        /*
        String partyString = "";
        SharedPreferences.Editor editor = prefs.edit();
        for(int i = 0; i < 7; i++){
            if(i > 0) {
                partyString += ",";
            }
            if(party.get(i) == null){
                partyString += "";
            }
            else{
                partyString += party.get(i).getHashKey();
            }
        }
        editor.putString("party_0", partyString);
        editor.apply();
        */
    }

    private static void saveParty(int partyNum) {
        //Fill later
        String partyString = "";
        SharedPreferences.Editor editor = prefs.edit();
        for(int i = 0; i < 7; i++){
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

    //For debugging purposes
    /*
    public static String getString(){
        StringBuilder val = new StringBuilder();
        val.append("Party of size: " + getCurrentPartySize() + "\n");
        for(int i = 0; i < getCurrentPartySize(); i++){
            val.append("Party member" + i + ":"  + party.get(i).getName() + "\n");
        }
        return val.toString();
    }
    */
}
