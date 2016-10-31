package ultrapoulet.wifeygame.battle;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.PartySelectScreen;
import ultrapoulet.wifeygame.gamestate.Battles;

/**
 * Created by John on 4/26/2016.
 */
public class BattleSelectScreen extends Screen {

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
            BattleInfo testInfo = null;
            if(t.y >= 100 && t.y <= 200){
                testInfo = Battles.get("TEST-BATL");
            }
            if(t.y >= 250 && t.y <= 350){
                testInfo = Battles.get("TEST-BTWO");
            }
            if(t.y >= 400 && t.y <= 500){
                testInfo = Battles.get("TEST-PHYS");
            }
            if(t.y >= 550 && t.y <= 650){
                testInfo = Battles.get("TEST-MAGI");
            }
            if(testInfo != null){
                /*
                PartySelectScreen pss = new PartySelectScreen(game);
                pss.setBattleInfo(testInfo);
                pss.setPreviousScreen(this);
                game.setScreen(pss);
                */
                BattleInfoScreen bis = new BattleInfoScreen(game);
                bis.setBattleInfo(testInfo);
                bis.setPreviousScreen(this);
                game.setScreen(bis);
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

        g.drawRect(100, 250, 600, 100, Color.DKGRAY);
        BattleInfo test2 = Battles.get("TEST-BTWO");
        g.drawString(test2.getName(), 400, 320, paint);

        g.drawRect(100, 400, 600, 100, Color.DKGRAY);
        BattleInfo test3 = Battles.get("TEST-PHYS");
        g.drawString(test3.getName(), 400, 470, paint);

        g.drawRect(100, 550, 600, 100, Color.DKGRAY);
        BattleInfo test4 = Battles.get("TEST-MAGI");
        g.drawString(test4.getName(), 400, 620, paint);
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
