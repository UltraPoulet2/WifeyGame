package ultrapoulet.wifeygame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.battle.BattleScreen;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 5/6/2016.
 */
public class PartySelectScreen extends Screen {

    private List<WifeyCharacter> validCharacters;
    private WifeyCharacter[] currentParty;
    private int maxPartySize;
    private int finalIndex;
    private BattleInfo battleInfo;

    private Screen previousScreen;

    private CharacterInfoScreen charInfo;

    private Paint textPaint;

    private Image background;

    private int currentPage = 1;
    private int maxPage;
    private final static int COLUMN_SIZE = 8;
    private final static int ROW_SIZE = 8;
    private final static int PER_PAGE = COLUMN_SIZE * ROW_SIZE;

    private final static int HALF_SCALE = 50;
    private final static int PARTY_SCALE = 57;
    private final static int DRAGGING_SCALE = 75;

    private Image[] numbers;

    private boolean touchDown = false;
    private final static int DRAG_WAIT = 10;
    private float holdTime = 0;
    private boolean dragging = false;
    private int draggingX = 0;
    private int draggingY = 0;
    private int draggingRecruitIndex = -1;
    private int draggingPartyIndex = -1;

    private enum ButtonPressed{
        PREV_PAGE,
        NEXT_PAGE,
        SORT,
        BACK,
        ACCEPT
    };

    private static final int BACK_BUTTON_LEFT_X = 45;
    private static final int BACK_BUTTON_RIGHT_X = 215;
    private static final int BACK_BUTTON_TOP_Y = 1150;
    private static final int BACK_BUTTON_BOT_Y = 1250;

    private static final int ACCEPT_BUTTON_LEFT_X = 585;
    private static final int ACCEPT_BUTTON_RIGHT_X = 755;
    private static final int ACCEPT_BUTTON_TOP_Y = 1150;
    private static final int ACCEPT_BUTTON_BOT_Y = 1250;

    private static final int PREV_BUTTON_LEFT_X = 45;
    private static final int PREV_BUTTON_RIGHT_X = 215;
    private static final int PREV_BUTTON_TOP_Y = 338;
    private static final int PREV_BUTTON_BOT_Y = 388;

    private static final int NEXT_BUTTON_LEFT_X = 315;
    private static final int NEXT_BUTTON_RIGHT_X = 485;
    private static final int NEXT_BUTTON_TOP_Y = 338;
    private static final int NEXT_BUTTON_BOT_Y = 388;

    private static final int SORT_BUTTON_LEFT_X = 585;
    private static final int SORT_BUTTON_RIGHT_X = 755;
    private static final int SORT_BUTTON_TOP_Y = 348;
    private static final int SORT_BUTTON_BOT_Y = 378;

    private static final int CUR_PAGE_X_1 = 215;
    private static final int CUR_PAGE_X_2 = 235;
    private static final int MAX_PAGE_X_1 = 265;
    private static final int MAX_PAGE_X_2 = 285;
    private static final int PAGE_Y = 355;

    private static final int CHAR_IMAGE_WIDTH = 80;
    private static final int CHAR_IMAGE_HEIGHT = 80;
    private static final int CHAR_IMAGE_BASE_LEFT_X = 45;
    private static final int CHAR_IMAGE_BASE_RIGHT_X = CHAR_IMAGE_BASE_LEFT_X + CHAR_IMAGE_WIDTH;
    private static final int CHAR_IMAGE_OFFSET_X = 90;
    private static final int CHAR_IMAGE_BASE_TOP_Y = 400;
    private static final int CHAR_IMAGE_BASE_BOT_Y = CHAR_IMAGE_BASE_TOP_Y + CHAR_IMAGE_HEIGHT;
    private static final int CHAR_IMAGE_OFFSET_Y = 90;

