package ultrapoulet.wifeygame.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
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

    private float waitTime = 2000;
    private float currentTime = 0;

    public PartySelectScreen(Game game){
        super(game);
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

    @Override
    public void update(float deltaTime){
        currentTime += deltaTime;
        if(currentTime >= waitTime){
            BattleScreen bs = new BattleScreen(game);
            BattleCharacter[] party = new BattleCharacter[currentParty.length];
            for(int i = 0; i < party.length; i++){
                party[i] = currentParty[i].getBattleCharacter();
            }
            bs.setParty(party);
            bs.setEnemies(enemies);
            bs.setBackground(Assets.testBG);
            game.setScreen(bs);
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
