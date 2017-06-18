package ultrapoulet.wifeygame.screens.dialogs;

import android.graphics.Color;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;

/**
 * Created by John on 6/18/2017.
 */

public class RequirementTextInfoDialog extends AbsAdjustableInfoDialog {

    private String text;
    private TextPaint infoPaint;
    private static final int FONT_SIZE = 30;
    private static final int TEXT_X = BG_X + 15;
    private static final int TEXT_WIDTH = 570;
    private static final int TOTAL_HEIGHT_OFFSET = 3;

    public RequirementTextInfoDialog(Game game, Screen prevScreen, String text){
        super(game, prevScreen);

        infoPaint = new TextPaint();
        infoPaint.setTextSize(FONT_SIZE);
        infoPaint.setColor(Color.BLACK);

        this.text = text;

        StaticLayout textLayout = new StaticLayout(
                text, infoPaint, TEXT_WIDTH, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        /*
        Rect textBounds = new Rect();
        infoPaint.getTextBounds(text, 0, text.length(), textBounds);
        System.out.println(textBounds.height() + " " + textBounds.width());
        int totalHeight = textBounds.height() + topHeight + botHeight + TOTAL_HEIGHT_OFFSET;
        */
        int totalHeight = textLayout.getHeight() + topHeight + botHeight + TOTAL_HEIGHT_OFFSET;

        //Add in the the necessary functions in super
        setHeights(totalHeight);
        createButton();
    }

    @Override
    protected void okButtonAction() {
        backButton();
    }

    @Override
    public void paint(float deltaTime) {
        super.paint(deltaTime);
        game.getGraphics().drawMultiLineString(text, TEXT_X, startY + topHeight, TEXT_WIDTH, infoPaint);
    }
}