    private static final int CHAR_HIGHLIGHT_WIDTH = 86;
    private static final int CHAR_HIGHLIGHT_HEIGHT = 86;
    private static final int CHAR_HIGHLIGHT_BASE_X = 42;
    private static final int CHAR_HIGHLIGHT_BASE_Y = 397;
    private static final int CHAR_HIGHLIGHT_OFFSET = 90;

    private static final int PARTY_IMAGE_BASE_LEFT_X = 60;
    private static final int PARTY_IMAGE_BASE_RIGHT_X = 150;
    private static final int PARTY_IMAGE_OFFSET_X = 100;
    private static final int PARTY_IMAGE_TOP_Y = 180;
    private static final int PARTY_IMAGE_BOT_Y = 270;

    private static final int DRAGGING_OFFSET = 60;

    private ButtonPressed lastPressed = null;

    private boolean hasRequiredList(){
        return battleInfo != null && battleInfo.getRequiredList() != null;
    }

    private Comparator<WifeyCharacter> nameComp = new Comparator<WifeyCharacter>(){
        @Override
        public int compare(WifeyCharacter a, WifeyCharacter b){
            if(hasRequiredList()){
                ArrayList<WifeyCharacter> list = battleInfo.getRequiredList();
                if(list.contains(a) && list.contains(b)){
                    return a.compareName(b);
                }
                else if(list.contains(a)){
                    return -1;
                }
                else if(list.contains(b)){
                    return 1;
                }
            }
            return a.compareName(b);
        }
    };

    private Comparator<WifeyCharacter> strengthComp = new Comparator<WifeyCharacter>() {
        @Override
        public int compare(WifeyCharacter a, WifeyCharacter b) {
            if(hasRequiredList()){
                ArrayList<WifeyCharacter> list = battleInfo.getRequiredList();
                if(list.contains(a) && list.contains(b)){
                    return a.compareStrength(b);
                }
                else if(list.contains(a)){
                    return -1;
                }
                else if(list.contains(b)){
                    return 1;
                }
            }
            return a.compareStrength(b);
        }
    };

    private Comparator<WifeyCharacter> magicComp = new Comparator<WifeyCharacter>() {
        @Override
        public int compare(WifeyCharacter a, WifeyCharacter b) {
            if(hasRequiredList()){
                ArrayList<WifeyCharacter> list = battleInfo.getRequiredList();
                if(list.contains(a) && list.contains(b)){
                    return a.compareMagic(b);
                }
                else if(list.contains(a)){
                    return -1;
                }
                else if(list.contains(b)){
                    return 1;
                }
            }
            return a.compareMagic(b);
        }
    };

    public PartySelectScreen(Game game){
        super(game);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setTextSize(25);

        background = Assets.PartySelectScreen;

        numbers = Assets.HPNumbers;

        charInfo = new CharacterInfoScreen(game);
        charInfo.setPreviousScreen(this);
    }

    public void setValidCharacters(WifeyCharacter[] inputCharacters){
        validCharacters = new ArrayList<>();
        for(int i = 0; i < inputCharacters.length; i++){
            //Do a check to make sure the character is valid for this battle
            boolean allowed = true;
            if(battleInfo != null){
                allowed = battleInfo.allowCharacter(inputCharacters[i]);
            }
            if(allowed){
                validCharacters.add(inputCharacters[i]);
            }
        }
        Collections.sort(validCharacters, nameComp);
        maxPage = (validCharacters.size() / PER_PAGE) + 1;
    }

    public void setCurrentParty(WifeyCharacter[] inputParty){
        //For now, only 7.
        this.currentParty = new WifeyCharacter[maxPartySize];
        //Maybe do validation here too
        this.currentParty = inputParty;
    }

    public void setBattleInfo(BattleInfo info){
        this.battleInfo = info;

        maxPartySize = battleInfo.getPartyMax();
        finalIndex = maxPartySize - 1;
        currentParty = new WifeyCharacter[maxPartySize];
        for(int i = 0; i < currentParty.length; i++){
            currentParty[i] = Party.getIndex(i);
        }
        setValidCharacters(RecruitedCharacters.getArray());
    }

