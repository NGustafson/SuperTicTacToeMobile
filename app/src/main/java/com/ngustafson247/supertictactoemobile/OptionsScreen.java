package com.ngustafson247.supertictactoemobile;

import android.graphics.Paint;

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

    private GameScreen oldGame;
    private RadioButtonGroup numBoardOptions;

    public OptionsScreen(Game game) {
        super(game, false);

        numBoardOptions = new RadioButtonGroup("Number of Boards", 50, 80);
        numBoardOptions.addButton("One Board");
        numBoardOptions.addButton("Nine Boards");

    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (TouchEvent event : touchEvents) {
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBoundsRect(event, 0, game.getFrameBufferHeight() - 410, 720, 200)) {
                    game.setScreen(oldGame);
                } else if (inBoundsRect(event, 0, game.getFrameBufferHeight() - 200, 720, 200)) {
                    game.setScreen(new GameScreen(game, this));
                } else {
                    numBoardOptions.checkButtonPress(event);
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

        //RadioButton testButton = new RadioButton(100, 100, "Single Button test");
        //testButton.paintButton(g);


        numBoardOptions.paintButtonGroup(g);

        g.drawImage(Assets.contGameButton, 0, game.getFrameBufferHeight() - 410);
        g.drawImage(Assets.newGameButton, 0, game.getFrameBufferHeight() - 200);
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

    public void setOldGame(GameScreen oldGame) {
        this.oldGame = oldGame;
    }

}
