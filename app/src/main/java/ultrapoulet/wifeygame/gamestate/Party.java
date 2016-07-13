package ultrapoulet.wifeygame.gamestate;

import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.battle.BattleWifey;

/**
 * Created by John on 5/13/2016.
 */
public class Party {

    private static WifeyCharacter[] party = new WifeyCharacter[7];

    public static void setParty(WifeyCharacter[] input){
        for(int i = 0; i < input.length; i++){
            party[i] = input[i];
        }
        for(int i = input.length; i < 7; i++){
            party[i] = null;
        }
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
    }

    public static void swapIndex(int index1, int index2){
        if(index1 == index2){
            return;
        }
        WifeyCharacter temp = party[index2];
        party[index2] = party[index1];
        party[index1] = temp;
    }

    public static WifeyCharacter getIndex(int index){
        return party[index];
    }

    public static void removeIndex(int index){
        if(index < 0 || index >= 7){
            return;
        }
        for(int i = index; i + 1 < 7; i++){
            party[i] = party[i+1];
        }
        party[6] = null;
    }

    public static void clearParty(){
        for(int i = 0; i < party.length; i++){
            party[i] = null;
        }
    }

    public static int partySize(){
        int size = 0;
        while(size < 7 && party[size] != null){
            size++;
        }
        return size;
    }

    public static BattleWifey[] getBattleParty(){
        BattleWifey[] battleParty = new BattleWifey[partySize()];
        for(int i = 0; i < battleParty.length; i++){
            battleParty[i] = party[i].getBattleCharacter();
        }
        return battleParty;
    }
}
