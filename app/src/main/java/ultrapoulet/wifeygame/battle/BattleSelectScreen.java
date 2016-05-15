package ultrapoulet.wifeygame.battle;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.WifeyCharacter;
import ultrapoulet.wifeygame.battle.enemyai.BasicMagicEnemyAI;
import ultrapoulet.wifeygame.battle.enemyai.BasicPhysicalEnemyAI;
import ultrapoulet.wifeygame.battle.enemyai.OriginalBossAI;
import ultrapoulet.wifeygame.gamestate.Party;

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
                Enemy[] enemies = new Enemy[3];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());
                enemies[1] = new Enemy("Enemy 2", 20000, 0, 0, 0, 0, 50, 1000, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicMagicEnemyAI());
                enemies[2] = new Enemy("Enemy 3", 50000, 20, 4, 0, 0, 0, 0, 2.0, 0.0, 0.0, 1.5, 50, 3, Assets.testEnemy, new OriginalBossAI());

                PartySelectScreen pss = new PartySelectScreen(game);
                pss.setEnemies(enemies);
                pss.setPreviousScreen(this);
                game.setScreen(pss);
            }

            if(t.y >= 250 && t.y <= 350){

            }

            if(t.y >= 400 && t.y <= 500){
                Enemy[] enemies = new Enemy[3];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());
                enemies[1] = new Enemy("Enemy 2", 20000, 0, 0, 0, 0, 50, 1000, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicMagicEnemyAI());
                enemies[2] = new Enemy("Enemy 3", 50000, 20, 4, 0, 0, 0, 0, 2.0, 0.0, 0.0, 1.5, 50, 3, Assets.testEnemy, new OriginalBossAI());

                PartySelectScreen pss = new PartySelectScreen(game);
                pss.setEnemies(enemies);
                pss.setPreviousScreen(this);
                game.setScreen(pss);
            }

            if(t.y >= 550 && t.y <= 700){
                Enemy[] enemies = new Enemy[3];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());
                enemies[1] = new Enemy("Enemy 2", 20000, 0, 0, 0, 0, 50, 1000, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicMagicEnemyAI());
                enemies[2] = new Enemy("Enemy 3", 50000, 20, 4, 0, 0, 0, 0, 2.0, 0.0, 0.0, 1.5, 50, 3, Assets.testEnemy, new OriginalBossAI());

                BattleScreen bs = new BattleScreen(game);
                bs.setEnemies(enemies);
                bs.setBackground(Assets.testBG);
                game.setScreen(bs);
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
        g.drawString("Initial Battle 1", 400, 170, paint);

        g.drawRect(100, 250, 600, 100, Color.DKGRAY);
        g.drawString("All Yunos!?", 400, 320, paint);

        g.drawRect(100, 400, 600, 100, Color.DKGRAY);
        g.drawString("Newbies 1", 400, 470, paint);

        g.drawRect(100, 550, 600, 100, Color.DKGRAY);
        g.drawString("Newbies 2", 400, 620, paint);
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
