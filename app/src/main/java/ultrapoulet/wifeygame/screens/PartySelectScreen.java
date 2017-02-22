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
import ultrapoulet.androidgame.framework.helpers.DropdownMenu;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
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
    private List<WifeyCharacter> currentParty;
    private int maxPartySize;
    private BattleInfo battleInfo;

    private Screen previousScreen;

    private CharacterInfoScreen charInfo;

    private Paint textPaint;

    private Image background;

    private int currentPage = 0;
    private int maxPage;
    private final static int COLUMN_SIZE = 8;
    private final static int ROW_SIZE = 8;
    private final static int PER_PAGE = COLUMN_SIZE * ROW_SIZE;

    private final static int HALF_SCALE = 50;
    private final static int PARTY_SCALE = 57;
    private final static int DRAGGING_SCALE = 75;

    private final static int DRAG_WAIT = 10;
    private float holdTime = 0;
    private boolean dragging = false;
    private int draggingX = 0;
    private int draggingY = 0;
    private int draggingRecruitIndex = -1;
    private int draggingPartyIndex = -1;

    private ButtonList basicButtonList;
    private Button lastPressed;
    private boolean sortMenuPressed;
    private Button sortPressed;

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

    private DropdownMenu sortDropdown;
    private static final int SORT_BUTTON_LEFT_X = 510;
    private static final int SORT_BUTTON_RIGHT_X = 755;
    private static final int SORT_BUTTON_TOP_Y = 338;
    private static final int SORT_BUTTON_BOT_Y = 388;
    private static final String ALPHA_SORT_STRING = "A -> Z";
    private static final String STR_SORT_STRING = "Strength";
    private static final String MAG_SORT_STRING = "Magic";

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

    private static final int CUR_PAGE_X = 255;
    private static final int MAX_PAGE_X = 265;
    private static final int PAGE_Y = 355;
    private static final int PAGE_WIDTH = 20;
    private static final int PAGE_HEIGHT = 40;

    private ButtonList partyList;

    private static final int PARTY_IMAGE_BASE_LEFT_X = 55;
    private static final int PARTY_IMAGE_BASE_RIGHT_X = 145;
    private static final int PARTY_IMAGE_OFFSET_X = 100;
    private static final int PARTY_IMAGE_TOP_Y = 180;
    private static final int PARTY_IMAGE_BOT_Y = 270;

    private ButtonList recruitList;

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

    private ArrayList<Image> partyImages;
    private ArrayList<Image> recruitImages;

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

    private enum SortMethod {
        ALPHA,
        STRENGTH,
        MAGIC
    }

    private static SortMethod currentSort = SortMethod.ALPHA;

    private Comparator<WifeyCharacter> getSort(){
        switch(currentSort){
            case ALPHA:
                return nameComp;
            case STRENGTH:
                return strengthComp;
            case MAGIC:
                return magicComp;
            default:
                return nameComp;
        }
    }

    private String getSortTitle(){
        switch(currentSort){
            case ALPHA:
                return ALPHA_SORT_STRING;
            case STRENGTH:
                return STR_SORT_STRING;
            case MAGIC:
                return MAG_SORT_STRING;
            default:
                return ALPHA_SORT_STRING;
        }
    }

    public PartySelectScreen(Game game, Screen previousScreen){
        this(game, previousScreen, null);
    }

    public PartySelectScreen(Game game, Screen previousScreen, BattleInfo info){
        super(game);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setTextSize(25);

        background = Assets.PartySelectScreen;

        charInfo = new CharacterInfoScreen(game, this);

        createButtons();

        createImageLists();

        setPreviousScreen(previousScreen);

        setBattleInfo(info);
    }

    public void createButtons(){
        basicButtonList = new ButtonList();
        prevButton = new Button(PREV_BUTTON_LEFT_X, PREV_BUTTON_RIGHT_X, PREV_BUTTON_TOP_Y, PREV_BUTTON_BOT_Y, false, PREV_BUTTON_STRING, Assets.PrevPageEnable, Assets.PrevPageDisable);
        basicButtonList.addButton(prevButton);
        nextButton = new Button(NEXT_BUTTON_LEFT_X, NEXT_BUTTON_RIGHT_X, NEXT_BUTTON_TOP_Y, NEXT_BUTTON_BOT_Y, false, NEXT_BUTTON_STRING, Assets.NextPageEnable, Assets.NextPageDisable);
        basicButtonList.addButton(nextButton);
        //Temporary ButtonDropdown
        Paint sortingPaint = new Paint();
        sortingPaint.setTextSize(30);
        sortingPaint.setColor(Color.BLACK);
        sortingPaint.setTextAlign(Align.CENTER);
        List<String> sortingList = new ArrayList<>();
        sortingList.add(ALPHA_SORT_STRING);
        sortingList.add(STR_SORT_STRING);
        sortingList.add(MAG_SORT_STRING);
        sortDropdown = new DropdownMenu(SORT_BUTTON_LEFT_X, SORT_BUTTON_RIGHT_X, SORT_BUTTON_TOP_Y, SORT_BUTTON_BOT_Y, Assets.DropdownMenuTop, Assets.DropdownMenuOption, sortingPaint, sortingList);
        sortDropdown.setTitle(getSortTitle());

        //Back button does not have an image associated with it
        backButton = new Button(BACK_BUTTON_LEFT_X, BACK_BUTTON_RIGHT_X, BACK_BUTTON_TOP_Y, BACK_BUTTON_BOT_Y, true, BACK_BUTTON_STRING, null, null);
        basicButtonList.addButton(backButton);
        acceptButton = new Button(ACCEPT_BUTTON_LEFT_X, ACCEPT_BUTTON_RIGHT_X, ACCEPT_BUTTON_TOP_Y, ACCEPT_BUTTON_BOT_Y, false, ACCEPT_BUTTON_STRING, Assets.AcceptEnable, Assets.AcceptDisable);
        basicButtonList.addButton(acceptButton);

        partyList = new ButtonList();
        for(int i = 0; i < 7; i++){
            int leftX = PARTY_IMAGE_BASE_LEFT_X + PARTY_IMAGE_OFFSET_X * i;
            int rightX = PARTY_IMAGE_BASE_RIGHT_X + PARTY_IMAGE_OFFSET_X * i;
            int topY = PARTY_IMAGE_TOP_Y;
            int botY = PARTY_IMAGE_BOT_Y;
            partyList.addButton(new Button(leftX, rightX, topY, botY, false, "PARTY_" + i));
        }

        recruitList = new ButtonList();
        for(int i = 0; i < PER_PAGE; i++){
            int leftX = CHAR_IMAGE_BASE_LEFT_X + CHAR_IMAGE_OFFSET_X * (i % ROW_SIZE);
            int rightX = CHAR_IMAGE_BASE_RIGHT_X + CHAR_IMAGE_OFFSET_X * (i % ROW_SIZE);
            int topY = CHAR_IMAGE_BASE_TOP_Y + CHAR_IMAGE_OFFSET_Y * ((i % PER_PAGE) / COLUMN_SIZE);
            int botY = CHAR_IMAGE_BASE_BOT_Y + CHAR_IMAGE_OFFSET_Y * ((i % PER_PAGE) / COLUMN_SIZE);
            recruitList.addButton(new Button(leftX, rightX, topY, botY, false, "RECRUIT_" + i));
        }
    }

    private void createImageLists() {
        partyImages = new ArrayList<>(7);

        recruitImages = new ArrayList<>(PER_PAGE);
        for(int i = 0; i < PER_PAGE; i++){
            recruitImages.add(null);
        }

    }


    public void updateButtons(){
        prevButton.setActive(currentPage > 0);
        nextButton.setActive(currentPage < maxPage);
        acceptButton.setActive(validParty());
    }

    public void updatePartyButtons(){
        for(int i = 0; i < maxPartySize; i++){
            partyList.get(i).setActive(true);
        }
    }

    public void updateRecruitButtons(){
        for(int i = 0; i < PER_PAGE; i++){
            if((currentPage * PER_PAGE + i) < validCharacters.size() ){
                recruitList.get(i).setActive(true);
            }
            else{
                recruitList.get(i).setActive(false);
            }
        }
    }

    private void updateRecruitImages(){
        for(int i = 0; i < PER_PAGE; i++){
            if(currentPage * PER_PAGE + i < validCharacters.size()){
                recruitImages.set(i, validCharacters.get(currentPage * PER_PAGE + i).getImage(game.getGraphics()));
            }
            else{
                recruitImages.set(i, null);
            }
        }
    }

    public boolean validParty(){
        if(currentParty.size() > 0){
            if(battleInfo == null || battleInfo.validParty(currentParty)){
                return true;
            }
        }
        return false;
    }

    public void setValidCharacters(List<WifeyCharacter> inputCharacters){
        validCharacters = new ArrayList<>();
        for(int i = 0; i < inputCharacters.size(); i++){
            //Do a check to make sure the character is valid for this battle
            boolean allowed = true;
            if(battleInfo != null){
                allowed = battleInfo.allowCharacter(inputCharacters.get(i));
            }
            if(allowed){
                validCharacters.add(inputCharacters.get(i));
            }
        }
        Collections.sort(validCharacters, getSort());
        currentPage = 0;
        maxPage = (validCharacters.size() / PER_PAGE);
    }

    private void setBattleInfo(BattleInfo info){
        this.battleInfo = info;

        updateParty();

        setValidCharacters(RecruitedCharacters.getArray());
        updateButtons();
        updatePartyButtons();
        updateRecruitButtons();
        updateRecruitImages();
    }

    private void updateParty(){
        maxPartySize = battleInfo != null ? battleInfo.getPartyMax() : 7;
        currentParty = new ArrayList<>();
        for(int i = 0; i < Party.partySize() && i < maxPartySize; i++){
            currentParty.add(Party.getIndex(i));
            partyImages.add(currentParty.get(i).getImage(game.getGraphics()));
        }
    }

    private void setPreviousScreen(Screen previousScreen){
        this.previousScreen = previousScreen;
    }

    //Gets the index of character in the full list
    //that is presently being touched
    private int getRecruitIndex(int x, int y){
        int index = recruitList.getIndexPressed(x, y);
        if(index == -1){
            return -1;
        }
        else{
            return currentPage * PER_PAGE + index;
        }
    }

    @Override
    public void update(float deltaTime){
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if (t.type == TouchEvent.TOUCH_DOWN){
                dragging = false;
                holdTime = 0;
                draggingX = t.x;
                draggingY = t.y;
                draggingRecruitIndex = getRecruitIndex(t.x, t.y);
                draggingPartyIndex = partyList.getIndexPressed(t.x, t.y);
                lastPressed = basicButtonList.getButtonPressed(t.x, t.y);
                sortPressed = sortDropdown.getButtonPressed(t.x, t.y);
                sortMenuPressed = sortDropdown.isMenuPressed(t.x, t.y);
                continue;
            }
            else if (t.type == TouchEvent.TOUCH_UP) {
                if(sortMenuPressed && sortDropdown.isMenuPressed(t.x, t.y)){
                    sortDropdown.activateMenu();
                }
                else if(sortDropdown.isMenuActive()){
                    if(sortPressed != null && sortPressed == sortDropdown.getButtonPressed(t.x, t.y)) {
                        String result = sortPressed.getName();
                        switch (result) {
                            case ALPHA_SORT_STRING:
                                currentSort = SortMethod.ALPHA;
                                break;
                            case STR_SORT_STRING:
                                currentSort = SortMethod.STRENGTH;
                                break;
                            case MAG_SORT_STRING:
                                currentSort = SortMethod.MAGIC;
                                break;
                        }
                        if(result != null){
                            Collections.sort(validCharacters, getSort());
                            updateRecruitImages();
                            sortDropdown.setTitle(getSortTitle());
                        }
                    }
                    sortDropdown.deactivateMenu();
                }
                else if(!dragging){
                    Button pressed = basicButtonList.getButtonPressed(t.x, t.y);
                    int pressedRecruit = getRecruitIndex(t.x, t.y);
                    int pressedParty = partyList.getIndexPressed(t.x, t.y);
                    if(lastPressed == pressed && lastPressed != null) {
                        //BACK BUTTON PRESSED
                        if(lastPressed == backButton){
                            backButton();
                        }
                        //ACCEPT BUTTON PRESSED
                        else if(lastPressed == acceptButton){
                            Party.setParty(currentParty);
                            backButton();
                        }
                        //PREV PAGE BUTTON PRESSED
                        else if(lastPressed == prevButton){
                            currentPage--;
                            updateButtons();
                            updateRecruitButtons();
                            updateRecruitImages();
                        }
                        //NEXT PAGE BUTTON PRESSED
                        else if(lastPressed == nextButton){
                            currentPage++;
                            updateButtons();
                            updateRecruitButtons();
                            updateRecruitImages();
                        }
                    }
                    //RECRUIT CHARACTER PRESSED
                    else if(pressedRecruit != -1 && pressedRecruit == draggingRecruitIndex){
                        charInfo.setChar(validCharacters.get(draggingRecruitIndex));
                        game.setScreen(charInfo);
                    }
                    //PARTY CHARACTER PRESSED
                    else if(pressedParty != -1 && pressedParty == draggingPartyIndex && pressedParty < currentParty.size()){
                        charInfo.setChar(currentParty.get(draggingPartyIndex));
                        game.setScreen(charInfo);
                    }
                }
                //DRAGGING A RECRUIT
                else if(dragging && draggingRecruitIndex != -1){
                    int partyIndex = partyList.getIndexPressed(t.x, t.y);
                    if(partyIndex != -1){
                        int inPartyIndex = currentParty.indexOf(validCharacters.get(draggingRecruitIndex));
                        if(inPartyIndex != -1 ){
                            if (currentParty.size() <= partyIndex){
                                currentParty.remove(inPartyIndex);
                                currentParty.add(validCharacters.get(draggingRecruitIndex));

                                Image temp = partyImages.get(inPartyIndex);
                                partyImages.remove(inPartyIndex);
                                partyImages.add(temp);
                            }
                            else{
                                WifeyCharacter temp = currentParty.get(inPartyIndex);
                                currentParty.set(inPartyIndex, currentParty.get(partyIndex));
                                currentParty.set(partyIndex, temp);

                                Image tempImage = partyImages.get(inPartyIndex);
                                partyImages.set(inPartyIndex, partyImages.get(partyIndex));
                                partyImages.set(partyIndex, tempImage);
                            }
                        }
                        else{
                            if(currentParty.size() <= partyIndex) {
                                currentParty.add(validCharacters.get(draggingRecruitIndex));
                                partyImages.add(validCharacters.get(draggingRecruitIndex).getImage(game.getGraphics()));
                            }
                            else{
                                currentParty.set(partyIndex, validCharacters.get(draggingRecruitIndex));
                                partyImages.set(partyIndex, validCharacters.get(draggingRecruitIndex).getImage(game.getGraphics()));
                            }
                        }
                    }
                    updateButtons();
                }
                //DRAGGING A PARTY MEMBER
                else if(dragging && draggingPartyIndex != -1){
                    int partyIndex = partyList.getIndexPressed(t.x, t.y);
                    if(partyIndex != -1 && partyIndex != draggingPartyIndex){
                        WifeyCharacter temp = currentParty.get(draggingPartyIndex);
                        if (currentParty.size() <= partyIndex){
                            currentParty.remove(draggingPartyIndex);
                            currentParty.add(temp);

                            Image tempImage = partyImages.get(draggingPartyIndex);
                            partyImages.remove(draggingPartyIndex);
                            partyImages.add(tempImage);
                        }
                        else{
                            currentParty.set(draggingPartyIndex, currentParty.get(partyIndex));
                            currentParty.set(partyIndex, temp);

                            Image tempImage = partyImages.get(draggingPartyIndex);
                            partyImages.set(draggingPartyIndex, partyImages.get(partyIndex));
                            partyImages.set(partyIndex, tempImage);
                        }
                    }
                    if(partyIndex == -1){
                        currentParty.remove(draggingPartyIndex);

                        partyImages.remove(draggingPartyIndex);
                    }
                    updateButtons();
                }
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
        if(game.getInput().isTouchDown(0) &&
                !sortDropdown.isMenuActive() &&
                (draggingRecruitIndex != -1 || (draggingPartyIndex != -1 && draggingPartyIndex < currentParty.size()))){
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

        int displayPage = currentPage + 1;
        int displayMaxPage = maxPage + 1;
        NumberPrinter.drawNumber(g, displayPage, CUR_PAGE_X, PAGE_Y, PAGE_WIDTH, PAGE_HEIGHT, 0, Assets.WhiteNumbers, NumberPrinter.Align.RIGHT);
        NumberPrinter.drawNumber(g, displayMaxPage, MAX_PAGE_X, PAGE_Y, PAGE_WIDTH, PAGE_HEIGHT, 0, Assets.WhiteNumbers, NumberPrinter.Align.LEFT);


        basicButtonList.drawImage(g);
        for(int i = 0; i < currentParty.size(); i++){
            if(!dragging || i != draggingPartyIndex) {
                g.drawPercentageImage(partyImages.get(i), PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y, PARTY_SCALE, PARTY_SCALE);
                if(battleInfo != null && !battleInfo.allowCharacter(currentParty.get(i))){
                    g.drawPercentageImage(Assets.InvalidChar, PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y, PARTY_SCALE, PARTY_SCALE);
                }
            }
        }
        for(int i = maxPartySize; i < 7; i++){
            g.drawImage(Assets.LockSelection, PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y);
        }

        //If the dropdown is not activated, draw it on a lower layer than the characters
        if(!sortDropdown.isMenuActive()) {
            sortDropdown.drawImage(g);
        }

        int minIndex = PER_PAGE * currentPage;
        int maxIndex = PER_PAGE * (currentPage + 1);
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
                g.drawPercentageImage(recruitImages.get(i - minIndex),
                        CHAR_IMAGE_OFFSET_X * (i % ROW_SIZE) + CHAR_IMAGE_BASE_LEFT_X,
                        CHAR_IMAGE_BASE_TOP_Y + CHAR_IMAGE_OFFSET_Y * ((i % PER_PAGE) / COLUMN_SIZE),
                        HALF_SCALE, HALF_SCALE);
            }
        }
        if(dragging && draggingRecruitIndex != -1){
            g.drawPercentageImage(recruitImages.get(draggingRecruitIndex - minIndex), draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, DRAGGING_SCALE, DRAGGING_SCALE);
        }
        if(dragging && draggingPartyIndex != -1){
            g.drawPercentageImage(partyImages.get(draggingPartyIndex), draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, DRAGGING_SCALE, DRAGGING_SCALE);
            if(battleInfo != null && !battleInfo.allowCharacter(currentParty.get(draggingPartyIndex))){
                g.drawPercentageImage(Assets.InvalidChar, draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, DRAGGING_SCALE, DRAGGING_SCALE);
            }
        }

        //If the dropdown is activated, draw it on the top layer
        if(sortDropdown.isMenuActive()) {
            sortDropdown.drawImage(g);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        Collections.sort(validCharacters, getSort());
        updateRecruitImages();
        sortDropdown.setTitle(getSortTitle());
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        game.setScreen(previousScreen);
    }
}
