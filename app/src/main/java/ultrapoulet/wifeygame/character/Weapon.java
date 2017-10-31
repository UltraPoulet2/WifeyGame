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
    HAMMER("Hammer", 2),
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
    private AnimationImages animation;

    Weapon(String weaponType, int numHits){
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

    public void loadAnimation(Graphics g){
        if(this.animation == null) {
            animation = new AnimationImages();
            for(int i = 0; i < 10; i++){
                animation.addFrame(g.newImage("BattleAnimations/" + this.name() + "/" + this.name() + i + ".png", ImageFormat.ARGB8888));
            }
        }
    }

    public void unloadAnimation() {
        this.animation = null;
    }

    public static void unloadAllAnimations() {
        for(Weapon weapon : Weapon.values()) {
            weapon.unloadAnimation();
        }
    }

    public AnimationImages getBattleAnimation() {
        return animation;
    }

    public static void setupImages(Graphics g){
        for(Weapon weapon : Weapon.values()) {
            if(weapon == Weapon.HAMMER) {
                //Hammer not completely prepared
                weapon.image = CLUB.getImage();
            }
            else {
                weapon.image = g.newImage("Weapons/" + weapon.toString() + ".png", ImageFormat.ARGB8888);
            }
        }
    }
}
