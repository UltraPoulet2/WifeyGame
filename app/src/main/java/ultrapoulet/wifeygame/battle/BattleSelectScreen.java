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

    private float waitTime = 0;

    private BattleCharacter[] currentParty;
    private Map<String, WifeyCharacter> recruitedCharacters;
    //private BattleCharacter[] recruitedCharacters;

    public BattleSelectScreen(Game game){
        super(game);
        recruitedCharacters = new HashMap<String, WifeyCharacter>();
        recruitedCharacters.put("TEST-YUNO", new WifeyCharacter("TEST-YUNO", "Yuno", 75, 50, Assets.TestYuno));
        recruitedCharacters.put("TEST-RENA", new WifeyCharacter("TEST-RENA", "Rena", 50, 75, Assets.TestRena));
        recruitedCharacters.put("TEST-KTNH", new WifeyCharacter("TEST-KTNH", "Kotonoha", 100, 25, Assets.TestKtnh));
        recruitedCharacters.put("TEST-ANNA", new WifeyCharacter("TEST-ANNA", "Anna", 25, 100, Assets.TestAnna));
        recruitedCharacters.put("TEST-SJGH", new WifeyCharacter("TEST-SJGH", "Senjougahara", 60, 70, Assets.TestSjgh));
        recruitedCharacters.put("TEST-YNDR", new WifeyCharacter("TEST-YNDR", "Yandere-chan", 70, 60, Assets.TestYndr));
        recruitedCharacters.put("TEST-PERI", new WifeyCharacter("TEST-PERI", "Peri", 10 ,200, Assets.TestPeri));
        recruitedCharacters.put("TEST-LULU", new WifeyCharacter("TEST-LULU", "Luluco", 75, 50, Assets.TestLulu));
        recruitedCharacters.put("TEST-TSMK", new WifeyCharacter("TEST-TSMK", "Tsumiki", 50, 75, Assets.TestTsmk));
        recruitedCharacters.put("TEST-MNMA", new WifeyCharacter("TEST-MNMA", "Menma", 100, 25, Assets.TestMnma));
        recruitedCharacters.put("TEST-KSGA", new WifeyCharacter("TEST-KSGA", "Osaka", 25, 100, Assets.TestKsga));
        recruitedCharacters.put("TEST-MRIA", new WifeyCharacter("TEST-MRIA", "Miria", 60, 70, Assets.TestMria));
        recruitedCharacters.put("TEST-SENY", new WifeyCharacter("TEST-SENY", "Sen", 70, 60, Assets.TestSeny));
        recruitedCharacters.put("TEST-REVY", new WifeyCharacter("TEST-REVY", "Revy", 10 ,200, Assets.TestRevy));
        recruitedCharacters.put("TEST-NOEL", new WifeyCharacter("TEST-NOEL", "Noel", 75, 50, Assets.TestNoel));
        recruitedCharacters.put("TEST-DKRO", new WifeyCharacter("TEST-DKRO", "Dokuro", 50, 75, Assets.TestDkro));
        recruitedCharacters.put("TEST-CMPA", new WifeyCharacter("TEST-CMPA", "Compa", 100, 25, Assets.TestCmpa));
        recruitedCharacters.put("TEST-CCCC", new WifeyCharacter("TEST-CCCC", "CC", 25, 100, Assets.TestCccc));
        recruitedCharacters.put("TEST-DWRD", new WifeyCharacter("TEST-DWRD", "Edward", 60, 70, Assets.TestDwrd));
        recruitedCharacters.put("TEST-ANGE", new WifeyCharacter("TEST-ANGE", "Angelise", 70, 60, Assets.TestAnge));
        recruitedCharacters.put("TEST-KYKO", new WifeyCharacter("TEST-KYKO", "Kyoko", 10 ,200, Assets.TestKyko));
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
                /*
                currentParty = new BattleCharacter[7];
                currentParty[0] = recruitedCharacters.get("TEST-YUNO").getBattleCharacter();
                currentParty[1] = recruitedCharacters.get("TEST-RENA").getBattleCharacter();
                currentParty[2] = recruitedCharacters.get("TEST-KTNH").getBattleCharacter();
                currentParty[3] = recruitedCharacters.get("TEST-ANNA").getBattleCharacter();
                currentParty[4] = recruitedCharacters.get("TEST-SJGH").getBattleCharacter();
                currentParty[5] = recruitedCharacters.get("TEST-YNDR").getBattleCharacter();
                currentParty[6] = recruitedCharacters.get("TEST-PERI").getBattleCharacter();
                */

                WifeyCharacter[] party = new WifeyCharacter[7];
                party[0] = recruitedCharacters.get("TEST-YUNO");
                party[1] = recruitedCharacters.get("TEST-RENA");
                party[2] = recruitedCharacters.get("TEST-KTNH");
                party[3] = recruitedCharacters.get("TEST-ANNA");
                party[4] = recruitedCharacters.get("TEST-SJGH");
                party[5] = recruitedCharacters.get("TEST-YNDR");
                party[6] = recruitedCharacters.get("TEST-PERI");

                Enemy[] enemies = new Enemy[3];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());
                enemies[1] = new Enemy("Enemy 2", 20000, 0, 0, 0, 0, 50, 1000, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicMagicEnemyAI());
                enemies[2] = new Enemy("Enemy 3", 50000, 20, 4, 0, 0, 0, 0, 2.0, 0.0, 0.0, 1.5, 50, 3, Assets.testEnemy, new OriginalBossAI());

                Party.setParty(party);
                PartySelectScreen pss = new PartySelectScreen(game);
                WifeyCharacter[] allCharacters = new WifeyCharacter[recruitedCharacters.size()];
                pss.setValidCharacters(recruitedCharacters.values().toArray(allCharacters));
                System.out.println(allCharacters.length);
                pss.setEnemies(enemies);
                pss.setPreviousScreen(this);
                game.setScreen(pss);
                /*
                BattleScreen bs = new BattleScreen(game);
                bs.setParty(currentParty);
                bs.setEnemies(enemies);
                bs.setBackground(Assets.testBG);
                game.setScreen(bs);
                */
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
                WifeyCharacter[] party = new WifeyCharacter[7];
                party[0] = recruitedCharacters.get("TEST-LULU");
                party[1] = recruitedCharacters.get("TEST-TSMK");
                party[2] = recruitedCharacters.get("TEST-MNMA");
                party[3] = recruitedCharacters.get("TEST-KSGA");
                party[4] = recruitedCharacters.get("TEST-MRIA");
                party[5] = recruitedCharacters.get("TEST-SENY");
                party[6] = recruitedCharacters.get("TEST-REVY");

                Enemy[] enemies = new Enemy[3];
                enemies[0] = new Enemy("Enemy 1", 10000, 50, 3, 35, 4, 0, 0, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicPhysicalEnemyAI());
                enemies[1] = new Enemy("Enemy 2", 20000, 0, 0, 0, 0, 50, 1000, 0.0, 0.0, 0.0, 0.0, 0, 0, Assets.testEnemy, new BasicMagicEnemyAI());
                enemies[2] = new Enemy("Enemy 3", 50000, 20, 4, 0, 0, 0, 0, 2.0, 0.0, 0.0, 1.5, 50, 3, Assets.testEnemy, new OriginalBossAI());

                Party.setParty(party);
                PartySelectScreen pss = new PartySelectScreen(game);
                WifeyCharacter[] allCharacters = new WifeyCharacter[recruitedCharacters.size()];
                pss.setValidCharacters(recruitedCharacters.values().toArray(allCharacters));
                System.out.println(allCharacters.length);
                pss.setEnemies(enemies);
                pss.setPreviousScreen(this);
                game.setScreen(pss);
            }

            if(t.y >= 550 && t.y <= 700){
                currentParty = new BattleCharacter[7];
                currentParty[0] = recruitedCharacters.get("TEST-NOEL").getBattleCharacter();
                currentParty[1] = recruitedCharacters.get("TEST-DKRO").getBattleCharacter();
                currentParty[2] = recruitedCharacters.get("TEST-CMPA").getBattleCharacter();
                currentParty[3] = recruitedCharacters.get("TEST-CCCC").getBattleCharacter();
                currentParty[4] = recruitedCharacters.get("TEST-DWRD").getBattleCharacter();
                currentParty[5] = recruitedCharacters.get("TEST-ANGE").getBattleCharacter();
                currentParty[6] = recruitedCharacters.get("TEST-KYKO").getBattleCharacter();

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
