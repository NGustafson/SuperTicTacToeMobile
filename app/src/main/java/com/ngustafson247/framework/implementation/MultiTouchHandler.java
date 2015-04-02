package com.ngustafson247.framework.implementation;

import android.graphics.PointF;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ngustafson247.framework.Input;
import com.ngustafson247.framework.Input.TouchEvent;
import com.ngustafson247.framework.Pool;
import com.ngustafson247.framework.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 3/19/2015.
 */
public class MultiTouchHandler implements TouchHandler {
    private static final int MAX_TOUCHPOINTS = 10;

    boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    int[] touchX = new int[MAX_TOUCHPOINTS];
    int[] touchY = new int[MAX_TOUCHPOINTS];
    int[] id = new int[MAX_TOUCHPOINTS];
    Pool<TouchEvent> touchEventPool;
    List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
    float scaleX;
    float scaleY;

    private String mode;
    float oldDist, newDist;
    PointF point;

    public MultiTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;

        mode = "NONE";
        oldDist = newDist = 0.0f;
        point = new PointF();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >>
                    MotionEvent.ACTION_POINTER_ID_SHIFT;
            int pointerCount = event.getPointerCount();
            TouchEvent touchEvent;
            for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
                if (i >= pointerCount) {
                    isTouched[i] = false;
                    id[i] = -1;
                    continue;
                }
                int pointerId = event.getPointerId(i);
                if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    // If it's an up/down/cancel/out event, mask the id to see if we should
                    // process it for this touch point
                    continue;
                }
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent = touchEventPool.newObject();

                        oldDist = spacing(event);
                        if (oldDist > 10f) {
                            Log.d("TAG", "This happened");
                            mode = "ZOOM";
                            midPoint(point, event);
                        }

                        touchEvent.type = TouchEvent.TOUCH_DOWN;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                        touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchEventsBuffer.add(touchEvent);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:

                        mode = "NONE";

                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_UP;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                        touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                        Log.d("TAG", "Touch Up: " + touchEvent.x + ", " + touchEvent.y);
                        isTouched[i] = false;
                        id[i] = -1;
                        touchEventsBuffer.add(touchEvent);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        touchEvent = touchEventPool.newObject();

                        if (mode.equals("ZOOM")) {
                            float newDist = spacing(event);
                            if (newDist > 10f) {
                                touchEvent.type = TouchEvent.TOUCH_ZOOM;
                                touchEvent.scale = newDist / oldDist;
                                touchEvent.x = touchX[i] = (int) (point.x * scaleX);
                                touchEvent.y = touchY[i] = (int) (point.y * scaleY);
                                Log.d("TAG", "Zoom test-Scale: " + touchEvent.scale);
                            }
                        } else {
                            touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                            touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                            touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                            Log.d("TAG", "DRAG");
                        }


                        touchEvent.pointer = pointerId;
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchEventsBuffer.add(touchEvent);
                        break;
                }
            }
            return true;
        }
    }

    private float spacing(MotionEvent event) {
        if (event.getPointerCount() >= 2) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return FloatMath.sqrt(x * x + y * y);
        } else {
            return 0.0f;
        }

    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getY(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS) {
                return false;
            } else {
                return isTouched[index];
            }
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS) {
                return 0;
            } else {
                return touchX[index];
            }
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS) {
                return 0;
            } else {
                return touchY[index];
            }
        }
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                touchEventPool.free(touchEvents.get(i));
            }
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

    // returns the index for a given pointerId or -1 if no index;
    private int getIndex(int pointerId) {
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if (id[i] == pointerId) {
                return i;
            }
        }
        return -1;
    }
}
