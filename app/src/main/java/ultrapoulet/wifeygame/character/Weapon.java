package ultrapoulet.wifeygame.character;

import android.util.Log;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.helpers.AnimationImages;
import ultrapoulet.wifeygame.AnimationAssets;

/**
 * Created by John on 6/25/2016.
 */
public enum Weapon {
    AIRSOFT("Airsoft", 6),
    ANIMAL("Animal", 5),
    BALL("Ball", 3),
    BOOK("Book", 2),
    CARD("Card", 3),
    CLAW("Claw", 3),
    CLUB("Club", 2),
    COOKING("Cooking", 3),
    COMPUTER("Computer", 4),
    FAN("Fan", 3),
    FISTS("Fists", 3),
    FOOD("Food", 2),
    GUN("Gun", 6),
    INSTRUMENT("Instrument", 6),
    KNIFE("Knife", 3),
    LASER("Laser", 6),
    MAGIC("Magic", 4),
    MECHA("Mecha", 5),
    MEDICINE("Medicine", 2),
    PLANT("Plant", 3),
    PLUSH("Plush", 5),
    SCYTHE("Scythe", 2),
    SPORT("Sport", 6),
    SWORD("Sword", 5),
    //TAIL("Tail", 3),
    TANK("Tank", 2),
    TOOL("Tool", 4),
    TOY("Toy", 3),
    VEHICLE("Vehicle", 6);

    private String weaponType;
    private int numHits;
    private Image image;

    Weapon(String weaponType, int numHits){
        this.weaponType = weaponType;
        this.numHits = numHits;
    }

    Weapon(String weaponType, int numHits, AnimationImages animationImages){
        this.weaponType = weaponType;
        this.numHits = numHits;
    }

    public String getWeaponType(){
        return this.weaponType;
    }

    public int getNumHits(){
        return this.numHits;
    }

    public Image getImage(){
        return this.image;
    }

    public AnimationImages getBattleAnimation() {
        switch(this){
            case AIRSOFT:
                return AnimationAssets.TestAnimation;
            case ANIMAL:
                return AnimationAssets.TestAnimation;
            case BALL:
                return AnimationAssets.TestAnimation;
            case BOOK:
                return AnimationAssets.TestAnimation;
            case CARD:
                return AnimationAssets.TestAnimation;
            case CLAW:
                return AnimationAssets.TestAnimation;
            case CLUB:
                return AnimationAssets.TestAnimation;
            case COOKING:
                return AnimationAssets.TestAnimation;
            case COMPUTER:
                return AnimationAssets.TestAnimation;
            case FAN:
                return AnimationAssets.TestAnimation;
            case FISTS:
                return AnimationAssets.TestAnimation;
            case FOOD:
                return AnimationAssets.TestAnimation;
            case GUN:
                return AnimationAssets.TestAnimation;
            case INSTRUMENT:
                return AnimationAssets.InstrumentAnimation;
            case KNIFE:
                return AnimationAssets.TestAnimation;
            case LASER:
                return AnimationAssets.LaserAnimation;
            case MAGIC:
                return AnimationAssets.TestAnimation;
            case MECHA:
                return AnimationAssets.MechaAnimation;
            case MEDICINE:
                return AnimationAssets.TestAnimation;
            case PLANT:
                return AnimationAssets.TestAnimation;
            case PLUSH:
                return AnimationAssets.TestAnimation;
            case SCYTHE:
                return AnimationAssets.ScytheAnimation;
            case SPORT:
                return AnimationAssets.TestAnimation;
            case SWORD:
                return AnimationAssets.TestAnimation;
            case TANK:
                return AnimationAssets.TestAnimation;
            case TOOL:
                return AnimationAssets.TestAnimation;
            case TOY:
                return AnimationAssets.TestAnimation;
            case VEHICLE:
                return AnimationAssets.TestAnimation;
            default:
                Log.e("Weapon", "Missing animation for: " + this.getWeaponType());
                return null;
        }
    }

    public static void setupImages(Graphics g){
        for(Weapon weapon : Weapon.values()) {
            weapon.image = g.newImage("Weapons/" + weapon.toString() + ".png", ImageFormat.ARGB8888);
        }
    }
}
