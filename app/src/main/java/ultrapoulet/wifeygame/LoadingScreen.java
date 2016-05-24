package ultrapoulet.wifeygame;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.battle.BattleSelectScreen;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

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
        Assets.LockSelection = g.newImage("LockSelection.png", ImageFormat.ARGB8888);

        createCharacterImages();
        createRecruits();
        createParty();

        BattleSelectScreen bss = new BattleSelectScreen(game);
        game.setScreen(bss);

    }

    private void createCharacterImages(){
        Graphics g = game.getGraphics();
        Assets.TestAnge = g.newImage("characters/TEST-ANGE.png", ImageFormat.RGB565);
        Assets.TestAngu = g.newImage("characters/TEST-ANGU.png", ImageFormat.RGB565);
        Assets.TestAnna = g.newImage("characters/TEST-ANNA.png", ImageFormat.RGB565);
        Assets.TestAsha = g.newImage("characters/TEST-ASHA.png", ImageFormat.RGB565);
        Assets.TestCccc = g.newImage("characters/TEST-CCCC.png", ImageFormat.RGB565);
        Assets.TestCgli = g.newImage("characters/TEST-CGLI.png", ImageFormat.RGB565);
        Assets.TestClty = g.newImage("characters/TEST-CLTY.png", ImageFormat.RGB565);
        Assets.TestCmpa = g.newImage("characters/TEST-CMPA.png", ImageFormat.RGB565);
        Assets.TestCyan = g.newImage("characters/TEST-CYAN.png", ImageFormat.RGB565);
        Assets.TestDkro = g.newImage("characters/TEST-DKRO.png", ImageFormat.RGB565);
        Assets.TestDwrd = g.newImage("characters/TEST-DWRD.png", ImageFormat.RGB565);
        Assets.TestEemi = g.newImage("characters/TEST-EEMI.png", ImageFormat.RGB565);
        Assets.TestExcl = g.newImage("characters/TEST-EXCL.png", ImageFormat.RGB565);
        Assets.TestGgou = g.newImage("characters/TEST-GGOU.png", ImageFormat.RGB565);
        Assets.TestHaru = g.newImage("characters/TEST-HARU.png", ImageFormat.RGB565);
        Assets.TestHime = g.newImage("characters/TEST-HIME.png", ImageFormat.RGB565);
        Assets.TestHkse = g.newImage("characters/TEST-HKSE.png", ImageFormat.RGB565);
        Assets.TestHmra = g.newImage("characters/TEST-HMRA.png", ImageFormat.RGB565);
        Assets.TestHmri = g.newImage("characters/TEST-HMRI.png", ImageFormat.RGB565);
        Assets.TestHolo = g.newImage("characters/TEST-HOLO.png", ImageFormat.RGB565);
        Assets.TestHrhi = g.newImage("characters/TEST-HRHI.png", ImageFormat.RGB565);
        Assets.TestIchk = g.newImage("characters/TEST-ICHK.png", ImageFormat.RGB565);
        Assets.TestIroe = g.newImage("characters/TEST-IROE.png", ImageFormat.RGB565);
        Assets.TestKarn = g.newImage("characters/TEST-KARN.png", ImageFormat.RGB565);
        Assets.TestKdou = g.newImage("characters/TEST-KDOU.png", ImageFormat.RGB565);
        Assets.TestKoko = g.newImage("characters/TEST-KOKO.png", ImageFormat.RGB565);
        Assets.TestKrsu = g.newImage("characters/TEST-KRSU.png", ImageFormat.RGB565);
        Assets.TestKsga = g.newImage("characters/TEST-KSGA.png", ImageFormat.RGB565);
        Assets.TestKtnh = g.newImage("characters/TEST-KTNH.png", ImageFormat.RGB565);
        Assets.TestKtru = g.newImage("characters/TEST-KTRU.png", ImageFormat.RGB565);
        Assets.TestKtys = g.newImage("characters/TEST-KTYS.png", ImageFormat.RGB565);
        Assets.TestKyko = g.newImage("characters/TEST-KYKO.png", ImageFormat.RGB565);
        Assets.TestLulu = g.newImage("characters/TEST-LULU.png", ImageFormat.RGB565);
        Assets.TestMaki = g.newImage("characters/TEST-MAKI.png", ImageFormat.RGB565);
        Assets.TestMako = g.newImage("characters/TEST-MAKO.png", ImageFormat.RGB565);
        Assets.TestMari = g.newImage("characters/TEST-MARI.png", ImageFormat.RGB565);
        Assets.TestMdka = g.newImage("characters/TEST-MDKA.png", ImageFormat.RGB565);
        Assets.TestMdri = g.newImage("characters/TEST-MDRI.png", ImageFormat.RGB565);
        Assets.TestMegu = g.newImage("characters/TEST-MEGU.png", ImageFormat.RGB565);
        Assets.TestMiia = g.newImage("characters/TEST-MIIA.png", ImageFormat.RGB565);
        Assets.TestMkru = g.newImage("characters/TEST-MKRU.png", ImageFormat.RGB565);
        Assets.TestMksa = g.newImage("characters/TEST-MKSA.png", ImageFormat.RGB565);
        Assets.TestMmka = g.newImage("characters/TEST-MMKA.png", ImageFormat.RGB565);
        Assets.TestMnma = g.newImage("characters/TEST-MNMA.png", ImageFormat.RGB565);
        Assets.TestMria = g.newImage("characters/TEST-MRIA.png", ImageFormat.RGB565);
        Assets.TestMski = g.newImage("characters/TEST-MSKI.png", ImageFormat.RGB565);
        Assets.TestMszu = g.newImage("characters/TEST-MSZU.png", ImageFormat.RGB565);
        Assets.TestNiat = g.newImage("characters/TEST-NIAT.png", ImageFormat.RGB565);
        Assets.TestNnko = g.newImage("characters/TEST-NNKO.png", ImageFormat.RGB565);
        Assets.TestNoel = g.newImage("characters/TEST-NOEL.png", ImageFormat.RGB565);
        Assets.TestNyrk = g.newImage("characters/TEST-NYRK.png", ImageFormat.RGB565);
        Assets.TestPeri = g.newImage("characters/TEST-PERI.png", ImageFormat.RGB565);
        Assets.TestRamu = g.newImage("characters/TEST-RAMU.png", ImageFormat.RGB565);
        Assets.TestRena = g.newImage("characters/TEST-RENA.png", ImageFormat.RGB565);
        Assets.TestRevy = g.newImage("characters/TEST-REVY.png", ImageFormat.RGB565);
        Assets.TestRiko = g.newImage("characters/TEST-RIKO.png", ImageFormat.RGB565);
        Assets.TestRtsk = g.newImage("characters/TEST-RTSK.png", ImageFormat.RGB565);
        Assets.TestRuko = g.newImage("characters/TEST-RUKO.png", ImageFormat.RGB565);
        Assets.TestSaka = g.newImage("characters/TEST-SAKA.png", ImageFormat.RGB565);
        Assets.TestSaya = g.newImage("characters/TEST-SAYA.png", ImageFormat.RGB565);
        Assets.TestSeny = g.newImage("characters/TEST-SENY.png", ImageFormat.RGB565);
        Assets.TestSjgh = g.newImage("characters/TEST-SJGH.png", ImageFormat.RGB565);
        Assets.TestSmzu = g.newImage("characters/TEST-SMZU.png", ImageFormat.RGB565);
        Assets.TestSnsh = g.newImage("characters/TEST-SNSH.png", ImageFormat.RGB565);
        Assets.TestStph = g.newImage("characters/TEST-STPH.png", ImageFormat.RGB565);
        Assets.TestSuzu = g.newImage("characters/TEST-SUZU.png", ImageFormat.RGB565);
        Assets.TestTesa = g.newImage("characters/TEST-TESA.png", ImageFormat.RGB565);
        Assets.TestTnri = g.newImage("characters/TEST-TNRI.png", ImageFormat.RGB565);
        Assets.TestTsmk = g.newImage("characters/TEST-TSMK.png", ImageFormat.RGB565);
        Assets.TestTtsm = g.newImage("characters/TEST-TTSM.png", ImageFormat.RGB565);
        Assets.TestWnry = g.newImage("characters/TEST-WNRY.png", ImageFormat.RGB565);
        Assets.TestYasn = g.newImage("characters/TEST-YASN.png", ImageFormat.RGB565);
        Assets.TestYkko = g.newImage("characters/TEST-YKKO.png", ImageFormat.RGB565);
        Assets.TestYndr = g.newImage("characters/TEST-YNDR.png", ImageFormat.RGB565);
        Assets.TestYrka = g.newImage("characters/TEST-YRKA.png", ImageFormat.RGB565);
        Assets.TestYshi = g.newImage("characters/TEST-YSHI.png", ImageFormat.RGB565);
        Assets.TestYuna = g.newImage("characters/TEST-YUNA.png", ImageFormat.RGB565);
        Assets.TestYuno = g.newImage("characters/TEST-YUNO.png", ImageFormat.RGB565);
        Assets.TestYura = g.newImage("characters/TEST-YURA.png", ImageFormat.RGB565);
    }

    private void createRecruits(){
        RecruitedCharacters.put("TEST-ANGE", new WifeyCharacter("TEST-ANGE", "Angelise Ikaruga Misurugi", 70, 60, Assets.TestAnge));
        RecruitedCharacters.put("TEST-ANGU", new WifeyCharacter("TEST-ANGU", "Ange Ushiromiya", 80, 10, Assets.TestAngu));
        RecruitedCharacters.put("TEST-ANNA", new WifeyCharacter("TEST-ANNA", "Anna Nishikinomiya", 120, 10, Assets.TestAnna));
        RecruitedCharacters.put("TEST-ASHA", new WifeyCharacter("TEST-ASHA", "Aisha Clanclan", 100, 25, Assets.TestAsha));
        RecruitedCharacters.put("TEST-CCCC", new WifeyCharacter("TEST-CCCC", "CC", 25, 100, Assets.TestCccc));
        RecruitedCharacters.put("TEST-CGLI", new WifeyCharacter("TEST-CGLI", "Cagali Yula Athha", 50, 75, Assets.TestCgli));
        RecruitedCharacters.put("TEST-CLTY", new WifeyCharacter("TEST-CLTY", "Celty Struluson", 40, 90, Assets.TestClty));
        RecruitedCharacters.put("TEST-CMPA", new WifeyCharacter("TEST-CMPA", "Compa", 25, 100, Assets.TestCmpa));
        RecruitedCharacters.put("TEST-CYAN", new WifeyCharacter("TEST-CYAN", "Cyan Hijirikawa", 20, 110, Assets.TestCyan));
        RecruitedCharacters.put("TEST-DKRO", new WifeyCharacter("TEST-DKRO", "Dokuro Mitsukai", 40, 90, Assets.TestDkro));
        RecruitedCharacters.put("TEST-DWRD", new WifeyCharacter("TEST-DWRD", "Edward Wong Hau Pepelu Tivrusky IV", 60, 70, Assets.TestDwrd));
        RecruitedCharacters.put("TEST-EEMI", new WifeyCharacter("TEST-EEMI", "Emi Yusa", 90, 60, Assets.TestEemi));
        RecruitedCharacters.put("TEST-EXCL", new WifeyCharacter("TEST-EXCL", "Excel", 70, 60, Assets.TestExcl));
        RecruitedCharacters.put("TEST-GGOU", new WifeyCharacter("TEST-GGOU", "Gou Matsuoka", 40, 40, Assets.TestGgou));
        RecruitedCharacters.put("TEST-HARU", new WifeyCharacter("TEST-HARU", "Haruko Haruhara", 60, 70, Assets.TestHaru));
        RecruitedCharacters.put("TEST-HIME", new WifeyCharacter("TEST-HIME", "Hime Arikawa", 30, 30, Assets.TestHime));
        RecruitedCharacters.put("TEST-HKSE", new WifeyCharacter("TEST-HKSE", "Hakase Shinonome", 20, 60, Assets.TestHkse));
        RecruitedCharacters.put("TEST-HMRA", new WifeyCharacter("TEST-HMRA", "Homura Akemi", 40, 100, Assets.TestHmra));
        RecruitedCharacters.put("TEST-KSGA", new WifeyCharacter("TEST-KSGA", "Osaka", 25, 100, Assets.TestKsga));
        RecruitedCharacters.put("TEST-KTNH", new WifeyCharacter("TEST-KTNH", "Kotonoha", 100, 25, Assets.TestKtnh));
        RecruitedCharacters.put("TEST-KYKO", new WifeyCharacter("TEST-KYKO", "Kyoko", 10 ,200, Assets.TestKyko));
        RecruitedCharacters.put("TEST-LULU", new WifeyCharacter("TEST-LULU", "Luluco", 75, 50, Assets.TestLulu));
        RecruitedCharacters.put("TEST-MNMA", new WifeyCharacter("TEST-MNMA", "Menma", 100, 25, Assets.TestMnma));
        RecruitedCharacters.put("TEST-MRIA", new WifeyCharacter("TEST-MRIA", "Miria", 60, 70, Assets.TestMria));
        RecruitedCharacters.put("TEST-NOEL", new WifeyCharacter("TEST-NOEL", "Noel", 75, 50, Assets.TestNoel));
        RecruitedCharacters.put("TEST-PERI", new WifeyCharacter("TEST-PERI", "Peri", 10 ,200, Assets.TestPeri));
        RecruitedCharacters.put("TEST-RENA", new WifeyCharacter("TEST-RENA", "Rena", 50, 75, Assets.TestRena));
        RecruitedCharacters.put("TEST-REVY", new WifeyCharacter("TEST-REVY", "Revy", 10 ,200, Assets.TestRevy));
        RecruitedCharacters.put("TEST-SENY", new WifeyCharacter("TEST-SENY", "Sen", 70, 60, Assets.TestSeny));
        RecruitedCharacters.put("TEST-SJGH", new WifeyCharacter("TEST-SJGH", "Senjougahara", 60, 70, Assets.TestSjgh));
        RecruitedCharacters.put("TEST-TNRI", new WifeyCharacter("TEST-TNRI", "Tenri", 70, 60, Assets.TestTnri));
        RecruitedCharacters.put("TEST-TSMK", new WifeyCharacter("TEST-TSMK", "Tsumiki", 50, 75, Assets.TestTsmk));
        RecruitedCharacters.put("TEST-YNDR", new WifeyCharacter("TEST-YNDR", "Yandere-chan", 70, 60, Assets.TestYndr));
        RecruitedCharacters.put("TEST-YUNO", new WifeyCharacter("TEST-YUNO", "Yuno", 75, 50, Assets.TestYuno));
    }

    private void createParty(){
        Party.setIndex(0, RecruitedCharacters.get("TEST-YUNO"));
        Party.setIndex(1, RecruitedCharacters.get("TEST-RENA"));
        Party.setIndex(2, RecruitedCharacters.get("TEST-KTNH"));
        Party.setIndex(3, RecruitedCharacters.get("TEST-ANNA"));
        Party.setIndex(4, RecruitedCharacters.get("TEST-SJGH"));
        Party.setIndex(5, RecruitedCharacters.get("TEST-YNDR"));
        Party.setIndex(6, RecruitedCharacters.get("TEST-PERI"));
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
