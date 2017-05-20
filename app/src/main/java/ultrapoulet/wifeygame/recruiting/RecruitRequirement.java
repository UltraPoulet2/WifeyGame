package ultrapoulet.wifeygame.recruiting;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;

/**
 * Created by John on 4/26/2017.
 */

public abstract class RecruitRequirement {

    private boolean complete = false;

    public abstract String getDescription();

    public void complete(){
        complete = true;
    }

    public boolean isComplete(){
        return complete;
    }

    public abstract boolean validate();

    //Most requirements will return nothing
    public Screen getScreen(Game game, Screen prevScreen){
        return null;
    }
}
