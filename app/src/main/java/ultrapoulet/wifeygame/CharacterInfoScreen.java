package ultrapoulet.wifeygame;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Screen;

/**
 * Created by John on 5/19/2016.
 */
public class CharacterInfoScreen extends Screen {

    private Screen previousScreen;

    private WifeyCharacter displayChar;

    public CharacterInfoScreen(Game game) {
        super(game);
    }

    public void setPreviousScreen(Screen screen){
        previousScreen = screen;
    }

    public void setChar(WifeyCharacter input){
        displayChar = input;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g= game.getGraphics();
        g.clearScreen(0xFFFFFFFF);
        g.drawPercentageImage(displayChar.getImage(),0,0,200,200);
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
