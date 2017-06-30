package ultrapoulet.androidgame.framework;

import android.content.SharedPreferences;

import java.io.InputStream;

/**
 * Created by John on 1/8/2016.
 */
public interface Game {

    Audio getAudio();

    Input getInput();

    FileIO getFileIO();

    Graphics getGraphics();

    void setScreen(Screen screen);

    Screen getCurrentScreen();

    Screen getInitScreen();

    InputStream openConfig(String filename);

    SharedPreferences getGamePreferences(String name, int mode);
}
