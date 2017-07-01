package ultrapoulet.wifeygame.screens.dialogs;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 6/17/2017.
 */

public abstract class AbsAdjustableDialog extends Screen {

    protected Screen prevScreen;

    protected static final int topHeight = Assets.AbsAdjustableDialogTop.getHeight();
    protected int midHeight;
    protected static final int botHeight = Assets.AbsAdjustableDialogBot.getHeight();

    protected int startY;
    protected static final int BG_X = 100;

    protected static final int MIN_HEIGHT = 50;

    private boolean fadeOut = true;

    public AbsAdjustableDialog(Game game, Screen prevScreen){
        super(game);
        this.prevScreen = prevScreen;
    }

    //setHeights will need to be called by the constructor of an actual implementation
    protected void setHeights(int height){
        this.midHeight = height - topHeight - botHeight;
        if(this.midHeight < 0) {
            this.midHeight = MIN_HEIGHT;
        }
        startY = 640 - ((topHeight + midHeight + botHeight) / 2);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        if(fadeOut) {
            g.drawARGB(192, 256, 256, 256);
            fadeOut = false;
        }
        g.drawImage(Assets.AbsAdjustableDialogTop, BG_X, startY);
        g.drawScaledImage(Assets.AbsAdjustableDialogMid, BG_X, startY + topHeight, Assets.AbsAdjustableDialogMid.getWidth(), midHeight);
        g.drawImage(Assets.AbsAdjustableDialogBot, BG_X, startY + topHeight + midHeight);
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
        game.setScreen(prevScreen);
    }
}
