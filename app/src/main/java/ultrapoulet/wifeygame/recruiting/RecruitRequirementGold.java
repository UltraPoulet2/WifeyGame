package ultrapoulet.wifeygame.recruiting;

/**
 * Created by John on 5/7/2017.
 */

public class RecruitRequirementGold extends RecruitRequirement {

    private int goldAmount;

    public void setGoldAmount(int inputGold){
        this.goldAmount = inputGold;
    }

    public int getGoldAmount(){
        return goldAmount;
    }

    public String getDescription(){
        return "Pay " + goldAmount + " gold";
    }

    public boolean validate(){
        return goldAmount > 0;
    }
}
