package ultrapoulet.wifeygame;

import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.implementation.AndroidGame;

/**
 * Created by John on 3/12/2016.
 */
public class WifeyGame extends AndroidGame {
    @Override
    public Screen getInitScreen(){
        return new LoadingScreen(this);
    }
}
