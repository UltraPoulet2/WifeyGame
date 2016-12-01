package ultrapoulet.wifeygame.character;

import java.util.HashMap;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 9/6/2016.
 */
public class Element {

    private String elementType;
    private Image elementImage;

    private Element(String elem, Image image){
        this.elementType = elem;
        this.elementImage = image;
    }

    public String getElementType(){
        return elementType;
    }

    public Image getElementImage(){
        return elementImage;
    }

    private static HashMap<String, Element> elementList;

    public static Element getElement(String key){
        if(elementList == null){
            createElementList();
        }
        return elementList.get(key);
    }

    private static void createElementList(){
        elementList = new HashMap();
        elementList.put("AIR", new Element("Air", Assets.ElementImages.get(Assets.AIR)));
        elementList.put("DARK", new Element("Dark", Assets.ElementImages.get(Assets.DARK)));
        elementList.put("FIRE", new Element("Fire", Assets.ElementImages.get(Assets.FIRE)));
        elementList.put("EARTH", new Element("Earth", Assets.ElementImages.get(Assets.EARTH)));
        elementList.put("LIGHT", new Element("Light", Assets.ElementImages.get(Assets.LIGHT)));
        elementList.put("WATER", new Element("Water", Assets.ElementImages.get(Assets.WATER)));
    }
}
