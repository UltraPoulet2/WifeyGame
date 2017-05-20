package ultrapoulet.wifeygame.recruiting;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;
import ultrapoulet.wifeygame.screens.dialogs.InfoDialog;
import ultrapoulet.wifeygame.screens.dialogs.RecruitGoldDialog;

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

    public Screen getScreen(Game game, Screen prevScreen){
        if(this.isComplete()){
            return null;
        }
        else if(PlayerInfo.getGold() < this.goldAmount){
            return new InfoDialog(game, prevScreen, "You do not have enough gold.");
        }
        else {
            return new RecruitGoldDialog(game, prevScreen, this);
        }
    }
}
