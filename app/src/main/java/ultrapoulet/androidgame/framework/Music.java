package ultrapoulet.androidgame.framework;

/**
 * Created by John on 1/8/2016.
 */
public interface Music {
    void play();

    void stop();

    void pause();

    void setLooping(boolean looping);

    void setVolume(float volume);

    boolean isPlaying();

    boolean isStopped();

    boolean isLooping();

    void dispose();

    void seekBegin();
}
