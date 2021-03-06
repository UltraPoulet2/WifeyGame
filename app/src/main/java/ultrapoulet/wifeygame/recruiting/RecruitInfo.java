package ultrapoulet.wifeygame.recruiting;

import java.util.ArrayList;

/**
 * Created by John on 4/26/2017.
 */

public class RecruitInfo {

    private String quote = "";
    private ArrayList<RecruitRequirement> requirements = new ArrayList<>();

    public void addRequirement(RecruitRequirement inRequirement){
        requirements.add(inRequirement);
    }

    public ArrayList<RecruitRequirement> getRequirements(){
        return requirements;
    }

    public String getQuote() {
        return this.quote;
    }

    public void setQuote(String input){
        this.quote = input;
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
