package ultrapoulet.wifeygame.screens;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Characters;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;
import ultrapoulet.wifeygame.gamestate.StoryBattles;
import ultrapoulet.wifeygame.parsers.BattleParser;
import ultrapoulet.wifeygame.parsers.CharacterParser;
import ultrapoulet.wifeygame.parsers.EnemyParser;
import ultrapoulet.wifeygame.parsers.RecruitingBattleParser;
import ultrapoulet.wifeygame.parsers.RecruitingParser;
import ultrapoulet.wifeygame.screens.dialogs.AdjustableTextInfoDialog;

/**
 * Created by John on 3/12/2016.
 */
public class LoadingScreen extends Screen {

    private static final int STATUS_X = 400;
    private static final int STATUS_Y = 1000;
    private Paint statusPaint;
    private boolean pressDown = false;

    private boolean error = false;
    private String errorString = "";
    private boolean displayedError = false;

    public LoadingScreen(Game game){
        super(game);

        statusPaint = new Paint();
        statusPaint.setTextAlign(Paint.Align.CENTER);
        statusPaint.setTextSize(40);
        statusPaint.setColor(Color.WHITE);
    }

    private enum LoadingPhase {
        CREATE_IMAGES{
            @Override
            protected String getStatus() {
                return "Creating Images";
            }
        },
        CREATE_RECRUITS{
            @Override
            protected String getStatus() {
                return "Creating Wifeys";
            }
        },
        CREATE_ENEMIES{
            @Override
            protected String getStatus() {
               return "Creating Enemies";
            }
        },
        CREATE_BATTLES{
            @Override
            protected String getStatus() {
                return "Creating Battles";
            }
        },
        CREATE_RECRUITING{
            @Override
            protected String getStatus() {
                return "Creating Recruiting Info";
            }
        },
        LOAD_SAVE{
            @Override
            protected String getStatus() {
                return "Loading Save";
            }
        },
        CREATE_PARTY{
            @Override
            protected String getStatus() {
                return "Creating Current Party";
            }
        },
        COMPLETE{
            @Override
            protected String getStatus() {
                return "Tap to Start";
            }
        },
        ERROR{
            @Override
            protected String getStatus() {
                return "Error Encountered";
            }
        };
        protected abstract String getStatus();
    }

    private LoadingPhase currentPhase = LoadingPhase.CREATE_IMAGES;

    @Override
    public void update(float deltaTime) {
        switch(currentPhase){
            case CREATE_IMAGES:
                createImages();
                if(error) {
                    currentPhase = LoadingPhase.ERROR;
                }
                else {
                    currentPhase = LoadingPhase.CREATE_RECRUITS;
                }
                break;
            case CREATE_RECRUITS:
                createRecruits();
                if(error) {
                    currentPhase = LoadingPhase.ERROR;
                }
                else {
                    currentPhase = LoadingPhase.CREATE_ENEMIES;
                }
                break;
            case CREATE_ENEMIES:
                createEnemies();
                if(error) {
                    currentPhase = LoadingPhase.ERROR;
                }
                else {
                    currentPhase = LoadingPhase.CREATE_BATTLES;
                }
                break;
            case CREATE_BATTLES:
                createBattles();
                if(error) {
                    currentPhase = LoadingPhase.ERROR;
                }
                else {
                    currentPhase = LoadingPhase.CREATE_RECRUITING;
                }
                break;
            case CREATE_RECRUITING:
                createRecruiting();
                if(error) {
                    currentPhase = LoadingPhase.ERROR;
                }
                else {
                    currentPhase = LoadingPhase.LOAD_SAVE;
                }
                break;
            case LOAD_SAVE:
                loadSave();
                if(error) {
                    currentPhase = LoadingPhase.ERROR;
                }
                else {
                    currentPhase = LoadingPhase.CREATE_PARTY;
                }
                break;
            case CREATE_PARTY:
                createParty();
                if(error) {
                    currentPhase = LoadingPhase.ERROR;
                }
                else {
                    currentPhase = LoadingPhase.COMPLETE;
                }
                //Clear the touch input buffer
                game.getInput().getTouchEvents();
                break;
            case COMPLETE:
                List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

                for (int i = 0; i < touchEvents.size(); i++) {
                    TouchEvent t = touchEvents.get(i);
                    if(t.type == TouchEvent.TOUCH_DOWN){
                        pressDown = true;
                    }
                    else if(t.type == TouchEvent.TOUCH_UP && pressDown) {
                        BattleSelectScreen bss = new BattleSelectScreen(game);
                        game.setScreen(bss);
                    }
                }
                break;
            case ERROR:
                if(!displayedError) {
                    Screen infoScreen = new AdjustableTextInfoDialog(game, this, errorString);
                    game.setScreen(infoScreen);
                    displayedError = true;
                }
        }
    }

    private void createImages(){
        Graphics g = game.getGraphics();

        //Setup general images
        Assets.load(g);

        //Setup the images for Weapons
        Weapon.setupImages(g);
    }

