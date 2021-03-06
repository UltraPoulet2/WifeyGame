package ultrapoulet.wifeygame;

import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.implementation.AndroidGame;
import ultrapoulet.wifeygame.screens.LoadingScreen;

/**
 * Created by John on 3/12/2016.
 */
public class WifeyGame extends AndroidGame {

    @Override
    public void onBackPressed(){
        getCurrentScreen().backButton();
    }

    @Override
    public Screen getInitScreen(){
        return new LoadingScreen(this);
    }

    @Override
    public void onPause() {
        super.onPause();


    }
}
