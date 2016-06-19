package ultrapoulet.wifeygame.battle;

import java.util.ArrayList;

import ultrapoulet.wifeygame.WifeyCharacter;

/**
 * Created by John on 6/19/2016.
 */
public class BattleInfo {

    private String battleName;
    private ArrayList<Enemy> enemyList = new ArrayList<>();
    //private ArrayList<Restriction> restrictionList = new ArrayList<>();

    public void setName(String name){
        battleName = name;
    }

    public String getName(){
        return battleName;
    }

    public void addEnemy(Enemy enemy){
        enemyList.add(enemy);
    }

    public Enemy getEnemy(int index){
        if(index >= 0 && index < enemyList.size()){
            return enemyList.get(index);
        }
        else{
            return null;
        }
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
