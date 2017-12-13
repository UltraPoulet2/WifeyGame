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
    private List<WifeyCharacter> requiredCharacters;
    private ArrayList<Image> partyImages;
    private ArrayList<Image> recruitImages;

    private int maxPartySize;
    private BattleInfo battleInfo;

    private Screen previousScreen;

    private CharacterInfoScreen charInfo;

    private Paint textPaint;

    private Image background;

    private int currentPage = 0;
    private int maxPage;
    //Number of Rows
    private final static int ROW_SIZE = 7;
    //Number of Columns
    private final static int COLUMN_SIZE = 8;
    private final static int PER_PAGE = ROW_SIZE * COLUMN_SIZE;

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
    private static final int PREV_BUTTON_TOP_Y = 422;
    private static final int PREV_BUTTON_BOT_Y = 472;
    private static final String PREV_BUTTON_STRING = "Prev";

    private Button nextButton;
    private static final int NEXT_BUTTON_LEFT_X = 315;
    private static final int NEXT_BUTTON_RIGHT_X = 485;
    private static final int NEXT_BUTTON_TOP_Y = 422;
    private static final int NEXT_BUTTON_BOT_Y = 472;
    private static final String NEXT_BUTTON_STRING = "Next";

    private DropdownMenu sortDropdown;
    private Paint sortingPaint;
    private List<String> sortingList;
    private static final int SORT_BUTTON_LEFT_X = 510;
    private static final int SORT_BUTTON_RIGHT_X = 755;
    private static final int SORT_BUTTON_TOP_Y = 422;
    private static final int SORT_BUTTON_BOT_Y = 472;
    private static final String ALPHA_SORT_STRING = "A -> Z";
    private static final String STR_SORT_STRING = "Strength";
    private static final String MAG_SORT_STRING = "Magic";
    private static final String FAV_SORT_STRING = "Favorite";

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
    private static final int PAGE_Y = 439;
    private static final int PAGE_WIDTH = 20;
    private static final int PAGE_HEIGHT = 40;

    private int partyNum = 0;
    private static final int MAX_PARTY_NUM = 9;
    private static final int PARTY_NUM_CENTER_X = 250;
    private static final int PARTY_NUM_TOP_Y = 185;
    private static final int PARTY_NUM_HEIGHT = 60;
    private static final int PARTY_NUM_WIDTH = 30;
    private static final int PARTY_NUM_SPACING = 0;

    private Button prevPartyButton;
    private Button nextPartyButton;
    private static final int PREV_PARTY_BUTTON_LEFT_X = 144;
    private static final int PREV_PARTY_BUTTON_RIGHT_X = 190;
    private static final int NEXT_PARTY_BUTTON_LEFT_X = 610;
    private static final int NEXT_PARTY_BUTTON_RIGHT_X = 656;
    private static final int PARTY_BUTTON_TOP_Y = 164;
    private static final int PARTY_BUTTON_BOT_Y = 364;
    private static final String PREV_PARTY_BUTTON_STRING = "Prev Party";
    private static final String NEXT_PARTY_BUTTON_STRING = "Next Party";

    private ButtonList partyList;

    private static final int PARTY_NUM_TOP_ROW = 3;
    private static final int PARTY_IMAGE_BASE_ROW_1_LEFT_X = 305;
    private static final int PARTY_IMAGE_BASE_ROW_1_RIGHT_X = 395;
    private static final int PARTY_IMAGE_BASE_ROW_2_LEFT_X = 205;
    private static final int PARTY_IMAGE_BASE_ROW_2_RIGHT_X = 295;
    private static final int PARTY_IMAGE_OFFSET = 100;
    private static final int PARTY_IMAGE_BASE_TOP_Y = 170;
    private static final int PARTY_IMAGE_BASE_BOT_Y = 260;

    private ButtonList recruitList;

    private static final int CHAR_IMAGE_WIDTH = 80;
    private static final int CHAR_IMAGE_HEIGHT = 80;
    private static final int CHAR_IMAGE_BASE_LEFT_X = 45;
    private static final int CHAR_IMAGE_BASE_RIGHT_X = CHAR_IMAGE_BASE_LEFT_X + CHAR_IMAGE_WIDTH;
    private static final int CHAR_IMAGE_OFFSET_X = 90;
    private static final int CHAR_IMAGE_BASE_TOP_Y = 490;
    private static final int CHAR_IMAGE_BASE_BOT_Y = CHAR_IMAGE_BASE_TOP_Y + CHAR_IMAGE_HEIGHT;
    private static final int CHAR_IMAGE_OFFSET_Y = 90;
    private static final int CHAR_FAVORITE_SIZE = 30;

    private static final int CHAR_REQUIRED_HOLDER_BASE_X = CHAR_IMAGE_BASE_LEFT_X - 3;
    private static final int CHAR_REQUIRED_HOLDER_BASE_Y = CHAR_IMAGE_BASE_TOP_Y - 12;
    private static final int CHAR_REQUIRED_OFFSET = 90;

    private static final int DRAGGING_OFFSET = 60;

    private Comparator<WifeyCharacter> nameComp = new Comparator<WifeyCharacter>(){
        @Override
        public int compare(WifeyCharacter a, WifeyCharacter b){
            if(requiredCharacters.contains(a) && requiredCharacters.contains(b)){
                return a.compareName(b);
            }
            else if(requiredCharacters.contains(a)){
                return -1;
            }
            else if(requiredCharacters.contains(b)){
                return 1;
            }
            return a.compareName(b);
        }
    };

    private Comparator<WifeyCharacter> strengthComp = new Comparator<WifeyCharacter>() {
        @Override
        public int compare(WifeyCharacter a, WifeyCharacter b) {
            if(requiredCharacters.contains(a) && requiredCharacters.contains(b)){
                return a.compareStrength(b);
            }
            else if(requiredCharacters.contains(a)){
                return -1;
            }
            else if(requiredCharacters.contains(b)){
                return 1;
            }
            return a.compareStrength(b);
        }
    };

    private Comparator<WifeyCharacter> magicComp = new Comparator<WifeyCharacter>() {
        @Override
        public int compare(WifeyCharacter a, WifeyCharacter b) {
            if(requiredCharacters.contains(a) && requiredCharacters.contains(b)){
                return a.compareMagic(b);
            }
            else if(requiredCharacters.contains(a)){
                return -1;
            }
            else if(requiredCharacters.contains(b)){
                return 1;
            }
            return a.compareMagic(b);
        }
    };

    private Comparator<WifeyCharacter> favComp = new Comparator<WifeyCharacter>(){
        @Override
        public int compare(WifeyCharacter a, WifeyCharacter b){
            if(requiredCharacters.contains(a) && requiredCharacters.contains(b)){
                return a.compareFavorite(b);
            }
            else if(requiredCharacters.contains(a)){
                return -1;
            }
            else if(requiredCharacters.contains(b)){
                return 1;
            }
            return a.compareFavorite(b);
        }
    };

    private enum SortMethod {
        ALPHA {
            @Override
            protected String getSortTitle() {
                return ALPHA_SORT_STRING;
            }
        },
        STRENGTH {
            @Override
            protected String getSortTitle() {
                return STR_SORT_STRING;
            }
        },
        MAGIC {
            @Override
            protected String getSortTitle() {
                return MAG_SORT_STRING;
            }
        },
        FAVORITE {
            @Override
            protected String getSortTitle() {
                return FAV_SORT_STRING;
            }
        };

        protected abstract String getSortTitle();
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
            case FAVORITE:
                return favComp;
            default:
                return nameComp;
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

        sortingPaint = new Paint();
        sortingPaint.setTextSize(30);
        sortingPaint.setColor(Color.BLACK);
        sortingPaint.setTextAlign(Align.CENTER);

        sortingList = new ArrayList<>();
        sortingList.add(ALPHA_SORT_STRING);
        sortingList.add(STR_SORT_STRING);
        sortingList.add(MAG_SORT_STRING);
        sortingList.add(FAV_SORT_STRING);

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

        sortDropdown = new DropdownMenu(SORT_BUTTON_LEFT_X, SORT_BUTTON_RIGHT_X, SORT_BUTTON_TOP_Y, SORT_BUTTON_BOT_Y, Assets.DropdownMenuTop, Assets.DropdownMenuOption, sortingPaint, sortingList);
        sortDropdown.setTitle(currentSort.getSortTitle());

        //Back button does not have an image associated with it
        backButton = new Button(BACK_BUTTON_LEFT_X, BACK_BUTTON_RIGHT_X, BACK_BUTTON_TOP_Y, BACK_BUTTON_BOT_Y, true, BACK_BUTTON_STRING);
        basicButtonList.addButton(backButton);
        acceptButton = new Button(ACCEPT_BUTTON_LEFT_X, ACCEPT_BUTTON_RIGHT_X, ACCEPT_BUTTON_TOP_Y, ACCEPT_BUTTON_BOT_Y, false, ACCEPT_BUTTON_STRING, Assets.AcceptEnable, Assets.AcceptDisable);
        basicButtonList.addButton(acceptButton);

        prevPartyButton = new Button(PREV_PARTY_BUTTON_LEFT_X, PREV_PARTY_BUTTON_RIGHT_X, PARTY_BUTTON_TOP_Y, PARTY_BUTTON_BOT_Y, true, PREV_BUTTON_STRING);
        basicButtonList.addButton(prevPartyButton);
        nextPartyButton = new Button(NEXT_PARTY_BUTTON_LEFT_X, NEXT_PARTY_BUTTON_RIGHT_X, PARTY_BUTTON_TOP_Y, PARTY_BUTTON_BOT_Y, true, NEXT_PARTY_BUTTON_STRING);
        basicButtonList.addButton(nextPartyButton);

        partyList = new ButtonList();
        for(int i = 0; i < 7; i++){
            int leftX;
            int rightX;
            int topY;
            int botY;
            if(i < PARTY_NUM_TOP_ROW) {
                leftX = PARTY_IMAGE_BASE_ROW_1_LEFT_X + PARTY_IMAGE_OFFSET * i;
                rightX = PARTY_IMAGE_BASE_ROW_1_RIGHT_X + PARTY_IMAGE_OFFSET * i;
                topY = PARTY_IMAGE_BASE_TOP_Y;
                botY = PARTY_IMAGE_BASE_BOT_Y;
            }
            else {
                leftX = PARTY_IMAGE_BASE_ROW_2_LEFT_X + PARTY_IMAGE_OFFSET * (i - PARTY_NUM_TOP_ROW);
                rightX = PARTY_IMAGE_BASE_ROW_2_RIGHT_X + PARTY_IMAGE_OFFSET * (i - PARTY_NUM_TOP_ROW);
                topY = PARTY_IMAGE_BASE_TOP_Y + PARTY_IMAGE_OFFSET;
                botY = PARTY_IMAGE_BASE_BOT_Y + PARTY_IMAGE_OFFSET;
            }
            partyList.addButton(new Button(leftX, rightX, topY, botY, false, "PARTY_" + i));
        }

        recruitList = new ButtonList();
        for(int i = 0; i < PER_PAGE; i++){
            int leftX = CHAR_IMAGE_BASE_LEFT_X + CHAR_IMAGE_OFFSET_X * (i % COLUMN_SIZE);
            int rightX = CHAR_IMAGE_BASE_RIGHT_X + CHAR_IMAGE_OFFSET_X * (i % COLUMN_SIZE);
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
            if(battleInfo == null || battleInfo.allowCharacter(inputCharacters.get(i))){
                validCharacters.add(inputCharacters.get(i));
            }
        }
        requiredCharacters = new ArrayList<>();
        if(battleInfo != null){
            requiredCharacters = battleInfo.getRequiredList();
            for(int i = 0; i < requiredCharacters.size(); i++){
                if(!validCharacters.contains(requiredCharacters.get(i))){
                    //Add it to the list of characters, but special handling will be done so it can't be added to party
                    validCharacters.add(requiredCharacters.get(i));
                }
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

    private void updatePartyNum() {
        //For now do nothing, but this will be updated in a bit
    }

    private void setPreviousScreen(Screen previousScreen){
        this.previousScreen = previousScreen;
    }

    //Gets the index of character in the full list
    //that is presently being touched
    private int getRecruitIndex(int x, int y){
        int index = recruitList.getIndexPressed(x, y);
        //If the character in question has not been recruited, they can't be dragged
        if(index == -1 || !validCharacters.get(currentPage * PER_PAGE + index).isRecruited()){
            return -1;
        }
        else{
            return currentPage * PER_PAGE + index;
        }
    }

    private void performSort(){
        ArrayList<WifeyCharacter> preSort = new ArrayList<>();
        for(int i = 0; i < PER_PAGE && ((currentPage * PER_PAGE) + i) < validCharacters.size(); i++){
            preSort.add(validCharacters.get((currentPage * PER_PAGE) + i));
        }
        Collections.sort(validCharacters, getSort());
        boolean pageChanged = false;
        for(int i = 0; i < PER_PAGE && ((currentPage * PER_PAGE) + i) < validCharacters.size(); i++){
            if(preSort.get(i) != validCharacters.get((currentPage * PER_PAGE) + i)){
                pageChanged = true;
            }
        }
        if(pageChanged) {
            updateRecruitImages();
        }
        sortDropdown.setTitle(currentSort.getSortTitle());
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
                            case FAV_SORT_STRING:
                                currentSort = SortMethod.FAVORITE;
                        }
                        if(result != null){
                            performSort();
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
                        //PREV PARTY BUTTON PRESSED
                        else if(lastPressed == prevPartyButton) {
                            //Modulus does not work for negative numbers
                            partyNum = (partyNum == 0) ? MAX_PARTY_NUM - 1 : partyNum - 1;
                            updatePartyNum();
                        }
                        //NEXT PARTY BUTTON PRESSED
                        else if (lastPressed == nextPartyButton) {
                            partyNum = (partyNum + 1) % MAX_PARTY_NUM;
                            updatePartyNum();
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

        NumberPrinter.drawNumber(g, (partyNum + 1), PARTY_NUM_CENTER_X, PARTY_NUM_TOP_Y, PARTY_NUM_WIDTH, PARTY_NUM_HEIGHT, PARTY_NUM_SPACING, Assets.WhiteNumbers, NumberPrinter.Align.CENTER);

        int displayPage = currentPage + 1;
        int displayMaxPage = maxPage + 1;
        NumberPrinter.drawNumber(g, displayPage, CUR_PAGE_X, PAGE_Y, PAGE_WIDTH, PAGE_HEIGHT, 0, Assets.WhiteNumbers, NumberPrinter.Align.RIGHT);
        NumberPrinter.drawNumber(g, displayMaxPage, MAX_PAGE_X, PAGE_Y, PAGE_WIDTH, PAGE_HEIGHT, 0, Assets.WhiteNumbers, NumberPrinter.Align.LEFT);


        basicButtonList.drawImage(g);
        for(int i = 0; i < currentParty.size(); i++){
            if(!dragging || i != draggingPartyIndex) {
                int x = (i < PARTY_NUM_TOP_ROW) ?  PARTY_IMAGE_OFFSET * i + PARTY_IMAGE_BASE_ROW_1_LEFT_X : PARTY_IMAGE_OFFSET * (i - PARTY_NUM_TOP_ROW) + PARTY_IMAGE_BASE_ROW_2_LEFT_X;
                int y = (i < PARTY_NUM_TOP_ROW) ? PARTY_IMAGE_BASE_TOP_Y : PARTY_IMAGE_BASE_TOP_Y + PARTY_IMAGE_OFFSET;
                g.drawPercentageImage(partyImages.get(i), x, y, PARTY_SCALE, PARTY_SCALE);
                if(currentParty.get(i).isFavorite()) {
                    g.drawScaledImage(Assets.Favorite,  x, y, CHAR_FAVORITE_SIZE, CHAR_FAVORITE_SIZE);
                }
                if(battleInfo != null && !battleInfo.allowCharacter(currentParty.get(i))){
                    g.drawPercentageImage(Assets.InvalidChar, x, y, PARTY_SCALE, PARTY_SCALE);
                }
            }
        }
        for(int i = maxPartySize; i < 7; i++){
            int x = (i < PARTY_NUM_TOP_ROW) ?  PARTY_IMAGE_OFFSET * i + PARTY_IMAGE_BASE_ROW_1_LEFT_X : PARTY_IMAGE_OFFSET * (i - PARTY_NUM_TOP_ROW) + PARTY_IMAGE_BASE_ROW_2_LEFT_X;
            int y = (i < PARTY_NUM_TOP_ROW) ? PARTY_IMAGE_BASE_TOP_Y : PARTY_IMAGE_BASE_TOP_Y + PARTY_IMAGE_OFFSET;
            g.drawImage(Assets.LockSelection, x, y);
        }

        //If the dropdown is not activated, draw it on a lower layer than the characters
        if(!sortDropdown.isMenuActive()) {
            sortDropdown.drawImage(g);
        }

        int minIndex = PER_PAGE * currentPage;
        int maxIndex = PER_PAGE * (currentPage + 1);
        for(int i = minIndex; i < validCharacters.size() && i < maxIndex; i++){
            if(requiredCharacters.contains(validCharacters.get(i))){
                //Since this can only be on row 1, the Y doesn't calculate offset.
                g.drawImage(Assets.RequiredCharHolder, (i % COLUMN_SIZE) * CHAR_REQUIRED_OFFSET + CHAR_REQUIRED_HOLDER_BASE_X, CHAR_REQUIRED_HOLDER_BASE_Y);
            }
            if(!dragging || i != draggingRecruitIndex) {
                g.drawPercentageImage(recruitImages.get(i - minIndex),
                        CHAR_IMAGE_OFFSET_X * (i % COLUMN_SIZE) + CHAR_IMAGE_BASE_LEFT_X,
                        CHAR_IMAGE_BASE_TOP_Y + CHAR_IMAGE_OFFSET_Y * ((i % PER_PAGE) / COLUMN_SIZE),
                        HALF_SCALE, HALF_SCALE);
                if(validCharacters.get(i).isFavorite()) {
                    g.drawScaledImage(Assets.Favorite,
                            CHAR_IMAGE_OFFSET_X * (i % COLUMN_SIZE) + CHAR_IMAGE_BASE_LEFT_X,
                            CHAR_IMAGE_BASE_TOP_Y + CHAR_IMAGE_OFFSET_Y * ((i % PER_PAGE) / COLUMN_SIZE),
                            CHAR_FAVORITE_SIZE, CHAR_FAVORITE_SIZE);
                }
            }
            if(!validCharacters.get(i).isRecruited()){
                g.drawPercentageImage(Assets.InvalidChar,
                        CHAR_IMAGE_OFFSET_X * (i % COLUMN_SIZE) + CHAR_IMAGE_BASE_LEFT_X,
                        CHAR_IMAGE_BASE_TOP_Y + CHAR_IMAGE_OFFSET_Y * ((i % PER_PAGE) / COLUMN_SIZE), HALF_SCALE, HALF_SCALE);
            }
        }
        if(dragging && draggingRecruitIndex != -1){
            g.drawPercentageImage(recruitImages.get(draggingRecruitIndex - minIndex), draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, DRAGGING_SCALE, DRAGGING_SCALE);
            if(validCharacters.get(draggingRecruitIndex).isFavorite()){
                g.drawScaledImage(Assets.Favorite, draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, CHAR_FAVORITE_SIZE, CHAR_FAVORITE_SIZE);
            }
        }
        if(dragging && draggingPartyIndex != -1){
            g.drawPercentageImage(partyImages.get(draggingPartyIndex), draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, DRAGGING_SCALE, DRAGGING_SCALE);
            if(currentParty.get(draggingPartyIndex).isFavorite()){
                g.drawScaledImage(Assets.Favorite, draggingX - DRAGGING_OFFSET, draggingY - DRAGGING_OFFSET, CHAR_FAVORITE_SIZE, CHAR_FAVORITE_SIZE);
            }
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
        performSort();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        game.setScreen(previousScreen);
    }
}
