package ultrapoulet.wifeygame;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 5/5/2016.
 */
public class WifeyCharacter {

    private String name;
    private String hashKey;
    private int strength;
    private int magic;

    private int experience;
    private int level;

    //Variable for weaponType

    private Image image;

    public WifeyCharacter(String hashKey, String name, int strength, int magic, Image image){
        this.hashKey = hashKey;
        this.name = name;
        this.strength = strength;
        this.magic = magic;
        this.image = image;
    }

    public BattleCharacter getBattleCharacter(){
        return new BattleCharacter(name, 3, strength, magic, image);
    }

    public String getName(){
        return this.name;
    }

    public String getHashKey(){
        return this.hashKey;
    }

    public Image getImage(){
        return this.image;
    }

    public int getHP(){
        return BattleCharacter.calculateHP(this.strength);
    }

    public int getStrength(){
        return this.strength;
    }

    public int getMagic(){
        return this.magic;
    }

    public int compareName(WifeyCharacter other){
        int result = this.getName().compareTo(other.getName());
        if(result != 0){
            return result;
        }
        else{
            return this.getHashKey().compareTo(other.getHashKey());
        }
    }
}
