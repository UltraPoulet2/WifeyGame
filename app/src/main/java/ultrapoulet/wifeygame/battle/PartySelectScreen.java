package ultrapoulet.wifeygame.battle;

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
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 5/6/2016.
 */
public class PartySelectScreen extends Screen {

    private List<WifeyCharacter> validCharacters;
    private WifeyCharacter[] currentParty;
    private Enemy[] enemies;

    private Screen previousScreen;

    private Paint textPaint;

    private Image background;

    private int currentPage = 1;
    private int maxPage;
    private final static int PER_PAGE = 64;

    private final static int PARTY_SCALE = 57;
    private final static int DRAGGING_SCALE = 75;

    private Image[] numbers;

    private boolean touchDown = false;
    private final static int DRAG_WAIT = 25;
    private float holdTime = 0;
    private boolean dragging = false;
    private int draggingX = 0;
    private int draggingY = 0;
    private int draggingIndex = -1;

    public PartySelectScreen(Game game){
        super(game);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setTextSize(25);

        background = Assets.PartySelectScreen;

        numbers = Assets.HPNumbers;

        currentParty = new WifeyCharacter[7];
        for(int i = 0; i < currentParty.length; i++){
            currentParty[i] = Party.getIndex(i);
        }
        setValidCharacters(RecruitedCharacters.getArray());
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
        this.currentParty = new WifeyCharacter[7];
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
    private int getDraggingIndex(int x, int y){
        for(int i = PER_PAGE * (currentPage - 1); i < PER_PAGE * currentPage && i < validCharacters.size(); i++){
            int xLeft = 45 + 90 * (i % 8);
            int xRight = 125 + 90 * (i % 8);
            int yTop = 400 + 90 * (i / 8);
            int yBot = 480 + 90 * (i / 8);
            if(x >= xLeft && x <= xRight && y >= yTop && y <= yBot){
                return i;
            }
        }
        return -1;
    }

    private int getPartyIndex(int x, int y){
        for(int i = 0; i < 7; i++){
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

    @Override
    public void update(float deltaTime){
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if (t.type == TouchEvent.TOUCH_DOWN){
                touchDown = true;
                dragging = false;
                holdTime = 0;
                draggingIndex = getDraggingIndex(t.x, t.y);
                continue;
            }
            else if (t.type == TouchEvent.TOUCH_UP) {
                if(!dragging){
                    if(t.x >= 45 && t.x <= 215 && t.y >= 1150 && t.y <= 1250){
                        backButton();
                    }
                    else if(t.x >= 585 && t.x <= 755 && t.y >= 1150 && t.y <= 1250){
                        BattleScreen bs = new BattleScreen(game);
                        BattleCharacter[] party = new BattleCharacter[currentParty.length];
                        for(int j = 0; j < party.length; j++){
                            party[j] = currentParty[j].getBattleCharacter();
                        }
                        Party.setParty(currentParty);
                        bs.setParty(party);
                        bs.setEnemies(enemies);
                        bs.setBackground(Assets.testBG);
                        game.setScreen(bs);
                    }
                    else if(draggingIndex != -1){
                        System.out.println("I would display info on: " + validCharacters.get(draggingIndex).getName());
                    }
                }
                else if(dragging){
                    int partyIndex = getPartyIndex(t.x, t.y);
                    if(partyIndex != -1){
                        boolean inParty = false;
                        for(int j = 0; j < currentParty.length; j++){
                            if(currentParty[j] == validCharacters.get(draggingIndex)){
                                inParty = true;
                            }
                        }
                        //For now, if already in party, ignore
                        if(inParty){
                            System.out.println("Character already in party");
                        }
                        else{
                            currentParty[partyIndex] = validCharacters.get(draggingIndex);
                        }
                    }
                }
                touchDown = false;
                dragging = false;
                holdTime = 0;
                draggingIndex = -1;
            }
            else if(t.type == TouchEvent.TOUCH_DRAGGED) {
                if(!dragging && draggingIndex != -1 && getDraggingIndex(t.x, t.y) != draggingIndex){
                    holdTime = 0;
                    draggingIndex = -1;
                }
                else{
                    draggingX = t.x;
                    draggingY = t.y;
                }
            }
        }
        if(game.getInput().isTouchDown(0) && draggingIndex != -1){
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
        g.drawARGB(0xFF, 0x77, 0x77, 0x77);
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

        for(int i = 0; i < currentParty.length; i++){
            g.drawPercentageImage(currentParty[i].getImage(), 100 * i + 60, 180, PARTY_SCALE, PARTY_SCALE);
        }
        for(int i = 64 * (currentPage - 1); i < validCharacters.size(); i++){
            if(!dragging || i != draggingIndex) {
                g.drawPercentageImage(validCharacters.get(i).getImage(), 90 * (i % 8) + 45, 400 + 90 * (i / 8), 50, 50);
            }
        }
        if(dragging){
            g.drawPercentageImage(validCharacters.get(draggingIndex).getImage(), draggingX - 40, draggingY - 40, DRAGGING_SCALE, DRAGGING_SCALE);
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
