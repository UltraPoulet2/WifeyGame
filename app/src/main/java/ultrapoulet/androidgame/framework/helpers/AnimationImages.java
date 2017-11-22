package ultrapoulet.androidgame.framework.helpers;

import java.util.ArrayList;

import ultrapoulet.androidgame.framework.Image;

/**
 * Created by John on 9/3/2017.
 */

public class AnimationImages {

    private ArrayList<Image> frames;

    public AnimationImages(){
        frames = new ArrayList<>();
    }

    public void addFrame(Image image){
        frames.add(image);
    }

    public Image getFrame(int i){
        return frames.get(i);
    }

    public int getNumFrames(){
        return frames.size();
    }
}
