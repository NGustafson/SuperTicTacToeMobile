package com.ngustafson247.supertictactoemobile;

import com.ngustafson247.framework.Screen;
import com.ngustafson247.framework.implementation.AndroidGame;

/**
 * Created by Nick on 3/20/2015.
 */
public class SuperTicTacToeMobile extends AndroidGame {
    @Override
    public Screen getInitScreen() {
        return new LoadingScreen(this);
    }
}
