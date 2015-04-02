package com.ngustafson247.framework;

import com.ngustafson247.framework.Graphics.ImageFormat;

/**
 * Created by Nick on 3/18/2015.
 */

public interface Image {
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
}
