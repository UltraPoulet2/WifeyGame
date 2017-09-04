package ultrapoulet.androidgame.framework.helpers;

import ultrapoulet.androidgame.framework.Image;

/**
 * Created by John on 9/3/2017.
 */

public class Animation {

    private AnimationImages frames;
    private long currentDuration;
    private long totalDuration;
    private boolean repeat;

    public Animation(AnimationImages inputFrames, long duration, boolean repeat){
        this.frames = inputFrames;
        this.totalDuration = duration;
        this.repeat = repeat;
    }

    public void update(long deltaTime){
        this.currentDuration += deltaTime;
        if(currentDuration >= totalDuration){
            if(repeat){
                currentDuration -= totalDuration;
            }
            else {
                //Not the prettiest way of doing it, but this will lock it to the final frame
                currentDuration = totalDuration - 1;
            }
        }
    }

    public Image getFrame(){
        int numFrames = frames.getNumFrames();
        int timePerFrame = (int) totalDuration / numFrames;
        int currentFrame = (int) currentDuration / timePerFrame;
        return frames.getFrame(currentFrame);
    }
}
