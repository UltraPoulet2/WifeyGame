package ultrapoulet.wifeygame.screens.dialogs;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 6/18/2017.
 */

public abstract class AbsAdjustableInfoDialog extends AbsAdjustableDialog {

    protected ButtonList buttonList;
    protected Button okButton;

    protected Button lastPressed;

    private static final int OK_BUTTON_LEFT_X = 400 - (Assets.OptionOk.getWidth() / 2);
    private static final int OK_BUTTON_RIGHT_X = OK_BUTTON_LEFT_X + 150;
    private static final int OK_BUTTON_Y_OFFSET = 14;
    private int okTopY;
    private int okBotY;

    public AbsAdjustableInfoDialog(Game game, Screen prevScreen) {
        super(game, prevScreen);
    }

    //createButton will need to be called after AbsAdjustableDialog.setHeights in constructor
    protected void createButton(){
        okTopY = startY + topHeight + midHeight + OK_BUTTON_Y_OFFSET;
        okBotY = okTopY + Assets.OptionOk.getHeight();
        buttonList = new ButtonList();
        okButton = new Button(OK_BUTTON_LEFT_X, OK_BUTTON_RIGHT_X, okTopY, okBotY, true, "OK", Assets.OptionOk);
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
                        okButtonAction();
                        break;
                    }
                }
            }
        }
    }

    protected abstract void okButtonAction();

    @Override
    public void paint(float deltaTime) {
        super.paint(deltaTime);
        buttonList.drawImage(game.getGraphics());
    }
}
