package ultrapoulet.wifeygame.character;

import java.util.HashMap;

/**
 * Created by John on 9/6/2016.
 */
public class Element {

    private String elementType;

    private Element(String elem){
        this.elementType = elem;
    }

    public String getElementType(){
        return elementType;
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
        elementList.put("FIRE", new Element("Fire"));
        elementList.put("WATER", new Element("Water"));
        elementList.put("WIND", new Element("Wind"));
        elementList.put("EARTH", new Element("Earth"));
        elementList.put("LIGHT", new Element("Light"));
        elementList.put("DARK", new Element("Dark"));
    }
}
