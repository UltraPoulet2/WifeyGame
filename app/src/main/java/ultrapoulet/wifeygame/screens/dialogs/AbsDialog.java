package ultrapoulet.wifeygame.screens.dialogs;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.TextPaint;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 5/15/2017.
 */

public class AbsDialog extends Screen {

    protected Screen previousScreen;

    protected static final int BACKGROUND_X = 200;
    protected static final int BACKGROUND_Y = 540;

    protected String text = "Placeholder that is multiple lines";
    protected TextPaint textPaint;
    protected static final int FONT_SIZE = 30;
    protected static final int TEXT_X = BACKGROUND_X + 200;
    protected static final int TEXT_Y = BACKGROUND_Y + 15;
    protected static final int TEXT_WIDTH = 370;

    private boolean fadeOut = true;

    public AbsDialog(Game game, Screen prevScreen) {
        super(game);
        this.previousScreen = prevScreen;

        textPaint = new TextPaint();
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(FONT_SIZE);

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
        g.drawImage(Assets.DialogBackground, BACKGROUND_X, BACKGROUND_Y);
        g.drawMultiLineString(text, TEXT_X, TEXT_Y, TEXT_WIDTH, textPaint);

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
