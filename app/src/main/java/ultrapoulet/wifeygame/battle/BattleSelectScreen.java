package ultrapoulet.wifeygame.battle;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.enemyai.BasicMagicEnemyAI;
import ultrapoulet.wifeygame.battle.enemyai.BasicPhysicalEnemyAI;
import ultrapoulet.wifeygame.battle.enemyai.OriginalBossAI;

/**
 * Created by John on 4/26/2016.
 */
public class BattleSelectScreen extends Screen {

    private float waitTime = 0;

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
                BattleCharacter[] party = new BattleCharacter[7];
                party[0] = new BattleCharacter("Yuno", 3, 75, 50, Assets.testChar1);
                party[1] = new BattleCharacter("Rena", 3, 50, 75, Assets.testChar2);
                party[2] = new BattleCharacter("Kotonoha", 3, 100, 25, Assets.testChar3);
                party[3] = new BattleCharacter("Anna", 3, 25, 100, Assets.testChar4);
                party[4] = new BattleCharacter("Hitagi", 3, 60, 70, Assets.testChar5);
                party[5] = new BattleCharacter("Yandere-chan", 3, 70, 60, Assets.testChar6);
                party[6] = new BattleCharacter("Peri", 3, 10, 200, Assets.testChar7);

                Enemy[] enemies = new Enemy[3];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());
                enemies[1] = new Enemy("Enemy 2", 20000, 0, 0, 0, 0, 50, 1000, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicMagicEnemyAI());
                enemies[2] = new Enemy("Enemy 3", 50000, 20, 4, 0, 0, 0, 0, 2.0, 0.0, 0.0, 1.5, 50, 3, Assets.testEnemy, new OriginalBossAI());

                BattleScreen bs = new BattleScreen(game);
                bs.setParty(party);
                bs.setEnemies(enemies);
                bs.setBackground(Assets.testBG);
                game.setScreen(bs);
            }

            if(t.y >= 250 && t.y <= 350){
                BattleCharacter[] party = new BattleCharacter[7];
                party[0] = new BattleCharacter("Yuno 1", 3, 75, 50, Assets.testChar1);
                party[1] = new BattleCharacter("Yuno 2", 3, 75, 50, Assets.testChar1);
                party[2] = new BattleCharacter("Yuno 3", 3, 75, 50, Assets.testChar1);
                party[3] = new BattleCharacter("Yuno 4", 3, 75, 50, Assets.testChar1);
                party[4] = new BattleCharacter("Yuno 5", 3, 75, 50, Assets.testChar1);
                party[5] = new BattleCharacter("Yuno 6", 3, 75, 50, Assets.testChar1);
                party[6] = new BattleCharacter("Yuno 7", 3, 75, 50, Assets.testChar1);

                Enemy[] enemies = new Enemy[1];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());

                BattleScreen bs = new BattleScreen(game);
                bs.setParty(party);
                bs.setEnemies(enemies);
                bs.setBackground(Assets.yunoBG);
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
