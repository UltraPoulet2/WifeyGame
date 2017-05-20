package ultrapoulet.wifeygame.screens.dialogs;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;
import ultrapoulet.wifeygame.recruiting.RecruitRequirementGold;

/**
 * Created by John on 5/19/2017.
 */

public class RecruitGoldDialog extends AbsYesNoDialog {

    private RecruitRequirementGold goldReq;

    public RecruitGoldDialog(Game game, Screen prevScreen, RecruitRequirementGold req){
        super(game, prevScreen);
        this.goldReq = req;
        this.text = "Do you want to spend " + req.getGoldAmount() + " gold?";
    }

    @Override
    public void yesButtonAction() {
        PlayerInfo.payGold(goldReq.getGoldAmount());
        goldReq.complete();
        backButton();
    }

    @Override
    public void noButtonAction() {
        backButton();
    }
}
