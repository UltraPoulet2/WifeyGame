package ultrapoulet.wifeygame.character;

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
    TAIL("Tail", 3),
    TANK("Tank", 2),
    TOOL("Tool", 4),
    TOY("Toy", 3),
    VEHICLE("Vehicle", 6);

    private String weaponType;
    private int numHits;

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
}
