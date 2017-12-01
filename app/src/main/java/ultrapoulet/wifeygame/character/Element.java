package ultrapoulet.wifeygame.character;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.helpers.AnimationImages;
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
    private AnimationImages animation;

    Element(String elem, Image image){
        this.elementType = elem;
        this.elementImage = image;
    }

    public String getElementType(){
        return elementType;
    }

    public Image getElementImage(){
        return elementImage;
    }

    public void loadAnimation(Graphics g){
        if(this.animation == null) {
            animation = new AnimationImages();
            for(int i = 0; i < 10; i++){
                animation.addFrame(g.newImage("BattleAnimations/" + this.name() + "/" + this.name() + i + ".png", Graphics.ImageFormat.ARGB8888));
            }
        }
    }

    public void unloadAnimation() {
        this.animation = null;
    }

    public static void unloadAllAnimations() {
        for(Element element : Element.values()){
            element.unloadAnimation();
        }
    }

    public AnimationImages getBattleAnimation() {
        return animation;
    }
}
