package ultrapoulet.wifeygame.screens.dialogs;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 5/18/2017.
 */

public class InfoDialog extends AbsDialog {

    private Button okButton;
    private static final int OK_BUTTON_LEFT_X = BACKGROUND_X + 125;
    private static final int OK_BUTTON_RIGHT_X = OK_BUTTON_LEFT_X + 150;
    private static final int OK_BUTTON_TOP_Y = BACKGROUND_Y + 140;
    private static final int OK_BUTTON_BOT_Y = OK_BUTTON_TOP_Y + 50;

    protected Button lastPressed;

    public InfoDialog(Game game, Screen prevScreen, String text){
        super(game, prevScreen);
        this.text = text;

        okButton = new Button(OK_BUTTON_LEFT_X, OK_BUTTON_RIGHT_X, OK_BUTTON_TOP_Y, OK_BUTTON_BOT_Y, true, "OK", Assets.OptionOk);
        buttonList.addButton(okButton);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if (t.type == Input.TouchEvent.TOUCH_DOWN) {
                lastPressed = buttonList.getButtonPressed(t.x, t.y);
                continue;
            } else if (t.type == Input.TouchEvent.TOUCH_UP) {
                if(lastPressed == buttonList.getButtonPressed(t.x, t.y) && lastPressed != null){
                    if(lastPressed == okButton){
                        backButton();
                        break;
                    }
                }
            }
        }
    }
}
