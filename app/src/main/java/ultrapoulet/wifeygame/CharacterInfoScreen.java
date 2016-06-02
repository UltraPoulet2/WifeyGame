package ultrapoulet.wifeygame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

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

    private Image background;
    private static final int BG_X = 50;
    private static final int BG_Y = 75;

    private static final int CLOSE_LEFT_X = BG_X + 600;
    private static final int CLOSE_RIGHT_X = CLOSE_LEFT_X + 75;
    private static final int CLOSE_TOP_Y = BG_Y + 10;
    private static final int CLOSE_BOT_Y = CLOSE_TOP_Y + 75;

    private WifeyCharacter displayChar;
    private static final int CHAR_X = 30 + BG_X;
    private static final int CHAR_Y = 100 + BG_Y;
    private static final int DOUBLE_SCALE = 200;

    private Paint namePaint;
    private int fontSize;
    private int nameY;
    private static final int MAX_NAME_FONT = 40;
    private static final int MAX_NAME_SIZE = 246;
    private static final int NAME_X = 402 + BG_X;
    private static final int MAX_NAME_Y = 155 + BG_Y;

    private Paint levelPaint;
    private static final int LEVEL_SIZE = 34;
    private static final int LEVEL_X = 425 + BG_X;
    private static final int LEVEL_Y = 237 + BG_Y;

    private Paint statPaint;
    private static final int STAT_SIZE = 34;
    private static final int HP_X = 440 + BG_X;
    private static final int STR_X = 85 + HP_X;
    private static final int MAG_X = 85 + STR_X;
    private static final int STAT_Y = 322 + BG_Y;

    private enum ButtonPressed{
        CLOSE
        //Fill with skill buttons later
    }

    public CharacterInfoScreen(Game game) {
        super(game);
        background = Assets.CharacterInfoScreen;

        createPaints();
    }

    private void createPaints(){
        namePaint = new Paint();
        namePaint.setTextAlign(Align.LEFT);
        namePaint.setColor(Color.BLACK);

        levelPaint = new Paint();
        levelPaint.setTextAlign(Align.CENTER);
        levelPaint.setColor(Color.BLACK);
        levelPaint.setTextSize(LEVEL_SIZE);

        statPaint = new Paint();
        statPaint.setTextAlign(Align.CENTER);
        statPaint.setColor(Color.BLACK);
        statPaint.setTextSize(STAT_SIZE);
    }

    public void setPreviousScreen(Screen screen){
        previousScreen = screen;
    }

    public void setChar(WifeyCharacter input){
        displayChar = input;
        fontSize = MAX_NAME_FONT;
        namePaint.setTextSize(fontSize);
        while(namePaint.measureText(displayChar.getName()) > MAX_NAME_SIZE){
            fontSize--;
            namePaint.setTextSize(fontSize);
        }
        nameY = MAX_NAME_Y - ((MAX_NAME_FONT - fontSize) / 2);
    }

    private ButtonPressed getButtonPressed(int x, int y){
        if(x >= CLOSE_LEFT_X && x <= CLOSE_RIGHT_X && y >= CLOSE_TOP_Y && y <= CLOSE_BOT_Y){
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
        g.drawImage(background, BG_X, BG_Y);
        g.drawPercentageImage(displayChar.getImage(),CHAR_X, CHAR_Y, DOUBLE_SCALE, DOUBLE_SCALE);

        g.drawString(displayChar.getName(), NAME_X, nameY, namePaint);

        g.drawString("1", LEVEL_X, LEVEL_Y, levelPaint);

        g.drawString(String.valueOf(displayChar.getHP()), HP_X, STAT_Y, statPaint);
        g.drawString(String.valueOf(displayChar.getStrength()), STR_X, STAT_Y, statPaint);
        g.drawString(String.valueOf(displayChar.getMagic()), MAG_X, STAT_Y, statPaint);

        //Draw image for weapon category
        //Draw string for weapon name
        //Draw image for number hits
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
