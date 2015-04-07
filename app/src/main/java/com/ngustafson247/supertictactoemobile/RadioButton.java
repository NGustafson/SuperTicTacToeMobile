package com.ngustafson247.supertictactoemobile;

import android.graphics.Paint;
import android.util.Log;

import com.ngustafson247.framework.Graphics;

/**
 * Created by Nick on 4/5/2015.
 */
public class RadioButton {

    private boolean isChecked;
    private int x, y;
    private String text;
    private Paint paint;

    public RadioButton(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;

        isChecked = false;
        paint = new Paint();
        paint.setTextSize(50);
    }

    public void paintButton(Graphics g) {
        if (isChecked) {
            g.drawScaledImage(Assets.radioButtonCheck, x, y, 70, 70, 0, 0, 300, 300);
        } else {
            g.drawScaledImage(Assets.radioButtonUncheck, x, y, 70, 70, 0, 0, 300, 300);
        }

        //g.drawImage(Assets.radioButtonUncheck, x, y);
        g.drawString(text, x + 100, y + 48, paint);
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        Log.d("RadioButton", "button was pressed");
    }

    public boolean isChecked() {
        return isChecked;
    }
}
