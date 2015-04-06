package com.ngustafson247.supertictactoemobile;

import com.ngustafson247.framework.Game;
import com.ngustafson247.framework.Graphics;
import com.ngustafson247.framework.Graphics.ImageFormat;
import com.ngustafson247.framework.Image;
import com.ngustafson247.framework.Screen;

/**
 * Created by Nick on 3/21/2015.
 */
public class LoadingScreen extends Screen{
    public LoadingScreen(Game game) {
        super(game, false);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.tempBackground = g.newImage("BackGround.png", ImageFormat.RGB565);
        Assets.testButton = g.newImage("TestButton.png", ImageFormat.RGB565);
        Assets.blankBackground = g.newImage("Blank.png", ImageFormat.RGB565);

        Assets.contGameButton = g.newImage("ContinueButton.png", ImageFormat.RGB565);
        Assets.newGameButton = g.newImage("NewGameButton.png", ImageFormat.RGB565);

        Assets.radioButtonUncheck = g.newImage("radiobutton-unchecked-md.png", ImageFormat.ARGB4444);
        Assets.radioButtonCheck = g.newImage("radiobutton-checked-sm-md.png", ImageFormat.ARGB4444);


        Assets.SingleBoardBlackBorder = g.newImage("BlackBorder.png", ImageFormat.ARGB4444);
        Assets.SingleBoard = g.newImage("SingleBoard2.png", ImageFormat.RGB565);
        Assets.XMarker = g.newImage("XMarker.png", ImageFormat.ARGB4444);
        Assets.OMarker = g.newImage("OMarker.png", ImageFormat.ARGB4444);


        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void paint(float deltaTime) {

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