    public void setPreviousScreen(Screen previousScreen){
        this.previousScreen = previousScreen;
    }

    //Gets the index of character in the full list
    //that is presently being touched
    private int getRecruitIndex(int x, int y){
        for(int i = PER_PAGE * (currentPage - 1); i < PER_PAGE * currentPage && i < validCharacters.size(); i++){
            int xLeft = CHAR_IMAGE_BASE_LEFT_X + CHAR_IMAGE_OFFSET_X * (i % ROW_SIZE);
            int xRight = CHAR_IMAGE_BASE_RIGHT_X + CHAR_IMAGE_OFFSET_X * (i % ROW_SIZE);
            int yTop = CHAR_IMAGE_BASE_TOP_Y + CHAR_IMAGE_OFFSET_Y * ((i % PER_PAGE) / COLUMN_SIZE);
            int yBot = CHAR_IMAGE_BASE_BOT_Y + CHAR_IMAGE_OFFSET_Y * ((i % PER_PAGE) / COLUMN_SIZE);
            if(x >= xLeft && x <= xRight && y >= yTop && y <= yBot){
                return i;
            }
        }
        return -1;
    }

    //Gets the index of the party member that is presently being touched
    private int getPartyIndex(int x, int y){
        for(int i = 0; i < maxPartySize; i++){
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

    private ButtonPressed getButtonPressed(int x, int y){
        if(x >= BACK_BUTTON_LEFT_X && x <= BACK_BUTTON_RIGHT_X && y >= BACK_BUTTON_TOP_Y && y <= BACK_BUTTON_BOT_Y){
            return ButtonPressed.BACK;
        }
        else if(x >= ACCEPT_BUTTON_LEFT_X && x <= ACCEPT_BUTTON_RIGHT_X && y >= ACCEPT_BUTTON_TOP_Y && y <= ACCEPT_BUTTON_BOT_Y) {
            return ButtonPressed.ACCEPT;
        }
        else if(x >= PREV_BUTTON_LEFT_X && x <= PREV_BUTTON_RIGHT_X && y >= PREV_BUTTON_TOP_Y && y <= PREV_BUTTON_BOT_Y) {
            return ButtonPressed.PREV_PAGE;
        }
        else if(x >= NEXT_BUTTON_LEFT_X && x <= NEXT_BUTTON_RIGHT_X && y >= NEXT_BUTTON_TOP_Y && y <= NEXT_BUTTON_BOT_Y) {
            return ButtonPressed.NEXT_PAGE;
        }
        else if(x >= SORT_BUTTON_LEFT_X && x <= SORT_BUTTON_RIGHT_X && y >= SORT_BUTTON_TOP_Y && y <= SORT_BUTTON_BOT_Y) {
            return ButtonPressed.SORT;
        }
        return null;
    }

    @Override
    public void update(float deltaTime){
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if (t.type == TouchEvent.TOUCH_DOWN){
                touchDown = true;
                dragging = false;
                holdTime = 0;
                draggingX = t.x;
                draggingY = t.y;
                draggingRecruitIndex = getRecruitIndex(t.x, t.y);
                draggingPartyIndex = getPartyIndex(t.x, t.y);
                lastPressed = getButtonPressed(t.x, t.y);
                continue;
            }
            else if (t.type == TouchEvent.TOUCH_UP) {
                if(!dragging){
                    if(lastPressed == ButtonPressed.BACK && getButtonPressed(t.x, t.y) == ButtonPressed.BACK){
                        backButton();
                    }
                    else if(lastPressed == ButtonPressed.ACCEPT && getButtonPressed(t.x, t.y) == ButtonPressed.ACCEPT){
                        if(currentParty[0] != null) {
                            if(battleInfo == null || battleInfo.validParty(currentParty)) {
                                BattleScreen bs = new BattleScreen(game);
                                Party.setParty(currentParty);
                                bs.setParty(Party.getBattleParty());
                                bs.setBattleInfo(battleInfo);
                                bs.setBackground(Assets.testBG);
                                game.setScreen(bs);
                            }
                        }
                    }
                    else if(lastPressed == ButtonPressed.PREV_PAGE && getButtonPressed(t.x, t.y) == ButtonPressed.PREV_PAGE && currentPage > 1) {
                        currentPage--;
                    }
                    else if(lastPressed == ButtonPressed.NEXT_PAGE && getButtonPressed(t.x, t.y) == ButtonPressed.NEXT_PAGE && currentPage < maxPage){
                        currentPage++;
                    }
                    else if(draggingRecruitIndex != -1){
                        charInfo.setChar(validCharacters.get(draggingRecruitIndex));
                        game.setScreen(charInfo);
                    }
                    else if(draggingPartyIndex != -1 && currentParty[draggingPartyIndex] != null){
                        charInfo.setChar(currentParty[draggingPartyIndex]);
                        game.setScreen(charInfo);
                    }
                }
                else if(dragging && draggingRecruitIndex != -1){
                    int partyIndex = getPartyIndex(t.x, t.y);
                    while(partyIndex - 1 > 0 && currentParty[partyIndex - 1] == null){
                        partyIndex--;
                    }
                    if(partyIndex != -1){
                        int inPartyIndex = -1;
                        for(int j = 0; j < currentParty.length; j++){
                            if(currentParty[j] == validCharacters.get(draggingRecruitIndex)){
                                inPartyIndex = j;
                            }
                        }
                        //For now, if already in party, ignore
                        if(inPartyIndex != -1 && inPartyIndex != partyIndex){
                            WifeyCharacter temp = currentParty[inPartyIndex];
                            currentParty[inPartyIndex] = currentParty[partyIndex];
                            currentParty[partyIndex] = temp;
                            if(currentParty[inPartyIndex] == null){
                                for(int j = inPartyIndex; j + 1 < maxPartySize; j++){
                                    currentParty[j] = currentParty[j + 1];
                                }
                                currentParty[finalIndex] = null;
                            }
                        }
                        else{
                            currentParty[partyIndex] = validCharacters.get(draggingRecruitIndex);
                        }
                    }
                }
                else if(dragging && draggingPartyIndex != -1){
                    int partyIndex = getPartyIndex(t.x, t.y);
                    while(partyIndex - 1 > 0 && currentParty[partyIndex - 1] == null){
                        partyIndex--;
                    }
                    if(partyIndex != -1 && partyIndex != draggingPartyIndex){
                        WifeyCharacter temp = currentParty[draggingPartyIndex];
                        currentParty[draggingPartyIndex] = currentParty[partyIndex];
                        currentParty[partyIndex] = temp;
                    }
                    if(partyIndex == -1 || currentParty[draggingPartyIndex] == null){
                        for(int j = draggingPartyIndex; j + 1 < maxPartySize; j++){
                            currentParty[j] = currentParty[j + 1];
                        }
                        currentParty[finalIndex] = null;
                    }

                }
                touchDown = false;
                dragging = false;
                holdTime = 0;
                draggingX = 0;
                draggingY = 0;
                draggingRecruitIndex = -1;
                draggingPartyIndex = -1;
            }
            else if(t.type == TouchEvent.TOUCH_DRAGGED) {
                if(!dragging && draggingRecruitIndex != -1 && getRecruitIndex(t.x, t.y) != draggingRecruitIndex){
                    holdTime = 0;
                    draggingRecruitIndex = -1;
                }
                else if(!dragging && draggingPartyIndex != -1 && getPartyIndex(t.x, t.y) != draggingPartyIndex){
                    holdTime = 0;
                    draggingPartyIndex = -1;
                }
                else{
                    draggingX = t.x;
                    draggingY = t.y;
                }
            }
        }
        if(game.getInput().isTouchDown(0) && (draggingRecruitIndex != -1 || (draggingPartyIndex != -1 && currentParty[draggingPartyIndex] != null))){
            if (holdTime < DRAG_WAIT) {
                holdTime += deltaTime;
            } else if (!dragging) {
                dragging = true;
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.clearScreen(0xFFFFFFFF);
        g.drawImage(background, 0, 0);

        if(currentPage > 10){
            g.drawImage(numbers[currentPage / 10], CUR_PAGE_X_1, PAGE_Y);
        }
        g.drawImage(numbers[currentPage % 10], CUR_PAGE_X_2, PAGE_Y);

        if(maxPage > 10){
            g.drawImage(numbers[maxPage / 10], MAX_PAGE_X_1, PAGE_Y);
            g.drawImage(numbers[maxPage % 10], MAX_PAGE_X_2, PAGE_Y);
        }
        else {
            g.drawImage(numbers[maxPage % 10], MAX_PAGE_X_1, PAGE_Y);
        }

        if(currentPage == 1){
            g.drawImage(Assets.PrevPageDisable, PREV_BUTTON_LEFT_X, PREV_BUTTON_TOP_Y);
        }
        else{
            g.drawImage(Assets.PrevPageEnable, PREV_BUTTON_LEFT_X, PREV_BUTTON_TOP_Y);
        }

        if(currentPage == maxPage){
            g.drawImage(Assets.NextPageDisable, NEXT_BUTTON_LEFT_X, NEXT_BUTTON_TOP_Y);
        }
        else{
            g.drawImage(Assets.NextPageEnable, NEXT_BUTTON_LEFT_X, NEXT_BUTTON_TOP_Y);
        }

        for(int i = 0; i < maxPartySize && currentParty[i] != null; i++){
            if(!dragging || i != draggingPartyIndex) {
                g.drawPercentageImage(currentParty[i].getImage(), PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y, PARTY_SCALE, PARTY_SCALE);
            }
        }
        for(int i = maxPartySize; i < 7; i++){
            g.drawImage(Assets.LockSelection, PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y);
        }
        int minIndex = PER_PAGE * (currentPage - 1);
        int maxIndex = PER_PAGE * currentPage;
        ArrayList<WifeyCharacter> reqList;
        if(hasRequiredList()){
            reqList = battleInfo.getRequiredList();
        }
        else{
            reqList = new ArrayList<>();
        }
        for(int i = minIndex; i < validCharacters.size() && i < maxIndex; i++){
            if(reqList.contains(validCharacters.get(i))){
                g.drawRect((i % ROW_SIZE) * CHAR_HIGHLIGHT_OFFSET + CHAR_HIGHLIGHT_BASE_X,
                        (i % PER_PAGE / COLUMN_SIZE) * CHAR_HIGHLIGHT_OFFSET + CHAR_HIGHLIGHT_BASE_Y,
                        CHAR_HIGHLIGHT_WIDTH, CHAR_HIGHLIGHT_HEIGHT, Color.BLUE);
            }
            if(!dragging || i != draggingRecruitIndex) {
                g.drawPercentageImage(validCharacters.get(i).getImage(),
                        CHAR_IMAGE_OFFSET_X * (i % ROW_SIZE) + CHAR_IMAGE_BASE_LEFT_X,
                        CHAR_IMAGE_BASE_TOP_Y + CHAR_IMAGE_OFFSET_Y * ((i % PER_PAGE) / COLUMN_SIZE),
                        HALF_SCALE, HALF_SCALE);
            }
        }
        if(dragging && draggingRecruitIndex != -1){
            g.drawPercentageImage(validCharacters.get(draggingRecruitIndex).getImage(), draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, DRAGGING_SCALE, DRAGGING_SCALE);
        }
        if(dragging && draggingPartyIndex != -1){
            g.drawPercentageImage(currentParty[draggingPartyIndex].getImage(), draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, DRAGGING_SCALE, DRAGGING_SCALE);
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
        game.setScreen(previousScreen);
    }
}
