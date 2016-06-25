package ultrapoulet.wifeygame.battle;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;
import java.util.Map;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.PartySelectScreen;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Battles;

/**
 * Created by John on 4/26/2016.
 */
public class BattleSelectScreen extends Screen {

    private Map<String, WifeyCharacter> recruitedCharacters;

    public BattleSelectScreen(Game game){
        super(game);
    }

    @Override
    public void update(float deltaTime){

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++){
            TouchEvent t = touchEvents.get(i);
            if(t.type != TouchEvent.TOUCH_UP){
                continue;
            }
            if(t.x < 100 || t.x > 700){
                continue;
            }
            if(t.y >= 100 && t.y <= 200){
                BattleInfo testInfo = Battles.get("TEST-BATL");

                PartySelectScreen pss = new PartySelectScreen(game);
                pss.setBattleInfo(testInfo);
                pss.setPreviousScreen(this);
                game.setScreen(pss);
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawARGB(0xff, 0x99, 0x99, 0x99);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);
        g.drawString("Select Battle", 400, 70, paint);
        g.drawRect(100, 100, 600, 100, Color.DKGRAY);
        BattleInfo testInfo = Battles.get("TEST-BATL");
        g.drawString(testInfo.getName(), 400, 170, paint);
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
