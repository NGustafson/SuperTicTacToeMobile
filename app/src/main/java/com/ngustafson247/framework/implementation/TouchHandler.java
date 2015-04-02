package com.ngustafson247.framework.implementation;

import android.view.View.OnTouchListener;

import com.ngustafson247.framework.Input.TouchEvent;

import java.util.List;

/**
 * Created by Nick on 3/19/2015.
 */
public interface TouchHandler extends OnTouchListener{
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
