package ultrapoulet.wifeygame.battle;

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
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.CharacterInfoScreen;
import ultrapoulet.wifeygame.PartySelectScreen;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;

/**
 * Created by John on 10/12/2016.
 */

public class BattleInfoScreen extends Screen{

    private Screen prevScreen;

    private BattleInfo battleInfo;
    private PartySelectScreen partySelect;

    private CharacterInfoScreen charInfo;
    private int selectedChar;

    private Image background = Assets.BattleInfoScreen;

    private WifeyCharacter[] party;

    private static int BATTLE_NAME_X = 400;
    private static int BATTLE_NAME_Y = 92;
    private Paint battlePaint;

    private static final int PARTY_IMAGE_BASE_LEFT_X = 55;
    private static final int PARTY_IMAGE_BASE_RIGHT_X = 145;
    private static final int PARTY_IMAGE_OFFSET_X = 100;
    private static final int PARTY_IMAGE_TOP_Y = 1000;
    private static final int PARTY_IMAGE_BOT_Y = 1090;
    private final static int PARTY_SCALE = 57;

    private static int BACK_BUTTON_LEFT_X = 45;
    private static int BACK_BUTTON_RIGHT_X = 215;
    private static int BACK_BUTTON_TOP_Y = 1150;
    private static int BACK_BUTTON_BOTTOM_Y = 1250;

    private static int PARTY_BUTTON_LEFT_X = 250;
    private static int PARTY_BUTTON_RIGHT_X = 550;
    private static int PARTY_BUTTON_TOP_Y = 1150;
    private static int PARTY_BUTTON_BOTTOM_Y = 1250;

    private static int START_BUTTON_LEFT_X = 585;
    private static int START_BUTTON_RIGHT_X = 755;
    private static int START_BUTTON_TOP_Y = 1150;
    private static int START_BUTTON_BOTTOM_Y = 1250;

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
        battlePaint.setTextSize(40);

        partySelect = new PartySelectScreen(game);
        partySelect.setPreviousScreen(this);

        charInfo = new CharacterInfoScreen(game);
        charInfo.setPreviousScreen(this);
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

    //Gets the index of the party member that is presently being touched
    private int getPartyIndex(int x, int y){
        for(int i = 0; i < battleInfo.getPartyMax(); i++){
            int xLeft = PARTY_IMAGE_BASE_LEFT_X + PARTY_IMAGE_OFFSET_X * i;
            int xRight = PARTY_IMAGE_BASE_RIGHT_X + PARTY_IMAGE_OFFSET_X * i;
            int yTop = PARTY_IMAGE_TOP_Y;
            int yBot = PARTY_IMAGE_BOT_Y;
            if(x >= xLeft && x <= xRight && y >= yTop && y <= yBot){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if(t.type == TouchEvent.TOUCH_DOWN){
                lastPressed = getButtonPressed(t.x, t.y);
                selectedChar = getPartyIndex(t.x, t.y);
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
                else if(selectedChar == getPartyIndex(t.x, t.y) && selectedChar != -1){
                    charInfo.setChar(party[selectedChar]);
                    game.setScreen(charInfo);
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.clearScreen(0xFF777777);

        g.drawImage(background, 0, 0);

        g.drawString(battleInfo.getName(), BATTLE_NAME_X, BATTLE_NAME_Y, battlePaint);

        for(int i = 0; i < battleInfo.getPartyMax() && party[i] != null; i++){
            g.drawPercentageImage(party[i].getImage(), PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y, PARTY_SCALE, PARTY_SCALE);
        }
        for(int i = battleInfo.getPartyMax(); i < 7; i++){
            g.drawImage(Assets.LockSelection, PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        party = Party.getParty();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        game.setScreen(prevScreen);
    }
}
