package ultrapoulet.wifeygame.character;

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

    private Weapon weapon;

    private Image image;


    //We're getting rid of this call
    /*
    public WifeyCharacter(String hashKey, String name, int strength, int magic, Image image){
        this.hashKey = hashKey;
        this.name = name;
        this.strength = strength;
        this.magic = magic;
        this.image = image;
    }
    */

    public BattleCharacter getBattleCharacter(){
        return new BattleCharacter(name, weapon, strength, magic, image);
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

    public Weapon getWeapon() { return this.weapon; }

    public void setHashKey(String hashKey){
        this.hashKey = hashKey;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public void setMagic(int magic){
        this.magic = magic;
    }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

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