    private void createRecruits(){
        InputStream in = null;
        try {
            in = game.openConfig("config/characters.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            CharacterParser charParser = new CharacterParser();
            saxParser.parse(in, charParser);
            error = charParser.getNumberErrors() > 0;
            if(error){
                errorString = charParser.getErrorString();
                Log.e("LoadingScreen", "Recruiting Parsing incomplete. Number errors: " + charParser.getNumberErrors());
            }
            else {
                Log.i("LoadingScreen", "Recruiting Parsing complete.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            //Do better error handling
        }
        finally{
            if(in != null){
                try {
                    in.close();
                }
                catch(IOException e){
                }
            }
        }
    }

    private void createParty(){
        SharedPreferences prefs = game.getGamePreferences("ultrapoulet.wifeygame.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        Party.init(prefs);

        //This will get cleaned up later
        ArrayList<WifeyCharacter> party = new ArrayList<>();
        party.add(RecruitedCharacters.get(prefs.getString("party_0", "TEST-YUNO")));
        party.add(RecruitedCharacters.get(prefs.getString("party_1", "TEST-RENA")));
        party.add(RecruitedCharacters.get(prefs.getString("party_2", "TEST-KTNH")));
        party.add(RecruitedCharacters.get(prefs.getString("party_3", "TEST-ANNA")));
        party.add(RecruitedCharacters.get(prefs.getString("party_4", "TEST-SJGH")));
        party.add(RecruitedCharacters.get(prefs.getString("party_5", "TEST-YNDR")));
        party.add(RecruitedCharacters.get(prefs.getString("party_6", "TEST-PERI")));

        Party.setParty(party);
    }

    private void createEnemies(){
        InputStream in = null;
        try {
            in = game.openConfig("config/enemies.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            EnemyParser enemyParser = new EnemyParser();
            saxParser.parse(in, enemyParser);
            error = enemyParser.getNumberErrors() > 0;
            if(error) {
                errorString = enemyParser.getErrorString();
                Log.e("LoadingScreen", "Enemy Parsing incomplete. Number errors: " + enemyParser.getNumberErrors());
            }
            else {
                Log.i("LoadingScreen", "Enemy Parsing complete.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            //Do better error handling
        }
        finally{
            if(in != null){
                try {
                    in.close();
                }
                catch(IOException e){
                }
            }
        }
    }

    private void createBattles(){
        InputStream in = null;
        try {
            in = game.openConfig("config/battles.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            BattleParser battleParser = new BattleParser();
            saxParser.parse(in, battleParser);
            error = battleParser.getNumberErrors() > 0;
            if(error){
                Log.e("LoadingScreen", "Battle Parsing incomplete. Number errors: " + battleParser.getNumberErrors());
                errorString = battleParser.getErrorString();
                return;
            }
            else {
                Log.i("LoadingScreen", "Battle Parsing complete.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            //Do better error handling
        }
        finally{
            if(in != null){
                try {
                    in.close();
                }
                catch(IOException e){
                }
            }
        }
        StoryBattles.validateUnlocks();

        try {
            in = game.openConfig("config/recruitbattles.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            RecruitingBattleParser battleParser = new RecruitingBattleParser();
            saxParser.parse(in, battleParser);
            error = battleParser.getNumberErrors() > 0;
            if(error) {
                errorString = battleParser.getErrorString();
                Log.e("LoadingScreen", "Recruiting Battle Parsing incomplete. Number errors: " + battleParser.getNumberErrors());
                return;
            }
            else{
                Log.i("LoadingScreen", "Recruiting Battle Parsing complete.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            //Do better error handling
        }
        finally{
            if(in != null){
                try {
                    in.close();
                }
                catch(IOException e){
                }
            }
        }
    }

    private void createRecruiting(){
        InputStream in = null;
        try {
            in = game.openConfig("config/recruiting.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            RecruitingParser recParser = new RecruitingParser();
            saxParser.parse(in, recParser);
            error = recParser.getNumberErrors() > 0;
            if(error) {
                errorString = recParser.getErrorString();
                Log.e("LoadingScreen", "Recruiting Parsing incomplete. Number errors: " + recParser.getNumberErrors());
            }
            else {
                Log.i("LoadingScreen", "Recruiting Parsing complete.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (in != null){
                try {
                    in.close();
                }
                catch(IOException e){
                }
            }
        }
    }

    private void loadSave(){
        SharedPreferences prefs = game.getGamePreferences("ultrapoulet.wifeygame.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        PlayerInfo.init(prefs);
        long nextEnergy = prefs.getLong("next_energy", 0);
        int currentEnergy = prefs.getInt("current_energy", 0);
        PlayerInfo.setCurrentEnergy(currentEnergy);
        PlayerInfo.setEnergyTimers(nextEnergy);

        //Load all recruited characters
        for(String key : Characters.getKeys()){
            //For Required character testing, temporarily remove Nyaruko
            if(key.equals("TEST-NYRK") || key.equals("TEST-PERI")){
                continue;
            }
            //System.out.println("Character recruited: " + key);
            Log.i("LoadingScreen", "Wifey recruited: " + key);
            Characters.get(key).recruit();
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        g.clearScreen(Color.BLACK);
        g.drawString(currentPhase.getStatus(), STATUS_X, STATUS_Y, statusPaint);
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
