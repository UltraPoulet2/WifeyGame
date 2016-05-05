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

    private BattleCharacter[] currentParty;
    private BattleCharacter[] recruitedCharacters;

    public BattleSelectScreen(Game game){
        super(game);
        recruitedCharacters = new BattleCharacter[21];
        recruitedCharacters[0] = new BattleCharacter("Yuno", 3, 75, 50, Assets.TestYuno);
        recruitedCharacters[1] = new BattleCharacter("Rena", 3, 50, 75, Assets.TestRena);
        recruitedCharacters[2] = new BattleCharacter("Kotonoha", 3, 100, 25, Assets.TestKtnh);
        recruitedCharacters[3] = new BattleCharacter("Anna", 3, 25, 100, Assets.TestAnna);
        recruitedCharacters[4] = new BattleCharacter("Hitagi", 3, 60, 70, Assets.TestSjgh);
        recruitedCharacters[5] = new BattleCharacter("Yandere-chan", 3, 70, 60, Assets.TestYndr);
        recruitedCharacters[6] = new BattleCharacter("Peri", 3, 10, 200, Assets.TestPeri);
        recruitedCharacters[7] = new BattleCharacter("Luluco", 3, 75, 50, Assets.TestLulu);
        recruitedCharacters[8] = new BattleCharacter("Tsumiki", 3, 50, 75, Assets.TestTsmk);
        recruitedCharacters[9] = new BattleCharacter("Menma", 3, 100, 25, Assets.TestMnma);
        recruitedCharacters[10] = new BattleCharacter("Osaka", 3, 25, 100, Assets.TestKsga);
        recruitedCharacters[11] = new BattleCharacter("Miria", 3, 60, 70, Assets.TestMria);
        recruitedCharacters[12] = new BattleCharacter("Sen", 3, 70, 60, Assets.TestSeny);
        recruitedCharacters[13] = new BattleCharacter("Revy", 3, 10, 200, Assets.TestRevy);
        recruitedCharacters[14] = new BattleCharacter("Noel", 3, 75, 50, Assets.TestNoel);
        recruitedCharacters[15] = new BattleCharacter("Dokuro", 3, 50, 75, Assets.TestDkro);
        recruitedCharacters[16] = new BattleCharacter("Compa", 3, 100, 25, Assets.TestCmpa);
        recruitedCharacters[17] = new BattleCharacter("CC", 3, 25, 100, Assets.TestCccc);
        recruitedCharacters[18] = new BattleCharacter("Edward", 3, 60, 70, Assets.TestDwrd);
        recruitedCharacters[19] = new BattleCharacter("Angelise", 3, 70, 60, Assets.TestAnge);
        recruitedCharacters[20] = new BattleCharacter("Kyoko", 3, 10, 200, Assets.TestKyko);
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
                currentParty = new BattleCharacter[7];
                currentParty[0] = recruitedCharacters[0];
                currentParty[1] = recruitedCharacters[1];
                currentParty[2] = recruitedCharacters[2];
                currentParty[3] = recruitedCharacters[3];
                currentParty[4] = recruitedCharacters[4];
                currentParty[5] = recruitedCharacters[5];
                currentParty[6] = recruitedCharacters[6];

                Enemy[] enemies = new Enemy[3];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());
                enemies[1] = new Enemy("Enemy 2", 20000, 0, 0, 0, 0, 50, 1000, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicMagicEnemyAI());
                enemies[2] = new Enemy("Enemy 3", 50000, 20, 4, 0, 0, 0, 0, 2.0, 0.0, 0.0, 1.5, 50, 3, Assets.testEnemy, new OriginalBossAI());

                BattleScreen bs = new BattleScreen(game);
                bs.setParty(currentParty);
                bs.setEnemies(enemies);
                bs.setBackground(Assets.testBG);
                game.setScreen(bs);
            }

            if(t.y >= 250 && t.y <= 350){
                currentParty = new BattleCharacter[7];
                currentParty[0] = new BattleCharacter("Yuno 1", 3, 75, 50, Assets.TestYuno);
                currentParty[1] = new BattleCharacter("Yuno 2", 3, 75, 50, Assets.TestYuno);
                currentParty[2] = new BattleCharacter("Yuno 3", 3, 75, 50, Assets.TestYuno);
                currentParty[3] = new BattleCharacter("Yuno 4", 3, 75, 50, Assets.TestYuno);
                currentParty[4] = new BattleCharacter("Yuno 5", 3, 75, 50, Assets.TestYuno);
                currentParty[5] = new BattleCharacter("Yuno 6", 3, 75, 50, Assets.TestYuno);
                currentParty[6] = new BattleCharacter("Yuno 7", 3, 75, 50, Assets.TestYuno);

                Enemy[] enemies = new Enemy[1];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());

                BattleScreen bs = new BattleScreen(game);
                bs.setParty(currentParty);
                bs.setEnemies(enemies);
                bs.setBackground(Assets.yunoBG);
                game.setScreen(bs);
            }

            if(t.y >= 400 && t.y <= 500){
                currentParty = new BattleCharacter[7];
                currentParty[0] = recruitedCharacters[7];
                currentParty[1] = recruitedCharacters[8];
                currentParty[2] = recruitedCharacters[9];
                currentParty[3] = recruitedCharacters[10];
                currentParty[4] = recruitedCharacters[11];
                currentParty[5] = recruitedCharacters[12];
                currentParty[6] = recruitedCharacters[13];

                Enemy[] enemies = new Enemy[3];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());
                enemies[1] = new Enemy("Enemy 2", 20000, 0, 0, 0, 0, 50, 1000, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicMagicEnemyAI());
                enemies[2] = new Enemy("Enemy 3", 50000, 20, 4, 0, 0, 0, 0, 2.0, 0.0, 0.0, 1.5, 50, 3, Assets.testEnemy, new OriginalBossAI());

                BattleScreen bs = new BattleScreen(game);
                bs.setParty(currentParty);
                bs.setEnemies(enemies);
                bs.setBackground(Assets.testBG);
                game.setScreen(bs);
            }

            if(t.y >= 550 && t.y <= 700){
                currentParty = new BattleCharacter[7];
                currentParty[0] = recruitedCharacters[14];
                currentParty[1] = recruitedCharacters[15];
                currentParty[2] = recruitedCharacters[16];
                currentParty[3] = recruitedCharacters[17];
                currentParty[4] = recruitedCharacters[18];
                currentParty[5] = recruitedCharacters[19];
                currentParty[6] = recruitedCharacters[20];

                Enemy[] enemies = new Enemy[3];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());
                enemies[1] = new Enemy("Enemy 2", 20000, 0, 0, 0, 0, 50, 1000, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicMagicEnemyAI());
                enemies[2] = new Enemy("Enemy 3", 50000, 20, 4, 0, 0, 0, 0, 2.0, 0.0, 0.0, 1.5, 50, 3, Assets.testEnemy, new OriginalBossAI());

                BattleScreen bs = new BattleScreen(game);
                bs.setParty(currentParty);
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
