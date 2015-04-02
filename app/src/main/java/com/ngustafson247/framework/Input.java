package com.ngustafson247.framework;

import java.util.List;

/**
 * Created by Nick on 3/18/2015.
 */

public interface Input {

    public static class TouchEvent {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;
        public static final int TOUCH_HOLD = 3;
        public static final int TOUCH_ZOOM = 4;

        public int type;
        public int x, y;
        public int pointer;
        public float scale;
    }

    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
