package ultrapoulet.wifeygame.screens.dialogs;

import android.graphics.Color;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.util.ArrayList;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 6/21/2017.
 */

public class RequirementWifeyInfoDialog extends AbsAdjustableInfoDialog {

    private String text;
    private TextPaint infoPaint;
    private static final int FONT_SIZE = 30;
    private static final int TEXT_X = BG_X + 15;
    private static final int TEXT_WIDTH = 570; //Also the width I have usable
    private static final int HEIGHT_OFFSET = 3;

    private ArrayList<Image> wifeyImages = new ArrayList<>();
    private int imageStartY;
    private static final int IMAGES_PER_ROW = 6;
    private static final int IMAGE_SIZE = 80;
    private static final int IMAGE_OFFSET = 18;
    private static final int IMAGE_BASE_X = BG_X + 15;

    public RequirementWifeyInfoDialog(Game game, Screen prevScreen, String text, ArrayList<WifeyCharacter> wifeys){
        super(game, prevScreen);

        infoPaint = new TextPaint();
        infoPaint.setTextSize(FONT_SIZE);
        infoPaint.setColor(Color.BLACK);

        this.text = text;

        StaticLayout textLayout = new StaticLayout(
                text, infoPaint, TEXT_WIDTH, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int textHeight = textLayout.getHeight();

        int imageHeight = (wifeys.size() / IMAGES_PER_ROW) * (IMAGE_SIZE + IMAGE_OFFSET) + IMAGE_SIZE + HEIGHT_OFFSET;
        Graphics g = game.getGraphics();
        for(WifeyCharacter wifey : wifeys){
            wifeyImages.add(wifey.getImage(g));
        }

        int totalHeight = imageHeight + textHeight + topHeight + botHeight + HEIGHT_OFFSET;

        //Add in the the necessary functions in super
        setHeights(totalHeight);
        createButton();

        imageStartY = startY + topHeight + textHeight + HEIGHT_OFFSET;
    }

    @Override
    protected void okButtonAction() {
        backButton();
    }

    @Override
    public void paint(float deltaTime) {
        super.paint(deltaTime);
        Graphics g = game.getGraphics();
        g.drawMultiLineString(text, TEXT_X, startY + topHeight, TEXT_WIDTH, infoPaint);

        for(int i = 0; i < wifeyImages.size(); i++){
            g.drawScaledImage(wifeyImages.get(i), IMAGE_BASE_X + ((IMAGE_SIZE + IMAGE_OFFSET) * (i % IMAGES_PER_ROW)), imageStartY + ((IMAGE_SIZE + IMAGE_OFFSET) * (i / IMAGES_PER_ROW)), IMAGE_SIZE, IMAGE_SIZE);
        }
    }
}
