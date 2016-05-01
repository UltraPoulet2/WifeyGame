package ultrapoulet.androidgame.framework;

import android.view.View;

import java.util.List;

/**
 * Created by John on 1/8/2016.
 */
public interface TouchHandler extends View.OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<Input.TouchEvent> getTouchEvents();
}
