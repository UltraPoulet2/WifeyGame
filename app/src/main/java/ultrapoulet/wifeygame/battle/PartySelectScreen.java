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
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;

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

    private Image[] numbers;

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

    @Override
    public void update(float deltaTime){
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if (t.type != Input.TouchEvent.TOUCH_UP) {
                continue;
            }
            if(t.x >= 45 && t.x <= 215 && t.y >= 1150 && t.y <= 1250){
                backButton();
            }
            if(t.x >= 585 && t.x <= 755 && t.y >= 1150 && t.y <= 1250){
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
        for(int i = 0; i < validCharacters.size(); i++){
            g.drawPercentageImage(validCharacters.get(i).getImage(), 90 * (i % 8) + 45, 400 + 90 *(i / 8), 50, 50);
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
