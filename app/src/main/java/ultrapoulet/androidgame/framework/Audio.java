package ultrapoulet.androidgame.framework;

/**
 * Created by John on 1/8/2016.
 */
public interface Audio {
    Music createMusic(String file);

    Sound createSound(String file);
}
