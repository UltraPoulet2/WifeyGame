package ultrapoulet.wifeygame.screens;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter.Align;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 1/18/2017.
 */

public class BattleResultScreen extends Screen{

    private static final int RESULT_X = 50;
    private static final int RESULT_Y = 50;
    private static final int EXP_Y = 250;
    private static final int GOLD_Y = 310;
    private static final int CENTER_X = 399;
    private static final int RESULT_OBJECT_WIDTH = 150;
    private static final int TEXT_SPACING = 10;
    private static final int TEXT_WIDTH = 30;
    private static final int TEXT_HEIGHT = 60;
    private static final int TEXT_OFFSET = 0;

    private static final int CONTINUE_LEFT_X = 500;
    private static final int CONTINUE_RIGHT_X = 750;
    private static final int CONTINUE_TOP_Y = 1130;
    private static final int CONTINUE_BOTTOM_Y = 1230;
    private static final String CONTINUE_STRING = "Continue";

    private BattleInfo info;
    private List<BattleCharacter> party;
    private List<BattleCharacter> enemies;
    private ButtonList buttons;
    private Button continueButton;
    private Button pressed;

    private boolean victory;
    //Calculate actual values later
    private int baseExp = 0;
    private int bonusExp = 1;
    private int baseGold = 0;
    private int bonusGold = 1;

    public BattleResultScreen(Game game, BattleInfo info, List<BattleCharacter> party, List<BattleCharacter> enemies){
        super(game);
        this.info = info;
        this.party = party;
        this.enemies = enemies;

        buttons = new ButtonList();
        continueButton = new Button(CONTINUE_LEFT_X, CONTINUE_RIGHT_X, CONTINUE_TOP_Y, CONTINUE_BOTTOM_Y, true, CONTINUE_STRING);
        buttons.addButton(continueButton);

        victory = enemies.get(enemies.size() - 1).getCurrentHP() == 0;
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            TouchEvent t = touchEvents.get(i);
            if(t.type == TouchEvent.TOUCH_DOWN){
                pressed = buttons.getButtonPressed(t.x, t.y);
            }
            else if(t.type == TouchEvent.TOUCH_UP){
                if(pressed != null && pressed == buttons.getButtonPressed(t.x, t.y)){
                    if(pressed == continueButton) {
                        game.setScreen(new BattleSelectScreen(game));
                    }
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.BattleResultScreen, 0, 0);

        if(victory){
            g.drawImage(Assets.BattleResultVictory, RESULT_X, RESULT_Y);
        }
        else {
            g.drawImage(Assets.BattleResultDefeat, RESULT_X, RESULT_Y);
        }

        int currentX;

        int expWidth = RESULT_OBJECT_WIDTH + TEXT_SPACING + TEXT_WIDTH /*For the plus*/;
        int baseExpDigits = Integer.toString(baseExp).length();
        int bonusExpDigits = Integer.toString(bonusExp).length();
        expWidth += baseExpDigits * TEXT_WIDTH;
        if(bonusExp > 0){
            expWidth += TEXT_SPACING + (bonusExpDigits * TEXT_WIDTH);
        }
        currentX = CENTER_X - (expWidth / 2);
        g.drawImage(Assets.BattleResultExp, currentX, EXP_Y);
        currentX += RESULT_OBJECT_WIDTH + TEXT_SPACING;
        g.drawImage(Assets.Plus, currentX, EXP_Y);
        currentX += TEXT_WIDTH;
        NumberPrinter.drawNumber(g, baseExp, currentX, EXP_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.YellowNumbers, Align.LEFT);
        if(bonusExp > 0) {
            currentX += baseExpDigits * TEXT_WIDTH + TEXT_SPACING;
            g.drawImage(Assets.Plus, currentX, EXP_Y);
            currentX += TEXT_WIDTH;
            NumberPrinter.drawNumber(g, bonusExp, currentX, EXP_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.YellowNumbers, Align.LEFT);
        }

        int goldWidth = RESULT_OBJECT_WIDTH + TEXT_SPACING + TEXT_WIDTH /*For the plus*/;
        int baseGoldDigits = Integer.toString(baseGold).length();
        int bonusGoldDigits = Integer.toString(bonusGold).length();
        goldWidth += baseGoldDigits * TEXT_WIDTH;
        if(bonusGold > 0){
            goldWidth += TEXT_SPACING + (bonusGoldDigits * TEXT_WIDTH);
        }
        currentX = CENTER_X - (goldWidth / 2);
        g.drawImage(Assets.BattleResultGold, currentX, GOLD_Y);
        currentX += RESULT_OBJECT_WIDTH + TEXT_SPACING;
        g.drawImage(Assets.Plus, currentX, GOLD_Y);
        currentX += TEXT_WIDTH;
        NumberPrinter.drawNumber(g, baseGold, currentX, GOLD_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.YellowNumbers, Align.LEFT);
        if(bonusGold > 0) {
            currentX += baseGoldDigits * TEXT_WIDTH + TEXT_SPACING;
            g.drawImage(Assets.Plus, currentX, GOLD_Y);
            currentX += TEXT_WIDTH;
            NumberPrinter.drawNumber(g, bonusGold, currentX, GOLD_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.YellowNumbers, Align.LEFT);
        }
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

    }
}
