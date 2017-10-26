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
                return AnimationAssets.AirsoftAnimation;
            case ANIMAL:
                return AnimationAssets.AnimalAnimation;
            case BALL:
                return AnimationAssets.BallAnimation;
            case BOOK:
                return AnimationAssets.BookAnimation;
            case CARD:
                return AnimationAssets.CardAnimation;
            case CLAW:
                return AnimationAssets.ClawAnimation;
            case CLUB:
                return AnimationAssets.TestAnimation;
            case COOKING:
                return AnimationAssets.CookingAnimation;
            case COMPUTER:
                return AnimationAssets.ComputerAnimation;
            case FAN:
                return AnimationAssets.FanAnimation;
            case FISTS:
                return AnimationAssets.TestAnimation;
            case FOOD:
                return AnimationAssets.FoodAnimation;
            case GUN:
                return AnimationAssets.GunAnimation;
            case HAMMER:
                return AnimationAssets.HammerAnimation;
            case INSTRUMENT:
                return AnimationAssets.InstrumentAnimation;
            case KNIFE:
                return AnimationAssets.KnifeAnimation;
            case LASER:
                return AnimationAssets.LaserAnimation;
            case MAGIC:
                return AnimationAssets.MagicAnimation;
            case MECHA:
                return AnimationAssets.MechaAnimation;
            case MEDICINE:
                return AnimationAssets.MedicineAnimation;
            case PLANT:
                return AnimationAssets.PlantAnimation;
            case PLUSH:
                return AnimationAssets.PlushAnimation;
            case SCYTHE:
                return AnimationAssets.ScytheAnimation;
            case SPORT:
                return AnimationAssets.SportAnimation;
            case SWORD:
                return AnimationAssets.SwordAnimation;
            case TANK:
                return AnimationAssets.TankAnimation;
            case TOOL:
                return AnimationAssets.ToolAnimation;
            case TOY:
                return AnimationAssets.ToyAnimation;
            case VEHICLE:
                return AnimationAssets.VehicleAnimation;
            default:
                Log.e("Weapon", "Missing animation for: " + this.getWeaponType());
                return null;
        }
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
