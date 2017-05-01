package ultrapoulet.wifeygame.recruiting;

import java.util.ArrayList;

import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 4/26/2017.
 */

public class RecruitInfo {

    private WifeyCharacter recruit;
    private ArrayList<RecruitRequirement> requirements = new ArrayList<>();

    public void setRecruit(WifeyCharacter inRecruit){
        this.recruit = inRecruit;
    }

    public WifeyCharacter getRecruit(){
        return recruit;
    }

    public void addRequirement(RecruitRequirement inRequirement){
        requirements.add(inRequirement);
    }

    public ArrayList<RecruitRequirement> getRequirements(){
        return requirements;
    }

    public boolean isRecruitable(){
        for(int i = 0; i < requirements.size(); i++){
            if(!requirements.get(i).isComplete()){
                return false;
            }
        }
        return true;
    }
}
