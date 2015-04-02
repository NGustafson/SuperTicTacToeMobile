package com.ngustafson247.supertictactoemobile;

import com.ngustafson247.framework.Game;
import com.ngustafson247.framework.Graphics;
import com.ngustafson247.framework.Input;
import com.ngustafson247.framework.Input.TouchEvent;
import com.ngustafson247.framework.Screen;

import java.util.List;

/**
 * Created by Nick on 3/22/2015.
 */
public class OptionsScreen extends Screen {

    public OptionsScreen(Game game) {
        super(game, false);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (TouchEvent event : touchEvents) {
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBoundsRect(event, 0, 1080, 720, 200)) {
                    game.setScreen(new GameScreen(game, this));
                }
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

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.blankBackground, 0, 0);
        g.drawImage(Assets.contGameButton, 0, 870);
        g.drawImage(Assets.newGameButton, 0, 1080);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}
