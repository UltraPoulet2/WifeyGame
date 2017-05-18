package ultrapoulet.wifeygame.screens.dialogs;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 5/17/2017.
 */

//Abstract commented out temporaily for testing
public /*abstract*/ class AbsYesNoDialog extends AbsDialog {

    protected Button yesButton;
    protected Button noButton;

    private static final int YES_BUTTON_LEFT_X = BACKGROUND_X + 10;
    private static final int YES_BUTTON_RIGHT_X = YES_BUTTON_LEFT_X + 150;
    private static final int NO_BUTTON_LEFT_X = BACKGROUND_X + 240;
    private static final int NO_BUTTON_RIGHT_X = NO_BUTTON_LEFT_X + 150;
    private static final int BUTTON_TOP_Y = BACKGROUND_Y + 140;
    private static final int BUTTON_BOT_Y = BUTTON_TOP_Y + 50;

    protected Button lastPressed;

    public AbsYesNoDialog(Game game, Screen prevScreen){
        super(game, prevScreen);

        yesButton = new Button(YES_BUTTON_LEFT_X, YES_BUTTON_RIGHT_X, BUTTON_TOP_Y, BUTTON_BOT_Y, true, "Yes", Assets.OptionYes);
        noButton = new Button(NO_BUTTON_LEFT_X, NO_BUTTON_RIGHT_X, BUTTON_TOP_Y, BUTTON_BOT_Y, true, "No", Assets.OptionNo);
        buttonList.addButton(yesButton);
        buttonList.addButton(noButton);
    }

    public /* abstract */ void yesButtonAction(){ backButton(); };
    public /* abstract */ void noButtonAction() { backButton(); };

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
                    if(lastPressed == yesButton){
                        yesButtonAction();
                        break;
                    }
                    else if(lastPressed == noButton){
                        noButtonAction();
                        break;
                    }
                }
            }
        }
    }
}
