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
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.WifeyCharacter;

/**
 * Created by John on 5/6/2016.
 */
public class PartySelectScreen extends Screen {

    private List<WifeyCharacter> validCharacters;
    private WifeyCharacter[] currentParty;
    private Enemy[] enemies;

    private Screen previousScreen;

    private Paint textPaint;

    public PartySelectScreen(Game game){
        super(game);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setTextSize(25);
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
            if(t.x >= 100 && t.x <= 200 && t.y >= 1200 && t.y <= 1250){
                backButton();
            }
            if(t.x >= 600 && t.x <= 700 && t.y >= 1200 && t.y <= 1250){
                BattleScreen bs = new BattleScreen(game);
                BattleCharacter[] party = new BattleCharacter[currentParty.length];
                for(int j = 0; j < party.length; j++){
                    party[j] = currentParty[j].getBattleCharacter();
                }
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
        for(int i = 0; i < currentParty.length; i++){
            g.drawPercentageImage(currentParty[i].getImage(), 100 * i + 60, 200, 50, 50);
        }
        for(int i = 0; i < validCharacters.size(); i++){
            g.drawPercentageImage(validCharacters.get(i).getImage(), 90 * (i % 8) + 40, 400 + 90 *(i / 8), 50, 50);
        }

        //Temporary back button
        g.drawRect(100, 1200, 100, 50, Color.DKGRAY);
        g.drawString("Back", 150, 1235, textPaint);
        //Temporary Battle Button
        g.drawRect(600, 1200, 100, 50, Color.DKGRAY);
        g.drawString("BATTLE!", 650, 1235, textPaint);

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
