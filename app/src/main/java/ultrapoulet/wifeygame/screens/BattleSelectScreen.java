package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.gamestate.Battles;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;

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
            if(t.y >= 160 && t.y <= 260){
                testInfo = Battles.get("TEST-BATL");
            }
            if(t.y >= 310 && t.y <= 410){
                testInfo = Battles.get("TEST-BTWO");
            }
            if(t.y >= 460 && t.y <= 560){
                testInfo = Battles.get("TEST-PHYS");
            }
            if(t.y >= 610 && t.y <= 710){
                testInfo = Battles.get("TEST-MAGI");
            }
            if(testInfo != null){
                BattleInfoScreen bis = new BattleInfoScreen(game, this, testInfo);
                game.setScreen(bis);
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawARGB(0xff, 0x99, 0x99, 0x99);
        PlayerInfo.drawHeader(g);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);
        g.drawString("Select Battle", 400, 130, paint);
        g.drawRect(100, 160, 600, 100, Color.DKGRAY);
        BattleInfo testInfo = Battles.get("TEST-BATL");
        g.drawString(testInfo.getName(), 400, 230, paint);

        g.drawRect(100, 310, 600, 100, Color.DKGRAY);
        BattleInfo test2 = Battles.get("TEST-BTWO");
        g.drawString(test2.getName(), 400, 380, paint);

        g.drawRect(100, 460, 600, 100, Color.DKGRAY);
        BattleInfo test3 = Battles.get("TEST-PHYS");
        g.drawString(test3.getName(), 400, 530, paint);

        g.drawRect(100, 610, 600, 100, Color.DKGRAY);
        BattleInfo test4 = Battles.get("TEST-MAGI");
        g.drawString(test4.getName(), 400, 680, paint);
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
