package ultrapoulet.wifeygame.character;

import java.util.HashMap;

/**
 * Created by John on 6/25/2016.
 */
public class Weapon {

    private String weaponType;
    private int numHits;

    private Weapon(String weaponType, int numHits){
        this.weaponType = weaponType;
        this.numHits = numHits;
    }

    public String getWeaponType(){
        return this.weaponType;
    }

    public int getNumHits(){
        return this.numHits;
    }

    private static HashMap<String, Weapon> weaponList;

    public static Weapon getWeapon(String key){
        if(weaponList == null){
            createWeaponList();
        }
        return weaponList.get(key);
    }

    private static void createWeaponList(){
        weaponList = new HashMap<>();
        weaponList.put("AIRSOFT", new Weapon("Airsoft", 6));
        weaponList.put("ANIMAL", new Weapon("Animal", 5));
        weaponList.put("BALL", new Weapon("Ball", 3));
        weaponList.put("BOOK", new Weapon("Book", 2));
        weaponList.put("CARD", new Weapon("Card", 3));
        weaponList.put("CLAW", new Weapon("Claw", 3));
        weaponList.put("CLUB", new Weapon("Club", 2));
        weaponList.put("COOKING", new Weapon("Cooking", 3));
        weaponList.put("COMPUTER", new Weapon("Computer", 4));
        weaponList.put("FAN", new Weapon("Fan", 3));
        weaponList.put("FISTS", new Weapon("Fists", 3));
        weaponList.put("FOOD", new Weapon("Food", 2));
        weaponList.put("GUN", new Weapon("Gun", 6));
        weaponList.put("INSTRUMENT", new Weapon("Instrument", 6));
        weaponList.put("KNIFE", new Weapon("Knife", 3));
        weaponList.put("LASER", new Weapon("Laser", 6));
        weaponList.put("MAGIC", new Weapon("Magic", 4));
        weaponList.put("MECHA", new Weapon("Mecha", 5));
        weaponList.put("MEDICINE", new Weapon("Medicine", 2));
        weaponList.put("PLANT", new Weapon("Plant", 3));
        weaponList.put("PLUSH", new Weapon("Plush", 5));
        weaponList.put("SCYTHE", new Weapon("Scythe", 2));
        weaponList.put("SPORT", new Weapon("Sport", 6));
        weaponList.put("SWORD", new Weapon("Sword", 5));
        weaponList.put("TAIL", new Weapon("Tail", 3));
        weaponList.put("TANK", new Weapon("Tank", 2));
        weaponList.put("TOOL", new Weapon("Tool", 4));
        weaponList.put("TOY", new Weapon("Toy", 3));
        weaponList.put("VEHICLE", new Weapon("Vehicle", 6));
    }


}
