package ultrapoulet.wifeygame;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;

/**
 * Created by John on 5/19/2016.
 */
public class CharacterInfoScreen extends Screen {

    private Screen previousScreen;

    private WifeyCharacter displayChar;

    private Image background;

    private enum ButtonPressed{
        CLOSE
        //Fill with skill buttons later
    }

    public CharacterInfoScreen(Game game) {
        super(game);
        background = Assets.CharacterInfoScreen;
    }

    public void setPreviousScreen(Screen screen){
        previousScreen = screen;
    }

    public void setChar(WifeyCharacter input){
        displayChar = input;
    }

    private ButtonPressed getButtonPressed(int x, int y){
        if(x >= 650 && x <= 725 && y >= 65 && y <= 140){
            return ButtonPressed.CLOSE;
        }
        return null;
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if (t.type == TouchEvent.TOUCH_UP) {
                ButtonPressed press = getButtonPressed(t.x, t.y);
                if(press == null){
                    continue;
                }

                switch (press) {
                    case CLOSE:
                        backButton();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g= game.getGraphics();
        g.drawImage(background, 50, 75);
        g.drawPercentageImage(displayChar.getImage(),80,175,200,200);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        game.setScreen(previousScreen);
    }
}
