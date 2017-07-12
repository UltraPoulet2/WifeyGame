package ultrapoulet.wifeygame.screens;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleEnemy;

/**
 * Created by John on 7/11/2017.
 */

public class EnemyInfoScreen extends Screen {
    private Screen prevScreen;

    private static final int BG_X = 50;
    private int topY;

    private static final int TOP_HEIGHT = Assets.EnemyInfoScreenTop.getHeight();
    private static final int MID_HEIGHT = Assets.EnemyInfoScreenMid.getHeight();
    private static final int BOT_HEIGHT = Assets.EnemyInfoScreenBot.getHeight();
    private int totalHeight;

    private List<BattleCharacter> party;
    private BattleEnemy displayEnemy;

    public EnemyInfoScreen(Game game, Screen prevScreen, List<BattleCharacter> party) {
        super(game);
        this.prevScreen = prevScreen;
        this.party = party;
        totalHeight = TOP_HEIGHT + (MID_HEIGHT * this.party.size()) + BOT_HEIGHT;
        topY = (1280 - totalHeight) / 2;
    }

    public void setEnemy(BattleEnemy enemy){
        this.displayEnemy = enemy;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.EnemyInfoScreenTop, BG_X, topY);
        for(int i = 0; i < party.size(); i++){
            g.drawImage(Assets.EnemyInfoScreenMid, BG_X, topY + TOP_HEIGHT + (MID_HEIGHT * i));
        }
        g.drawImage(Assets.EnemyInfoScreenBot, BG_X, topY + TOP_HEIGHT + (MID_HEIGHT * party.size()));
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
        game.setScreen(prevScreen);
    }
}
