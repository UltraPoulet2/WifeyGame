package ultrapoulet.androidgame.framework;

import ultrapoulet.androidgame.framework.Graphics.ImageFormat;

/**
 * Created by John on 1/8/2016.
 */
public interface Image {
    int getWidth();
    int getHeight();
    ImageFormat getFormat();
    void dispose();
}
