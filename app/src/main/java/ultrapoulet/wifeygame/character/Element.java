package ultrapoulet.wifeygame.character;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.helpers.AnimationImages;
import ultrapoulet.wifeygame.AnimationAssets;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 9/6/2016.
 */
public enum Element {
    AIR("Air", Assets.ElementImages.get(Assets.AIR)) {
        @Override
        public AnimationImages getBattleAnimation() {
            return AnimationAssets.WindAnimation;
        }
    },
    DARK("Dark", Assets.ElementImages.get(Assets.DARK)) {
        @Override
        public AnimationImages getBattleAnimation() {
            return AnimationAssets.DarkAnimation;
        }
    },
    EARTH("Earth", Assets.ElementImages.get(Assets.EARTH)) {
        @Override
        public AnimationImages getBattleAnimation() {
            return AnimationAssets.EarthAnimation;
        }
    },
    FIRE("Fire", Assets.ElementImages.get(Assets.FIRE)) {
        @Override
        public AnimationImages getBattleAnimation() {
            return AnimationAssets.FireAnimation;
        }
    },
    LIGHT("Light", Assets.ElementImages.get(Assets.LIGHT)) {
        @Override
        public AnimationImages getBattleAnimation() {
            return AnimationAssets.LightAnimation;
        }
    },
    WATER("Water", Assets.ElementImages.get(Assets.WATER)) {
        @Override
        public AnimationImages getBattleAnimation() {
            return AnimationAssets.WaterAnimation;
        }
    };

    private String elementType;
    private Image elementImage;

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

    public abstract AnimationImages getBattleAnimation();
}
