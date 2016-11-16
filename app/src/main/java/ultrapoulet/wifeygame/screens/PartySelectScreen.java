package ultrapoulet.wifeygame.screens;

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
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.battle.BattleInfo;
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

    private ButtonList basicButtonList;
    private Button lastPressed;

    private Button prevButton;
    private static final int PREV_BUTTON_LEFT_X = 45;
    private static final int PREV_BUTTON_RIGHT_X = 215;
    private static final int PREV_BUTTON_TOP_Y = 338;
    private static final int PREV_BUTTON_BOT_Y = 388;
    private static final String PREV_BUTTON_STRING = "Prev";

    private Button nextButton;
    private static final int NEXT_BUTTON_LEFT_X = 315;
    private static final int NEXT_BUTTON_RIGHT_X = 485;
    private static final int NEXT_BUTTON_TOP_Y = 338;
    private static final int NEXT_BUTTON_BOT_Y = 388;
    private static final String NEXT_BUTTON_STRING = "Next";

    private Button sortButton;
    private static final int SORT_BUTTON_LEFT_X = 585;
    private static final int SORT_BUTTON_RIGHT_X = 755;
    private static final int SORT_BUTTON_TOP_Y = 348;
    private static final int SORT_BUTTON_BOT_Y = 378;
    private static final String SORT_BUTTON_STRING = "Sort";

    private Button backButton;
    private static final int BACK_BUTTON_LEFT_X = 45;
    private static final int BACK_BUTTON_RIGHT_X = 215;
    private static final int BACK_BUTTON_TOP_Y = 1150;
    private static final int BACK_BUTTON_BOT_Y = 1250;
    private static final String BACK_BUTTON_STRING = "Back";

    private Button acceptButton;
    private static final int ACCEPT_BUTTON_LEFT_X = 585;
    private static final int ACCEPT_BUTTON_RIGHT_X = 755;
    private static final int ACCEPT_BUTTON_TOP_Y = 1150;
    private static final int ACCEPT_BUTTON_BOT_Y = 1250;
    private static final String ACCEPT_BUTTON_STRING = "Accept";

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

    private static final int CHAR_REQUIRED_HOLDER_BASE_X = CHAR_IMAGE_BASE_LEFT_X - 3;
    private static final int CHAR_REQUIRED_HOLDER_BASE_Y = CHAR_IMAGE_BASE_TOP_Y - 12;
    private static final int CHAR_REQUIRED_OFFSET = 90;

    private ButtonList partyList;

    private static final int PARTY_IMAGE_BASE_LEFT_X = 55;
    private static final int PARTY_IMAGE_BASE_RIGHT_X = 145;
    private static final int PARTY_IMAGE_OFFSET_X = 100;
    private static final int PARTY_IMAGE_TOP_Y = 180;
    private static final int PARTY_IMAGE_BOT_Y = 270;

    private static final int DRAGGING_OFFSET = 60;

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

        createButtons();
    }

    public void createButtons(){
        basicButtonList = new ButtonList();
        prevButton = new Button(PREV_BUTTON_LEFT_X, PREV_BUTTON_RIGHT_X, PREV_BUTTON_TOP_Y, PREV_BUTTON_BOT_Y, false, PREV_BUTTON_STRING);
        basicButtonList.addButton(prevButton);
        nextButton = new Button(NEXT_BUTTON_LEFT_X, NEXT_BUTTON_RIGHT_X, NEXT_BUTTON_TOP_Y, NEXT_BUTTON_BOT_Y, false, NEXT_BUTTON_STRING);
        basicButtonList.addButton(nextButton);
        sortButton = new Button(SORT_BUTTON_LEFT_X, SORT_BUTTON_RIGHT_X, SORT_BUTTON_TOP_Y, SORT_BUTTON_BOT_Y, false, SORT_BUTTON_STRING);
        basicButtonList.addButton(sortButton);
        backButton = new Button(BACK_BUTTON_LEFT_X, BACK_BUTTON_RIGHT_X, BACK_BUTTON_TOP_Y, BACK_BUTTON_BOT_Y, true, BACK_BUTTON_STRING);
        basicButtonList.addButton(backButton);
        acceptButton = new Button(ACCEPT_BUTTON_LEFT_X, ACCEPT_BUTTON_RIGHT_X, ACCEPT_BUTTON_TOP_Y, ACCEPT_BUTTON_BOT_Y, false, ACCEPT_BUTTON_STRING);
        basicButtonList.addButton(acceptButton);

        partyList = new ButtonList();
        for(int i = 0; i < 7; i++){
            int leftX = PARTY_IMAGE_BASE_LEFT_X + PARTY_IMAGE_OFFSET_X * i;
            int rightX = PARTY_IMAGE_BASE_RIGHT_X + PARTY_IMAGE_OFFSET_X * i;
            int topY = PARTY_IMAGE_TOP_Y;
            int botY = PARTY_IMAGE_BOT_Y;
            partyList.addButton(new Button(leftX, rightX, topY, botY, false, "PARTY_" + i));
        }
    }


    public void updateButtons(){
        prevButton.setActive(currentPage > 1);
        nextButton.setActive(currentPage < maxPage);
        acceptButton.setActive(validParty());
    }

    public void updatePartyButtons(){
        for(int i = 0; i < maxPartySize; i++){
            partyList.setIndexActive(i, true);
        }
    }

    public boolean validParty(){
        if(currentParty[0] != null){
            if(battleInfo == null || battleInfo.validParty(currentParty)){
                return true;
            }
        }
        return false;
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
        currentPage = 1;
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

        maxPartySize = battleInfo != null ? battleInfo.getPartyMax() : 7;
        finalIndex = maxPartySize - 1;
        currentParty = new WifeyCharacter[maxPartySize];
        for(int i = 0; i < currentParty.length; i++){
            currentParty[i] = Party.getIndex(i);
        }
        setValidCharacters(RecruitedCharacters.getArray());
        updateButtons();
        updatePartyButtons();
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
                draggingPartyIndex = partyList.getIndexPressed(t.x, t.y);
                lastPressed = basicButtonList.getButtonPressed(t.x, t.y);
                continue;
            }
            else if (t.type == TouchEvent.TOUCH_UP) {
                if(!dragging){
                    Button pressed = basicButtonList.getButtonPressed(t.x, t.y);
                    int pressedRecruit = getRecruitIndex(t.x, t.y);
                    int pressedParty = partyList.getIndexPressed(t.x, t.y);
                    if(lastPressed == pressed && lastPressed != null) {
                        if(lastPressed == backButton){
                            backButton();
                        }
                        else if(lastPressed == acceptButton){
                            Party.setParty(currentParty);
                            backButton();
                        }
                        else if(lastPressed == prevButton){
                            currentPage--;
                            updateButtons();
                        }
                        else if(lastPressed == nextButton){
                            currentPage++;
                            updateButtons();
                        }
                    }
                    else if(pressedRecruit != -1){
                        charInfo.setChar(validCharacters.get(draggingRecruitIndex));
                        game.setScreen(charInfo);
                    }
                    else if(pressedParty != -1 && currentParty[pressedParty] != null){
                        charInfo.setChar(currentParty[draggingPartyIndex]);
                        game.setScreen(charInfo);
                    }
                }
                else if(dragging && draggingRecruitIndex != -1){
                    int partyIndex = partyList.getIndexPressed(t.x, t.y);
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
                    updateButtons();
                }
                else if(dragging && draggingPartyIndex != -1){
                    int partyIndex = partyList.getIndexPressed(t.x, t.y);
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
                    updateButtons();
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
                else if(!dragging && draggingPartyIndex != -1 && partyList.getIndexPressed(t.x, t.y) != draggingPartyIndex){
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

        if(prevButton.isActive()){
            g.drawImage(Assets.PrevPageEnable, PREV_BUTTON_LEFT_X, PREV_BUTTON_TOP_Y);
        }
        else{
            g.drawImage(Assets.PrevPageDisable, PREV_BUTTON_LEFT_X, PREV_BUTTON_TOP_Y);
        }

        if(nextButton.isActive()){
            g.drawImage(Assets.NextPageEnable, NEXT_BUTTON_LEFT_X, NEXT_BUTTON_TOP_Y);
        }
        else{
            g.drawImage(Assets.NextPageDisable, NEXT_BUTTON_LEFT_X, NEXT_BUTTON_TOP_Y);
        }

        for(int i = 0; i < maxPartySize && currentParty[i] != null; i++){
            if(!dragging || i != draggingPartyIndex) {
                g.drawPercentageImage(currentParty[i].getImage(), PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y, PARTY_SCALE, PARTY_SCALE);
                if(battleInfo != null && !battleInfo.allowCharacter(currentParty[i])){
                    g.drawPercentageImage(Assets.InvalidChar, PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y, PARTY_SCALE, PARTY_SCALE);
                }
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
                //Since this can only be on row 1, the Y doesn't calculate offset.
                g.drawImage(Assets.RequiredCharHolder, (i % ROW_SIZE) * CHAR_REQUIRED_OFFSET + CHAR_REQUIRED_HOLDER_BASE_X, CHAR_REQUIRED_HOLDER_BASE_Y);
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
            if(battleInfo != null && !battleInfo.allowCharacter(currentParty[draggingPartyIndex])){
                g.drawPercentageImage(Assets.InvalidChar, draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, DRAGGING_SCALE, DRAGGING_SCALE);
            }
        }

        if(acceptButton.isActive()){
            g.drawImage(Assets.AcceptEnable, ACCEPT_BUTTON_LEFT_X, ACCEPT_BUTTON_TOP_Y);
        }
        else{
            g.drawImage(Assets.AcceptDisable, ACCEPT_BUTTON_LEFT_X, ACCEPT_BUTTON_TOP_Y);
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
