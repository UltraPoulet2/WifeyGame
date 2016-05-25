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
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.WifeyCharacter;
import ultrapoulet.wifeygame.battle.BattleScreen;
import ultrapoulet.wifeygame.battle.Enemy;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 5/6/2016.
 */
public class PartySelectScreen extends Screen {

    private List<WifeyCharacter> validCharacters;
    private WifeyCharacter[] currentParty;
    private int maxPartySize = 7;
    private int finalIndex = maxPartySize - 1;
    private Enemy[] enemies;

    private Screen previousScreen;

    private CharacterInfoScreen charInfo;

    private Paint textPaint;

    private Image background;

    private int currentPage = 1;
    private int maxPage;
    private final static int PER_PAGE = 64;

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

    private ButtonPressed lastPressed = null;

    public PartySelectScreen(Game game){
        super(game);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setTextSize(25);

        background = Assets.PartySelectScreen;

        numbers = Assets.HPNumbers;

        currentParty = new WifeyCharacter[maxPartySize];
        for(int i = 0; i < currentParty.length; i++){
            currentParty[i] = Party.getIndex(i);
        }
        setValidCharacters(RecruitedCharacters.getArray());

        charInfo = new CharacterInfoScreen(game);
        charInfo.setPreviousScreen(this);
    }

    public void setValidCharacters(WifeyCharacter[] inputCharacters){
        validCharacters = new ArrayList<WifeyCharacter>();
        for(int i = 0; i < inputCharacters.length; i++){
            //Do a check to make sure the character is valid for this battle
            validCharacters.add(inputCharacters[i]);
        }
        Comparator<WifeyCharacter> nameComp = new Comparator<WifeyCharacter>(){
            @Override
            public int compare(WifeyCharacter a, WifeyCharacter b){
                return a.compareName(b);
            }
        };
        Collections.sort(validCharacters, nameComp);
        maxPage = (validCharacters.size() / 64) + 1;
    }

    public void setCurrentParty(WifeyCharacter[] inputParty){
        //For now, only 7.
        this.currentParty = new WifeyCharacter[maxPartySize];
        //Maybe do validation here too
        this.currentParty = inputParty;
    }

    public void setEnemies(Enemy[] enemies){
        this.enemies = enemies;
    }

    public void setPreviousScreen(Screen previousScreen){
        this.previousScreen = previousScreen;
    }

    //Gets the index of character in the full list
    //that is presently being touched
    private int getRecruitIndex(int x, int y){
        for(int i = PER_PAGE * (currentPage - 1); i < PER_PAGE * currentPage && i < validCharacters.size(); i++){
            int xLeft = 45 + 90 * (i % 8);
            int xRight = 125 + 90 * (i % 8);
            int yTop = 400 + 90 * ((i % PER_PAGE) / 8);
            int yBot = 480 + 90 * ((i % PER_PAGE) / 8);
            if(x >= xLeft && x <= xRight && y >= yTop && y <= yBot){
                return i;
            }
        }
        return -1;
    }

    //Gets the index of the party member that is presently being touched
    private int getPartyIndex(int x, int y){
        for(int i = 0; i < maxPartySize; i++){
            int xLeft = 60 + 100 * i;
            int xRight = 150 + 100 * i;
            int yTop = 180;
            int yBot = 270;
            if(x >= xLeft && x <= xRight && y >= yTop && y <= yBot){
                return i;
            }
        }
        return -1;
    }

    private ButtonPressed getButtonPressed(int x, int y){
        if(x >= 45 && x <= 215 && y >= 1150 && y <= 1250){
            return ButtonPressed.BACK;
        }
        else if(x >= 585 && x <= 755 && y >= 1150 && y <= 1250) {
            return ButtonPressed.ACCEPT;
        }
        else if(x >= 45 && x <= 215 && y >= 338 && y <= 388) {
            return ButtonPressed.PREV_PAGE;
        }
        else if(x >= 315 && x <= 485 && y >= 338 && y <= 388) {
            return ButtonPressed.NEXT_PAGE;
        }
        else if(x >= 585 && x <= 755 && y >= 348 && y <= 378) {
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
                        BattleScreen bs = new BattleScreen(game);
                        Party.setParty(currentParty);
                        bs.setParty(Party.getBattleParty());
                        bs.setEnemies(enemies);
                        bs.setBackground(Assets.testBG);
                        game.setScreen(bs);
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
        //g.drawARGB(0xFF, 0x77, 0x77, 0x77);
        g.clearScreen(0xFFFFFFFF);
        g.drawImage(background, 0, 0);

        if(currentPage > 10){
            g.drawImage(numbers[currentPage / 10], 215, 355);
        }
        g.drawImage(numbers[currentPage % 10], 235, 355);

        if(maxPage > 10){
            g.drawImage(numbers[maxPage / 10], 265, 355);
            g.drawImage(numbers[maxPage % 10], 285, 355);
        }
        else {
            g.drawImage(numbers[maxPage % 10], 265, 355);
        }

        if(currentPage == 1){
            g.drawImage(Assets.PrevPageDisable, 45, 338);
        }
        else{
            g.drawImage(Assets.PrevPageEnable, 45, 338);
        }

        if(currentPage == maxPage){
            g.drawImage(Assets.NextPageDisable, 315, 338);
        }
        else{
            g.drawImage(Assets.NextPageEnable, 315, 338);
        }

        for(int i = 0; i < maxPartySize && currentParty[i] != null; i++){
            if(!dragging || i != draggingPartyIndex) {
                g.drawPercentageImage(currentParty[i].getImage(), 100 * i + 60, 180, PARTY_SCALE, PARTY_SCALE);
            }
        }
        for(int i = maxPartySize; i < 7; i++){
            g.drawImage(Assets.LockSelection, 100 * i + 60, 180);
        }
        int minIndex = PER_PAGE * (currentPage - 1);
        int maxIndex = PER_PAGE * currentPage;
        for(int i = minIndex; i < validCharacters.size() && i < maxIndex; i++){
            if(!dragging || i != draggingRecruitIndex) {
                g.drawPercentageImage(validCharacters.get(i).getImage(), 90 * (i % 8) + 45, 400 + 90 * ((i % PER_PAGE) / 8), 50, 50);
            }
        }
        if(dragging && draggingRecruitIndex != -1){
            g.drawPercentageImage(validCharacters.get(draggingRecruitIndex).getImage(), draggingX - 60, draggingY - 60, DRAGGING_SCALE, DRAGGING_SCALE);
        }
        if(dragging && draggingPartyIndex != -1){
            g.drawPercentageImage(currentParty[draggingPartyIndex].getImage(), draggingX - 60, draggingY - 60, DRAGGING_SCALE, DRAGGING_SCALE);
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
