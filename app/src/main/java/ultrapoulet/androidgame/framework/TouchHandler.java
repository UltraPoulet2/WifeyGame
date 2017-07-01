package ultrapoulet.androidgame.framework;

import android.view.View;

import java.util.List;

/**
 * Created by John on 1/8/2016.
 */
public interface TouchHandler extends View.OnTouchListener {
    boolean isTouchDown(int pointer);

    int getTouchX(int pointer);

    int getTouchY(int pointer);

    List<Input.TouchEvent> getTouchEvents();
}
