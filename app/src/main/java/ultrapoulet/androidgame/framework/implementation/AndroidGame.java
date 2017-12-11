package ultrapoulet.androidgame.framework.implementation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

import ultrapoulet.androidgame.framework.Audio;
import ultrapoulet.androidgame.framework.FileIO;
import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;

/**
 * Created by John on 1/8/2016.
 */
public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 800 : 1280;
        int frameBufferHeight = isPortrait ? 1280 : 800;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        float scaleX = (float) frameBufferWidth
                / size.x;
        float scaleY = (float) frameBufferHeight
                / size.y;

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getInitScreen();
        setContentView(renderView);
    }

    @Override
    public void onResume() {
        super.onResume();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {
        return screen;
    }

    public InputStream openConfig(String filename) {
        InputStream in = null;
        try {
            in = getAssets().open(filename);
            return in;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load config from asset '"
                    + filename + "'");
        }
    }

    public SharedPreferences getGamePreferences(String name, int mode) {
        return this.getSharedPreferences(name, mode);
    }
}
