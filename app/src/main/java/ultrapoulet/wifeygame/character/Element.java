package ultrapoulet.wifeygame.character;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 9/6/2016.
 */
public enum Element {
    AIR("Air", Assets.ElementImages.get(Assets.AIR)),
    DARK("Dark", Assets.ElementImages.get(Assets.DARK)),
    EARTH("Earth", Assets.ElementImages.get(Assets.EARTH)),
    FIRE("Fire", Assets.ElementImages.get(Assets.FIRE)),
    LIGHT("Light", Assets.ElementImages.get(Assets.LIGHT)),
    WATER("Water", Assets.ElementImages.get(Assets.WATER));

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
}
