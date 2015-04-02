package com.ngustafson247.supertictactoemobile;

import com.ngustafson247.framework.Game;
import com.ngustafson247.framework.Graphics;
import com.ngustafson247.framework.Input;
import com.ngustafson247.framework.Input.TouchEvent;
import com.ngustafson247.framework.Screen;

import java.util.List;

/**
 * Created by Nick on 3/21/2015.
 */
public class MainMenuScreen extends Screen {
    public MainMenuScreen(Game game) {
        super(game, false);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (TouchEvent event : touchEvents) {
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 160, 700, 400, 200)) {
                    game.setScreen(new OptionsScreen(game));
                }
            }
        }
    }

    public boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
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
        g.drawImage(Assets.tempBackground, 0, 0);
        g.drawImage(Assets.testButton, 160, 700);



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
