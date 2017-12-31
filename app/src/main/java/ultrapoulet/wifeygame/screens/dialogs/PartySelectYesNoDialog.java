package ultrapoulet.wifeygame.screens.dialogs;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;

/**
 * Created by John on 12/20/2017.
 */

public class PartySelectYesNoDialog extends AbsYesNoDialog {

    //The screen to transition to if Yes is selected
    private Screen leaveScreen;
    //If No is selected, we will go back to prevScreen

    public PartySelectYesNoDialog(Game game, Screen prevScreen, Screen leaveScreen) {
        super(game, prevScreen);
        this.leaveScreen = leaveScreen;
        this.text = "There are unsaved changes. Are you sure you want to leave?";
    }

    @Override
    public void yesButtonAction() {
        game.setScreen(leaveScreen);
    }

    @Override
    public void noButtonAction() {
        game.setScreen(previousScreen);
    }
}
