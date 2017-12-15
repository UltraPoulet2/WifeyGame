package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.BattleAssets;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.character.Element;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.screens.dialogs.AdjustableTextInfoDialog;

/**
 * Created by John on 10/30/2017.
 */

public class BattleLoadingScreen extends Screen {

    private static final int STATUS_X = 400;
    private static final int STATUS_Y = 1000;
    private Paint statusPaint;
    private boolean pressDown = false;

    private boolean error = false;
    private String errorString = "";
    private boolean displayedError = false;

    private enum LoadingPhase {
        CREATE_ANIMATIONS{
            @Override
            protected String getStatus() {
                return "Creating Animations";
            }
        },
        CREATE_IMAGES{
            @Override
            protected String getStatus() { return "Creating Images"; }
        },
        COMPLETE{
            @Override
            protected String getStatus() {
                return "Tap to Start Battle";
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

    private LoadingPhase currentPhase = LoadingPhase.CREATE_ANIMATIONS;
    private Screen nextScreen;
    private List<Weapon> loadingWeapons;
    private List<Element> loadingElements;

    public BattleLoadingScreen(Game game, BattleInfo battleInfo, AbsBattleScreen nextScreen){
        super(game);
        this.nextScreen = nextScreen;

        List<WifeyCharacter> party = Party.getCurrentParty(battleInfo.getPartyMax());
        List<EnemyCharacter> enemies = battleInfo.getCharacterEnemies();
        loadingWeapons = new ArrayList<>();
        loadingElements = new ArrayList<>();

        for(WifeyCharacter wifey : party){
            if(wifey != null) {
                loadingWeapons.add(wifey.getWeapon());
                loadingElements.add(wifey.getAttackElement());
            }
        }
        for(EnemyCharacter enemy : enemies) {
            loadingWeapons.add(enemy.getWeapon());
            loadingElements.add(enemy.getAttackElement());
        }

        statusPaint = new Paint();
        statusPaint.setTextAlign(Paint.Align.CENTER);
        statusPaint.setTextSize(40);
        statusPaint.setColor(Color.WHITE);
    }

    @Override
    public void update(float deltaTime) {
        switch(currentPhase) {
            case CREATE_ANIMATIONS:
                createAnimations();
                if(error) {
                    currentPhase = LoadingPhase.ERROR;
                }
                else {
                    currentPhase = LoadingPhase.CREATE_IMAGES;
                }
                break;
            case CREATE_IMAGES:
                createImages();
                if(error) {
                    currentPhase = LoadingPhase.ERROR;
                }
                else {
                    currentPhase = LoadingPhase.COMPLETE;
                }
            case COMPLETE:
                List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

                for (int i = 0; i < touchEvents.size(); i++) {
                    TouchEvent t = touchEvents.get(i);
                    if(t.type == TouchEvent.TOUCH_DOWN){
                        pressDown = true;
                    }
                    else if(t.type == TouchEvent.TOUCH_UP && pressDown) {
                        game.setScreen(nextScreen);
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

    private void createAnimations() {
        Graphics g = game.getGraphics();
        List<String> errorList = new ArrayList<>();
        for(Weapon weapon : loadingWeapons) {
            try {
                weapon.loadAnimation(g);
            }
            catch (Exception e) {
                error = true;
                errorList.add(weapon.getWeaponType());
            }
        }
        for(Element element : loadingElements) {
            try {
                element.loadAnimation(g);
            }
            catch (Exception e) {
                error = true;
                errorList.add(element.getElementType());
            }
        }
        if(error){
            StringBuilder builder = new StringBuilder();
            builder.append("Could not load the following animations:");
            for(String errorFile : errorList){
                builder.append("\n" + errorFile);
            }
            errorString = builder.toString();
        }

    }

    private void createImages() {
        Graphics g = game.getGraphics();
        try {
            BattleAssets.load(g);
        }
        catch (Exception e) {
            error = true;
            errorString = "Could not load all battle images";
            Log.e("BattleLoading", e.getMessage());
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
