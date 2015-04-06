package com.ngustafson247.supertictactoemobile;

import android.graphics.Paint;
import android.util.Log;

import com.ngustafson247.framework.Graphics;
import com.ngustafson247.framework.Input;
import com.ngustafson247.framework.Input.TouchEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 4/5/2015.
 */
public class RadioButtonGroup {

    private int x, y;
    private String title;
    private List<RadioButton> radioButtons;
    Paint titlePaint;

    private String TAG = "Button-Group";

    public RadioButtonGroup(String groupTitle, int x, int y) {
        this.title = groupTitle;
        this.x = x;
        this.y = y;

        titlePaint = new Paint();
        titlePaint.setTextSize(70);
        radioButtons = new ArrayList<>();
    }

    public void addButton(String text) {
        radioButtons.add(new RadioButton(x, 25 + y + radioButtons.size() * 80 , text));
        if (radioButtons.size() == 1) {
            radioButtons.get(0).setChecked(true);
        }
    }

    public void checkButtonPress(TouchEvent event) {
        Log.d(TAG, "checking button presses");
        for (int i = 0; i < radioButtons.size(); i++) {
            Log.d(TAG, "Checking (" + event.x + ", " + event.y + ") against ("+ x + ", " + (y + i*80) + ")");
            if (inBoundsRect(event, x, y + i * 80, 50, 50)) {
                changeSelection(i);
                //radioButtons.get(i).setChecked(true);
            }
        }
    }

    public boolean inBoundsRect(TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1) {
            return true;
        } else {
            return false;
        }
    }

    public void changeSelection(int index) {
        if (!radioButtons.get(index).isChecked()) {
            radioButtons.get(index).setChecked(true);
            for (int i = 0; i < radioButtons.size(); i++) {
                if (i != index) {
                    radioButtons.get(i).setChecked(false);
                }
            }
        }
    }

    public void paintButtonGroup(Graphics g) {
        g.drawString(title, x - 30, y, titlePaint);

        for (RadioButton radioButton : radioButtons) {
            radioButton.paintButton(g);
        }
    }
}
