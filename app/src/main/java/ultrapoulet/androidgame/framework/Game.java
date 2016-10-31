package ultrapoulet.androidgame.framework;

import android.content.SharedPreferences;

import java.io.InputStream;

/**
 * Created by John on 1/8/2016.
 */
public interface Game {

    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();

    public InputStream openConfig(String filename);

    public SharedPreferences getGamePreferences(String name, int mode);
}
