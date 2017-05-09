package ultrapoulet.wifeygame.recruiting;

import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 5/7/2017.
 */

public class RecruitRequirementWifey extends RecruitRequirement {

    private WifeyCharacter requiredWifey;

    public void setRequiredWifey(WifeyCharacter input){
        this.requiredWifey = input;
    }

    public WifeyCharacter getRequiredWifey(){
        return requiredWifey;
    }

    public String getDescription(){
        return "Recruit " + requiredWifey.getName() +".";
    }

    @Override
    public boolean isComplete() {
        return requiredWifey.isRecruited();
    }

    public boolean validate(){
        return requiredWifey != null;
    }
}
