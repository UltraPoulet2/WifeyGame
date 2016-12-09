package ultrapoulet.wifeygame.screens;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;
import ultrapoulet.wifeygame.parsers.BattleParser;
import ultrapoulet.wifeygame.parsers.CharacterParser;
import ultrapoulet.wifeygame.parsers.EnemyParser;

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

        Assets.PowerAttackEnabled = g.newImage("buttons/PowerAttackEnabled.png", ImageFormat.RGB565);
        Assets.PowerAttackDisabled = g.newImage("buttons/PowerAttackDisabled.png", ImageFormat.RGB565);
        Assets.ComboAttackEnabled = g.newImage("buttons/ComboAttackEnabled.png", ImageFormat.RGB565);
        Assets.ComboAttackDisabled = g.newImage("buttons/ComboAttackDisabled.png", ImageFormat.RGB565);
        Assets.MagicAttackEnabled = g.newImage("buttons/MagicAttackEnabled.png", ImageFormat.RGB565);
        Assets.MagicAttackDisabled = g.newImage("buttons/MagicAttackDisabled.png", ImageFormat.RGB565);
        Assets.HealMagicEnabled = g.newImage("buttons/HealMagicEnabled.png", ImageFormat.RGB565);
        Assets.HealMagicDisabled = g.newImage("buttons/HealMagicDisabled.png", ImageFormat.RGB565);
        Assets.SpecialAttackEnabled = g.newImage("buttons/SpecialAttackEnabled.png", ImageFormat.RGB565);
        Assets.SpecialAttackDisabled = g.newImage("buttons/SpecialAttackDisabled.png", ImageFormat.RGB565);
        Assets.TransformEnabled = g.newImage("buttons/TransformEnabled.png", ImageFormat.RGB565);
        Assets.TransformDisabled = g.newImage("buttons/TransformDisabled.png", ImageFormat.RGB565);
        Assets.DefendEnabled = g.newImage("buttons/DefendButtonEnabled.png", ImageFormat.RGB565);
        Assets.DefendDisabled = g.newImage("buttons/DefendButtonDisabled.png", ImageFormat.RGB565);

        Assets.attackBox = g.newImage("objects/battleMisc/AttackBox.png", ImageFormat.ARGB8888);
        Assets.charHolder = g.newImage("objects/holders/CharacterHolder.png", ImageFormat.ARGB8888);

        Assets.pHealthG = g.newImage("objects/bars/HealthBarGreen.png", ImageFormat.RGB565);
        Assets.pHealthY = g.newImage("objects/bars/HealthBarYellow.png", ImageFormat.RGB565);
        Assets.pHealthR = g.newImage("objects/bars/HealthBarRed.png", ImageFormat.RGB565);
        Assets.eHealthG = g.newImage("objects/bars/EnemyHealthBarGreen.png", ImageFormat.RGB565);
        Assets.eHealthY = g.newImage("objects/bars/EnemyHealthBarYellow.png", ImageFormat.RGB565);
        Assets.eHealthR = g.newImage("objects/bars/EnemyHealthBarRed.png", ImageFormat.RGB565);
        Assets.enemyHolder = g.newImage("objects/bars/EnemyHealthHolder.png", ImageFormat.ARGB8888);
        Assets.specialBar = g.newImage("objects/bars/SpecialBar.png", ImageFormat.RGB565);
        Assets.specialBarBase = g.newImage("objects/bars/SpecialBarBase.png", ImageFormat.ARGB8888);
        Assets.specialBarTop = g.newImage("objects/bars/SpecialBarTop.png", ImageFormat.ARGB8888);

        Assets.hitsText = g.newImage("objects/text/Hits.png", ImageFormat.ARGB8888);
        Assets.hitText = g.newImage("objects/text/Hit.png", ImageFormat.ARGB8888);
        Assets.damageText = g.newImage("objects/text/Damage.png", ImageFormat.ARGB8888);

        Assets.AttackUp = g.newImage("objects/battleMisc/StatusAttackUp.png", ImageFormat.ARGB8888);
        Assets.AttackDown = g.newImage("objects/battleMisc/StatusAttackDown.png", ImageFormat.ARGB8888);
        Assets.DefenseUp = g.newImage("objects/battleMisc/StatusDefenseUp.png", ImageFormat.ARGB8888);
        Assets.DefenseDown = g.newImage("objects/battleMisc/StatusDefenseDown.png", ImageFormat.ARGB8888);

        Assets.KOImages = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            Assets.KOImages.add(g.newImage("objects/battleMisc/KOImage" + i + ".png", ImageFormat.ARGB8888));
        }

        Assets.WhiteNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Assets.WhiteNumbers.add(g.newImage("numbers/white/" + i + ".png", ImageFormat.ARGB8888));
        }
        Assets.HPSlash = g.newImage("numbers/HPSlash.png", ImageFormat.ARGB8888);

        Assets.GreenNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Assets.GreenNumbers.add(g.newImage("numbers/green/" + i + ".png", ImageFormat.ARGB8888));
        }

        Assets.YellowNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Assets.YellowNumbers.add(g.newImage("numbers/yellow/" + i + ".png", ImageFormat.ARGB8888));
        }

        Assets.RedNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Assets.RedNumbers.add(g.newImage("numbers/red/" + i + ".png", ImageFormat.ARGB8888));
        }

        //Assets.WeakNumbers = Assets.RedNumbers;

        Assets.GreyNumbers = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Assets.GreyNumbers.add(g.newImage("numbers/grey/" + i + ".png", ImageFormat.ARGB8888));
        }

        Assets.PartySelectScreen = g.newImage("screens/PartySelectScreen.png", ImageFormat.RGB565);
        Assets.AcceptEnable = g.newImage("buttons/AcceptEnabled.png", ImageFormat.ARGB8888);
        Assets.AcceptDisable = g.newImage("buttons/AcceptDisabled.png", ImageFormat.ARGB8888);
        Assets.NextPageEnable = g.newImage("buttons/NextPageEnabled.png", ImageFormat.ARGB8888);
        Assets.NextPageDisable = g.newImage("buttons/NextPageDisabled.png", ImageFormat.ARGB8888);
        Assets.PrevPageEnable = g.newImage("buttons/PrevPageEnabled.png", ImageFormat.ARGB8888);
        Assets.PrevPageDisable = g.newImage("buttons/PrevPageDisabled.png", ImageFormat.ARGB8888);
        Assets.LockSelection = g.newImage("objects/holders/LockSelection.png", ImageFormat.ARGB8888);
        Assets.BattleEnable = g.newImage("buttons/BattleEnabled.png", ImageFormat.ARGB8888);
        Assets.BattleDisable = g.newImage("buttons/BattleDisabled.png", ImageFormat.ARGB8888);
        Assets.ScrollBarFull = g.newImage("buttons/ScrollBarFull.png", ImageFormat.ARGB8888);
        Assets.ScrollBarShort = g.newImage("buttons/ScrollBarShort.png", ImageFormat.ARGB8888);
        Assets.InvalidChar = g.newImage("objects/holders/InvalidChar.png", ImageFormat.ARGB8888);
        Assets.RequiredCharHolder = g.newImage("objects/holders/RequiredCharHolder.png", ImageFormat.ARGB8888);

        Assets.CharacterInfoScreen = g.newImage("screens/CharacterInfoScreen.png", ImageFormat.RGB565);
        Assets.BattleCharacterInfoScreen = g.newImage("screens/BattleCharacterInfoScreen.png", ImageFormat.RGB565);
        Assets.BattleInfoScreen = g.newImage("screens/BattleInfoScreen.png", ImageFormat.RGB565);

        Assets.ElementImages = new ArrayList<>();
        Assets.ElementImages.add(g.newImage("elements/AirElement.png", ImageFormat.ARGB8888));
        Assets.ElementImages.add(g.newImage("elements/DarkElement.png", ImageFormat.ARGB8888));
        Assets.ElementImages.add(g.newImage("elements/EarthElement.png", ImageFormat.ARGB8888));
        Assets.ElementImages.add(g.newImage("elements/FireElement.png", ImageFormat.ARGB8888));
        Assets.ElementImages.add(g.newImage("elements/LightElement.png", ImageFormat.ARGB8888));
        Assets.ElementImages.add(g.newImage("elements/WaterElement.png", ImageFormat.ARGB8888));

        createRecruits();
        createParty();
        createEnemies();
        createBattles();

        BattleSelectScreen bss = new BattleSelectScreen(game);
        game.setScreen(bss);

    }

    private void createRecruits(){
        InputStream in = null;
        try {
            in = game.openConfig("config/characters.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            CharacterParser charParser = new CharacterParser();
            charParser.setGraphics(game.getGraphics());
            saxParser.parse(in, charParser);
        }
        catch (Exception e){
            e.printStackTrace();
            //Do better error handling
        }
        finally{
            if(in != null){
                try {
                    in.close();
                }
                catch(IOException e){
                }
            }
        }
    }

    private void createParty(){
        SharedPreferences prefs = game.getGamePreferences("ultrapoulet.wifeygame.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        Party.init(prefs);

        //This will get cleaned up later
        ArrayList<WifeyCharacter> party = new ArrayList<>();
        party.add(RecruitedCharacters.get(prefs.getString("party_0", "TEST-YUNO")));
        party.add(RecruitedCharacters.get(prefs.getString("party_1", "TEST-RENA")));
        party.add(RecruitedCharacters.get(prefs.getString("party_2", "TEST-KTNH")));
        party.add(RecruitedCharacters.get(prefs.getString("party_3", "TEST-ANNA")));
        party.add(RecruitedCharacters.get(prefs.getString("party_4", "TEST-SJGH")));
        party.add(RecruitedCharacters.get(prefs.getString("party_5", "TEST-YNDR")));
        party.add(RecruitedCharacters.get(prefs.getString("party_6", "TEST-PERI")));

        Party.setParty(party);
    }

    private void createEnemies(){
        InputStream in = null;
        try {
            in = game.openConfig("config/enemies.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            EnemyParser enemyParser = new EnemyParser();
            enemyParser.setGraphics(game.getGraphics());
            saxParser.parse(in, enemyParser);
        }
        catch (Exception e){
            e.printStackTrace();
            //Do better error handling
        }
        finally{
            if(in != null){
                try {
                    in.close();
                }
                catch(IOException e){
                }
            }
        }
    }

    private void createBattles(){
        InputStream in = null;
        try {
            in = game.openConfig("config/battles.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            BattleParser battleParser = new BattleParser();
            saxParser.parse(in, battleParser);
        }
        catch (Exception e){
            e.printStackTrace();
            //Do better error handling
        }
        finally{
            if(in != null){
                try {
                    in.close();
                }
                catch(IOException e){
                }
            }
        }
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
