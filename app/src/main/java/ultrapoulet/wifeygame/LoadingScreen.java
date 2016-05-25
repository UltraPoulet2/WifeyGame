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
        RecruitedCharacters.put("TEST-DKRO", new WifeyCharacter("TEST-DKRO", "Dokuro Mitsukai", 70, 90, Assets.TestDkro));
        RecruitedCharacters.put("TEST-DWRD", new WifeyCharacter("TEST-DWRD", "Edward Wong Hau Pepelu Tivrusky IV", 60, 70, Assets.TestDwrd));
        RecruitedCharacters.put("TEST-EEMI", new WifeyCharacter("TEST-EEMI", "Emi Yusa", 90, 60, Assets.TestEemi));
        RecruitedCharacters.put("TEST-EXCL", new WifeyCharacter("TEST-EXCL", "Excel", 70, 60, Assets.TestExcl));
        RecruitedCharacters.put("TEST-GGOU", new WifeyCharacter("TEST-GGOU", "Gou Matsuoka", 40, 40, Assets.TestGgou));
        RecruitedCharacters.put("TEST-HARU", new WifeyCharacter("TEST-HARU", "Haruko Haruhara", 60, 70, Assets.TestHaru));
        RecruitedCharacters.put("TEST-HIME", new WifeyCharacter("TEST-HIME", "Hime Arikawa", 30, 30, Assets.TestHime));
        RecruitedCharacters.put("TEST-HKSE", new WifeyCharacter("TEST-HKSE", "Hakase Shinonome", 20, 60, Assets.TestHkse));
        RecruitedCharacters.put("TEST-HMRA", new WifeyCharacter("TEST-HMRA", "Homura Akemi", 40, 120, Assets.TestHmra));
        RecruitedCharacters.put("TEST-HMRI", new WifeyCharacter("TEST-HMRI", "Himari Takakura", 30, 50, Assets.TestHmri));
        RecruitedCharacters.put("TEST-HOLO", new WifeyCharacter("TEST-HOLO", "Holo", 70, 60, Assets.TestHolo));
        RecruitedCharacters.put("TEST-HRHI", new WifeyCharacter("TEST-HRHI", "Haruhi Fujioka", 50, 30, Assets.TestHrhi));
        RecruitedCharacters.put("TEST-ICHK", new WifeyCharacter("TEST-ICHK", "Ichika Mizuhara", 70, 30, Assets.TestIchk));
        RecruitedCharacters.put("TEST-IROE", new WifeyCharacter("TEST-IROE", "Iroe Genma", 80, 20, Assets.TestIroe));
        RecruitedCharacters.put("TEST-KARN", new WifeyCharacter("TEST-KARN", "Karen Kujou", 60, 40, Assets.TestKarn));
        RecruitedCharacters.put("TEST-KDOU", new WifeyCharacter("TEST-KDOU", "Mirei Kudou", 50, 100, Assets.TestKdou));
        RecruitedCharacters.put("TEST-KOKO", new WifeyCharacter("TEST-KOKO", "Kouko Kaga", 40, 40, Assets.TestKoko));
        RecruitedCharacters.put("TEST-KRSU", new WifeyCharacter("TEST-KRSU", "Kurisu Makise", 40, 50, Assets.TestKrsu));
        RecruitedCharacters.put("TEST-KSGA", new WifeyCharacter("TEST-KSGA", "Ayumu Kasuga", 20, 20, Assets.TestKsga));
        RecruitedCharacters.put("TEST-KTNH", new WifeyCharacter("TEST-KTNH", "Kotonoha Katsura", 70, 25, Assets.TestKtnh));
        RecruitedCharacters.put("TEST-KTRU", new WifeyCharacter("TEST-KTRU", "Koutarou Araki", 30, 70, Assets.TestKtru));
        RecruitedCharacters.put("TEST-KTYS", new WifeyCharacter("TEST-KTYS", "Katyusha", 60, 30, Assets.TestKtys));
        RecruitedCharacters.put("TEST-KYKO", new WifeyCharacter("TEST-KYKO", "Kyoko Kirigiri", 50, 90, Assets.TestKyko));
        RecruitedCharacters.put("TEST-LULU", new WifeyCharacter("TEST-LULU", "Luluco", 70, 70, Assets.TestLulu));
        RecruitedCharacters.put("TEST-MAKI", new WifeyCharacter("TEST-MAKI", "Natsuo Maki", 40, 40, Assets.TestMaki));
        RecruitedCharacters.put("TEST-MAKO", new WifeyCharacter("TEST-MAKO", "Mako Mankanshoku", 60, 20, Assets.TestMako));
        RecruitedCharacters.put("TEST-MARI", new WifeyCharacter("TEST-MARI", "Maria Naruse", 60, 100, Assets.TestMari));
        RecruitedCharacters.put("TEST-MDKA", new WifeyCharacter("TEST-MDKA", "Medaka Kurokami", 200, 20, Assets.TestMdka));
        RecruitedCharacters.put("TEST-MDRI", new WifeyCharacter("TEST-MDRI", "Midori Kasugano", 30, 50, Assets.TestMdri));
        RecruitedCharacters.put("TEST-MEGU", new WifeyCharacter("TEST-MEGU", "Megumi Sakura", 40, 70, Assets.TestMegu));
        RecruitedCharacters.put("TEST-MIIA", new WifeyCharacter("TEST-MIIA", "Miia", 100, 30, Assets.TestMiia));
        RecruitedCharacters.put("TEST-MKRU", new WifeyCharacter("TEST-MKRU", "Mikuru Asahina", 40, 70, Assets.TestMkru));
        RecruitedCharacters.put("TEST-MKSA", new WifeyCharacter("TEST-MKSA", "Mikasa Ackerman", 140, 20, Assets.TestMksa));
        RecruitedCharacters.put("TEST-MMKA", new WifeyCharacter("TEST-MMKA", "Momoka Sonokawa", 70, 40, Assets.TestMmka));
        RecruitedCharacters.put("TEST-MNMA", new WifeyCharacter("TEST-MNMA", "Meiko Honma", 40, 60, Assets.TestMnma));
        RecruitedCharacters.put("TEST-MRIA", new WifeyCharacter("TEST-MRIA", "Miria Harvent", 60, 30, Assets.TestMria));
        RecruitedCharacters.put("TEST-MSKI", new WifeyCharacter("TEST-MSKI", "Misaki Nakahara", 30, 40, Assets.TestMski));
        RecruitedCharacters.put("TEST-MSZU", new WifeyCharacter("TEST-MSZU", "Misuzu Natsukawa", 60, 30, Assets.TestMszu));
        RecruitedCharacters.put("TEST-NIAT", new WifeyCharacter("TEST-NIAT", "Nia Teppelin", 60, 80, Assets.TestNiat));
        RecruitedCharacters.put("TEST-NNKO", new WifeyCharacter("TEST-NNKO", "Nanako Yamamoto", 30, 50, Assets.TestNnko));
        RecruitedCharacters.put("TEST-NOEL", new WifeyCharacter("TEST-NOEL", "Noel Vermillion", 80, 90, Assets.TestNoel));
        RecruitedCharacters.put("TEST-NYRK", new WifeyCharacter("TEST-NYRK", "Nyaruko", 90, 60, Assets.TestNyrk));
        RecruitedCharacters.put("TEST-PERI", new WifeyCharacter("TEST-PERI", "Peri", 90, 20, Assets.TestPeri));
        RecruitedCharacters.put("TEST-RAMU", new WifeyCharacter("TEST-RAMU", "Raimu Kawasaki", 80, 40, Assets.TestRamu));
        RecruitedCharacters.put("TEST-RENA", new WifeyCharacter("TEST-RENA", "Rena Ryuuguu", 70, 50, Assets.TestRena));
        RecruitedCharacters.put("TEST-REVY", new WifeyCharacter("TEST-REVY", "Revy", 100, 20, Assets.TestRevy));
        RecruitedCharacters.put("TEST-RIKO", new WifeyCharacter("TEST-RIKO", "Riko Aida", 40, 50, Assets.TestRiko));
        RecruitedCharacters.put("TEST-RTSK", new WifeyCharacter("TEST-RTSK", "Ritsuko Kunihiro", 40, 60, Assets.TestRtsk));
        RecruitedCharacters.put("TEST-RUKO", new WifeyCharacter("TEST-RUKO", "Ruuko Kominato", 30, 70, Assets.TestRuko));
        RecruitedCharacters.put("TEST-SAKA", new WifeyCharacter("TEST-SAKA", "Saika Totsuka", 60, 40, Assets.TestSaka));
        RecruitedCharacters.put("TEST-SAYA", new WifeyCharacter("TEST-SAYA", "Saya Sasamiya", 80, 90, Assets.TestSaya));
        RecruitedCharacters.put("TEST-SENY", new WifeyCharacter("TEST-SENY", "Sen Yarizuri", 90, 30, Assets.TestSeny));
        RecruitedCharacters.put("TEST-SJGH", new WifeyCharacter("TEST-SJGH", "Hitagi Senjougahara", 70, 40, Assets.TestSjgh));
        RecruitedCharacters.put("TEST-SMZU", new WifeyCharacter("TEST-SMZU", "Kyoko Shimizu", 40, 40, Assets.TestSmzu));
        RecruitedCharacters.put("TEST-SNSH", new WifeyCharacter("TEST-SNSH", "Sonoshee McLaren", 80, 30, Assets.TestSnsh));
        RecruitedCharacters.put("TEST-STPH", new WifeyCharacter("TEST-STPH", "Stephanie Dola", 40, 60, Assets.TestStph));
        RecruitedCharacters.put("TEST-SUZU", new WifeyCharacter("TEST-SUZU", "Suzu Hagimura", 30, 60, Assets.TestSuzu));
        RecruitedCharacters.put("TEST-TESA", new WifeyCharacter("TEST-TESA", "Teletha Testarossa", 40, 80, Assets.TestTesa));
        RecruitedCharacters.put("TEST-TNRI", new WifeyCharacter("TEST-TNRI", "Tenri Ayukawa", 40, 70, Assets.TestTnri));
        RecruitedCharacters.put("TEST-TSMK", new WifeyCharacter("TEST-TSMK", "Tsumiki Miniwa", 40, 40, Assets.TestTsmk));
        RecruitedCharacters.put("TEST-TTSM", new WifeyCharacter("TEST-TTSM", "Tatsumaki", 40, 130, Assets.TestTtsm));
        RecruitedCharacters.put("TEST-WNRY", new WifeyCharacter("TEST-WNRY", "Winry Rockbell", 50, 50, Assets.TestWnry));
        RecruitedCharacters.put("TEST-YASN", new WifeyCharacter("TEST-YASN", "Yassan", 40, 50, Assets.TestYasn));
        RecruitedCharacters.put("TEST-YKKO", new WifeyCharacter("TEST-YYKO", "Yukiko Amagi", 60, 100, Assets.TestYkko));
        RecruitedCharacters.put("TEST-YNDR", new WifeyCharacter("TEST-YNDR", "Yandere-chan", 70, 60, Assets.TestYndr));
        RecruitedCharacters.put("TEST-YRKA", new WifeyCharacter("TEST-YRKA", "Yurika Misumaru", 40, 40, Assets.TestYrka));
        RecruitedCharacters.put("TEST-YSHI", new WifeyCharacter("TEST-YSHI", "Yoshino", 50, 80, Assets.TestYshi));
        RecruitedCharacters.put("TEST-YUNA", new WifeyCharacter("TEST-YUNA", "Yuuna Yuuki", 80, 70, Assets.TestYuna));
        RecruitedCharacters.put("TEST-YUNO", new WifeyCharacter("TEST-YUNO", "Yuno Gasai", 70, 70, Assets.TestYuno));
        RecruitedCharacters.put("TEST-YURA", new WifeyCharacter("TEST-YURA", "Yura Yamato", 40, 40, Assets.TestYura));
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
