package ultrapoulet.wifeygame.battle;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.PartySelectScreen;
import ultrapoulet.wifeygame.gamestate.Party;

/**
 * Created by John on 10/12/2016.
 */

public class BattleInfoScreen extends Screen{

    private Screen prevScreen;

    private BattleInfo battleInfo;
    private PartySelectScreen partySelect;

    private static int BATTLE_NAME_X = 400;
    private static int BATTLE_NAME_Y = 100;
    private Paint battlePaint;

    private static int BACK_BUTTON_LEFT_X = 50;
    private static int BACK_BUTTON_RIGHT_X = 150;
    private static int BACK_BUTTON_TOP_Y = 1150;
    private static int BACK_BUTTON_BOTTOM_Y = 1250;

    private static int PARTY_BUTTON_LEFT_X = 400;
    private static int PARTY_BUTTON_RIGHT_X = 500;
    private static int PARTY_BUTTON_TOP_Y = 900;
    private static int PARTY_BUTTON_BOTTOM_Y = 1000;

    private static int START_BUTTON_LEFT_X = 650;
    private static int START_BUTTON_RIGHT_X = 750;
    private static int START_BUTTON_TOP_Y = 1150;
    private static int START_BUTTON_BOTTOM_Y = 1250;

    //This is temporary
    private Paint buttonPaint;

    private ButtonPressed lastPressed = null;

    private enum ButtonPressed{
        BACK,
        PARTY,
        START
    }

    public BattleInfoScreen(Game game){
        super(game);

        battlePaint = new Paint();
        battlePaint.setColor(Color.BLACK);
        battlePaint.setTextAlign(Align.CENTER);
        battlePaint.setTextSize(30);

        buttonPaint = new Paint();
        buttonPaint.setColor(Color.YELLOW);
        buttonPaint.setTextAlign(Align.LEFT);
        buttonPaint.setTextSize(20);

        partySelect = new PartySelectScreen(game);
        partySelect.setPreviousScreen(this);
    }

    public void setBattleInfo(BattleInfo info){
        this.battleInfo = info;
    }

    public void setPreviousScreen(Screen prevScreen){
        this.prevScreen = prevScreen;
    }

    private ButtonPressed getButtonPressed(int x, int y){
        if(x >= BACK_BUTTON_LEFT_X && x <= BACK_BUTTON_RIGHT_X && y >= BACK_BUTTON_TOP_Y && y <= BACK_BUTTON_BOTTOM_Y){
            return ButtonPressed.BACK;
        }
        else if(x >= PARTY_BUTTON_LEFT_X && x <= PARTY_BUTTON_RIGHT_X && y >= PARTY_BUTTON_TOP_Y && y <= PARTY_BUTTON_BOTTOM_Y){
            return ButtonPressed.PARTY;
        }
        else if(x >= START_BUTTON_LEFT_X && x <= START_BUTTON_RIGHT_X && y >= START_BUTTON_TOP_Y && y <= START_BUTTON_BOTTOM_Y){
            return ButtonPressed.START;
        }
        else{
            return null;
        }
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if(t.type == TouchEvent.TOUCH_DOWN){
                lastPressed = getButtonPressed(t.x, t.y);
                continue;
            }
            else if(t.type == TouchEvent.TOUCH_UP){
                ButtonPressed pressed = getButtonPressed(t.x, t.y);
                if(lastPressed == pressed && pressed != null){
                    switch(pressed){
                        case BACK:
                            backButton();
                            break;
                        case PARTY:
                            partySelect.setBattleInfo(battleInfo);
                            game.setScreen(partySelect);
                            break;
                        case START:
                            if(Party.partySize() > 0 && battleInfo.validParty(Party.getParty())){
                                BattleScreen bs = new BattleScreen(game);
                                bs.setParty(Party.getBattleParty());
                                bs.setBattleInfo(battleInfo);
                                bs.setBackground(Assets.testBG);
                                game.setScreen(bs);
                            }
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.clearScreen(0xFF777777);

        g.drawString(battleInfo.getName(), BATTLE_NAME_X, BATTLE_NAME_Y, battlePaint);

        g.drawString("BACK", BACK_BUTTON_LEFT_X, BACK_BUTTON_TOP_Y, buttonPaint);
        g.drawString("PARTY", PARTY_BUTTON_LEFT_X, PARTY_BUTTON_TOP_Y, buttonPaint);
        g.drawString("START", START_BUTTON_LEFT_X, START_BUTTON_TOP_Y, buttonPaint);
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
