package ultrapoulet.wifeygame.recruiting;

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
}
