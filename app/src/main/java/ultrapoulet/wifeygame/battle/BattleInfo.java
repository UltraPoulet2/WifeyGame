package ultrapoulet.wifeygame.battle;

import java.util.ArrayList;

/**
 * Created by John on 6/19/2016.
 */
public class BattleInfo {

    private String battleName;
    private ArrayList<BattleEnemy> battleEnemyList = new ArrayList<>();
    //private ArrayList<Restriction> restrictionList = new ArrayList<>();

    public void setName(String name){
        battleName = name;
    }

    public String getName(){
        return battleName;
    }

    public void addEnemy(BattleEnemy battleEnemy){
        battleEnemyList.add(battleEnemy);
    }

    public BattleEnemy getEnemy(int index){
        if(index >= 0 && index < battleEnemyList.size()){
            return battleEnemyList.get(index);
        }
        else{
            return null;
        }
    }

    public BattleEnemy[] getEnemies(){
        BattleEnemy[] temp = new BattleEnemy[battleEnemyList.size()];
        battleEnemyList.toArray(temp);
        return temp;
    }

    //Future things
    /*
    public void addRestriction(Restriction r) {
        restrictionList.add(r);
    }

    public boolean allowCharacter(WifeyCharacter c){
        for(int i = 0; i < restrictionList.size(); i++){
            if(!restrictionList.isAllowed(c)){
                return false;
            }
        }
        return true;
    }

    public boolean validParty(WifeyCharacter[] party){
        for(int i = 0; i < party.length && party[i] != null; i++){
            if(allowCharacter(party[i]) == false){
                return false;
            }
        }
        return true;
    }
    */

}
