package ultrapoulet.wifeygame;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.battle.BattleSelectScreen;

/**
 * Created by John on 3/12/2016.
 */
public class LoadingScreen extends Screen {

    public LoadingScreen(Game game){
        super(game);
    }
    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.testBG = g.newImage("backgrounds/testbg.png", ImageFormat.RGB565);
        Assets.yunoBG = g.newImage("backgrounds/yunobg.png", ImageFormat.RGB565);
        Assets.buttonMenuNormal = g.newImage("BattleButtonMenuNormal.png", ImageFormat.RGB565);
        Assets.buttonMenuSpecial = g.newImage("BattleButtonMenuSpecial.png", ImageFormat.RGB565);
        Assets.buttonMenuBoth = g.newImage("BattleButtonMenuBoth.png", ImageFormat.RGB565);
        Assets.attackBox = g.newImage("AttackBox.png", ImageFormat.ARGB8888);
        Assets.charHolder = g.newImage("CharacterHolder.png", ImageFormat.ARGB8888);
        Assets.TestYuno = g.newImage("characters/TEST-YUNO.png", ImageFormat.RGB565);
        Assets.TestRena = g.newImage("characters/TEST-RENA.png", ImageFormat.RGB565);
        Assets.TestKtnh = g.newImage("characters/TEST-KTNH.png", ImageFormat.RGB565);
        Assets.TestAnna = g.newImage("characters/TEST-ANNA.png", ImageFormat.RGB565);
        Assets.TestSjgh = g.newImage("characters/TEST-SJGH.png", ImageFormat.RGB565);
        Assets.TestYndr = g.newImage("characters/TEST-YNDR.png", ImageFormat.RGB565);
        Assets.TestPeri = g.newImage("characters/TEST-PERI.png", ImageFormat.RGB565);
        Assets.TestLulu = g.newImage("characters/TEST-LULU.png", ImageFormat.RGB565);
        Assets.TestTsmk = g.newImage("characters/TEST-TSMK.png", ImageFormat.RGB565);
        Assets.TestMnma = g.newImage("characters/TEST-MNMA.png", ImageFormat.RGB565);
        Assets.TestKsga = g.newImage("characters/TEST-KSGA.png", ImageFormat.RGB565);
        Assets.TestMria = g.newImage("characters/TEST-MRIA.png", ImageFormat.RGB565);
        Assets.TestSeny = g.newImage("characters/TEST-SENY.png", ImageFormat.RGB565);
        Assets.TestRevy = g.newImage("characters/TEST-REVY.png", ImageFormat.RGB565);
        Assets.TestNoel = g.newImage("characters/TEST-NOEL.png", ImageFormat.RGB565);
        Assets.TestDkro = g.newImage("characters/TEST-DKRO.png", ImageFormat.RGB565);
        Assets.TestCmpa = g.newImage("characters/TEST-CMPA.png", ImageFormat.RGB565);
        Assets.TestCccc = g.newImage("characters/TEST-CCCC.png", ImageFormat.RGB565);
        Assets.TestDwrd = g.newImage("characters/TEST-DWRD.png", ImageFormat.RGB565);
        Assets.TestAnge = g.newImage("characters/TEST-ANGE.png", ImageFormat.RGB565);
        Assets.TestKyko = g.newImage("characters/TEST-KYKO.png", ImageFormat.RGB565);
        Assets.pHealthG = g.newImage("HealthBarGreen.png", ImageFormat.RGB565);
        Assets.pHealthY = g.newImage("HealthBarYellow.png", ImageFormat.RGB565);
        Assets.pHealthR = g.newImage("HealthBarRed.png", ImageFormat.RGB565);
        Assets.eHealthG = g.newImage("EnemyHealthBarGreen.png", ImageFormat.RGB565);
        Assets.eHealthY = g.newImage("EnemyHealthBarYellow.png", ImageFormat.RGB565);
        Assets.eHealthR = g.newImage("EnemyHealthBarRed.png", ImageFormat.RGB565);
        Assets.enemyHolder = g.newImage("EnemyHealthHolder.png", ImageFormat.ARGB8888);
        Assets.testEnemy = g.newImage("TemplateEnemy.png", ImageFormat.RGB565);
        Assets.specialBar = g.newImage("SpecialBar.png", ImageFormat.RGB565);
        Assets.specialBarBase = g.newImage("SpecialBarBase.png", ImageFormat.ARGB8888);
        Assets.specialBarTop = g.newImage("SpecialBarTop.png", ImageFormat.ARGB8888);
        Assets.hitsText = g.newImage("Hits.png", ImageFormat.ARGB8888);
        Assets.hitText = g.newImage("Hit.png", ImageFormat.ARGB8888);
        Assets.damageText = g.newImage("Damage.png", ImageFormat.ARGB8888);

        Assets.KOImages = new Image[7];
        for(int i = 0; i < 7; i++){
            Assets.KOImages[i] = g.newImage("KOImage" + i + ".png", ImageFormat.ARGB8888);
        }

        Assets.HPNumbers = new Image[11];
        for(int i = 0; i < 10; i++){
            Assets.HPNumbers[i] = g.newImage("numbers/HP" + i + ".png", ImageFormat.ARGB8888);
        }
        Assets.HPNumbers[10] = g.newImage("numbers/HPSlash.png", ImageFormat.ARGB8888);

        Assets.HPHealNumbers = new Image[10];
        for(int i = 0; i < 10; i++){
            Assets.HPHealNumbers[i] = g.newImage("numbers/HP" + i + "G.png", ImageFormat.ARGB8888);
        }

        Assets.ComboHitsNumbers = new Image[10];
        for(int i = 0; i < 10; i++){
            Assets.ComboHitsNumbers[i] = g.newImage("numbers/Combo" + i + ".png", ImageFormat.ARGB8888);
        }

        Assets.DamageHitsNumbers = new Image[10];
        for(int i = 0; i < 10; i++){
            Assets.DamageHitsNumbers[i] = g.newImage("numbers/Damage" + i + ".png", ImageFormat.ARGB8888);
        }

        Assets.PartySelectScreen = g.newImage("screens/PartySelectScreen.png", ImageFormat.RGB565);
        Assets.AcceptEnable = g.newImage("buttons/AcceptEnabled.png", ImageFormat.ARGB8888);
        Assets.AcceptDisable = g.newImage("buttons/AcceptDisabled.png", ImageFormat.ARGB8888);
        Assets.NextPageEnable = g.newImage("buttons/NextPageEnabled.png", ImageFormat.ARGB8888);
        Assets.NextPageDisable = g.newImage("buttons/NextPageDisabled.png", ImageFormat.ARGB8888);
        Assets.PrevPageEnable = g.newImage("buttons/PrevPageEnabled.png", ImageFormat.ARGB8888);
        Assets.PrevPageDisable = g.newImage("buttons/PrevPageDisabled.png", ImageFormat.ARGB8888);

        BattleSelectScreen bss = new BattleSelectScreen(game);
        game.setScreen(bss);

    }

    @Override
    public void paint(float deltaTime) {

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
