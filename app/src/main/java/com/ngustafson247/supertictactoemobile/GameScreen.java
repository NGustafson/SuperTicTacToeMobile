package com.ngustafson247.supertictactoemobile;

import android.util.Log;

import com.ngustafson247.framework.Game;
import com.ngustafson247.framework.Graphics;
import com.ngustafson247.framework.Input;
import com.ngustafson247.framework.Input.TouchEvent;
import com.ngustafson247.framework.Screen;
import com.ngustafson247.framework.implementation.AndroidGraphics;

import java.util.List;

/**
 * Created by Nick on 3/21/2015.
 */
public class GameScreen extends Screen{
    private OptionsScreen optionsScreen;
    private GameLogic gameLogic;

    // Scale values
    private float currentScale, lastScale;
    private final float MIN_SCALE = 720.0f / 2180.0f;

    // Drag values
    private int originX, originY;

    // gameBoard image info
    private final int BOX_SIZE = 230;
    private final int LINE_SIZE = 5;
    private final int BORDER_SIZE = 10;
    private final int TOTAL_SIZE = (3 * BOX_SIZE) + (2 * (LINE_SIZE
            + BORDER_SIZE));


    // Log tag
    private final String TAG = "Game-Screen";

    public GameScreen(Game game, OptionsScreen optionsScreen) {
        super(game, true);
        this.optionsScreen = optionsScreen;
        gameLogic = new GameLogic();
        gameLogic.setGameOptions(9);

        currentScale = lastScale = 1.0f;
        originX = originY = 0;
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (TouchEvent event : touchEvents) {
            if (event.type == TouchEvent.TOUCH_UP) {

                // Reset lastScale at the end of an event
                lastScale = 1.0f;

                if (insideBox(event.x, event.y)) {
                    gameLogic.makePlay(boardIndex, boxI, boxJ);
                    gameLogic.logBoard();
                }
            } else if (event.type == TouchEvent.TOUCH_ZOOM) {
                Log.d(TAG, "lastScale: " + lastScale);
                if (lastScale == 1.0f) {
                    lastScale = event.scale;
                } else {
                    modifyScale(event.scale);
                }
            } else if (event.type == TouchEvent.TOUCH_DRAGGED) {
                modifyOrigin(event.dragX, event.dragY);
                Log.d(TAG, "origin: (" + originX + ", " + originY + ")");
            }
        }

    }

    private void modifyOrigin(int dragX, int dragY) {
        if (gameLogic.getNumBoards() == GameLogic.NumBoards.NINE) {
            if (originX + dragX > 0) {
                originX = 0;
            } else if (originX + dragX - 720 < (int) (-2180 * currentScale)) {
                originX = (int) (-2180 * currentScale) + 720;
                //Log.d(TAG, "case 2");
            } else {
                originX += dragX;
            }

            if (originY + dragY > 0) {
                originY = 0;
            } else if (originY + dragY - (game.getFrameBufferHeight() - 300) < (int) (-2180 * currentScale)) {
                originY = (int) (-2180 * currentScale) + game.getFrameBufferHeight() - 300;
                if (originY > 0) {
                    originY = 0;
                }
            } else {
                originY += dragY;
            }
        }
    }

    private void modifyScale(float scale) {
        if (gameLogic.getNumBoards() == GameLogic.NumBoards.NINE) {
            float adjustedScale = currentScale + (scale - lastScale);

            if (adjustedScale < MIN_SCALE) {
                adjustedScale = MIN_SCALE;
            } else if (adjustedScale > 1.0) {
                adjustedScale = 1.0f;
            }

            lastScale = scale;
            currentScale = adjustedScale;
        }
        modifyOrigin(0, 0);
    }

    private int boardX, boardY, boardIndex, boxI, boxJ;

    // Check if x,y coordinates are inside of an existing board
    private boolean insideBoard(int x, int y) {
        boardIndex = 0;

        for (GameBoard gameBoard1 : gameLogic.getGameBoards()) {
            boardX = gameBoard1.getBoardX();
            boardY = gameBoard1.getBoardY();
            if (x < boardX + TOTAL_SIZE && x > boardX) {
                if (y < boardY + TOTAL_SIZE && y > boardY) {
                    return true;
                }
            }
            boardIndex++;
        }
        return false;
    }

    // Check if x,y coordinates are inside of an existing box
    public boolean insideBox(int x, int y) {
        if (!insideBoard(x, y)) {
            return false;
        }

        x -= boardX + BORDER_SIZE;
        y -= boardY + BORDER_SIZE;

        if (x < BOX_SIZE) {
            boxI = 0;
        } else if (x > BOX_SIZE + LINE_SIZE - 1
                && x < 2 * BOX_SIZE + LINE_SIZE) {
            boxI = 1;
        } else if (x > 2 * (BOX_SIZE + LINE_SIZE) - 1
                && x < 3 * BOX_SIZE + 2 * LINE_SIZE) {
            boxI = 2;
        } else {
            return false;
        }

        if (y < BOX_SIZE) {
            boxJ = 0;
        } else if (y > BOX_SIZE + LINE_SIZE - 1
                && y < 2 * BOX_SIZE + LINE_SIZE) {
            boxJ = 1;
        } else if (y > 2 * (BOX_SIZE + LINE_SIZE) - 1
                && y < 3 * BOX_SIZE + 2 * LINE_SIZE) {
            boxJ = 2;
        } else {
            return false;
        }
        return true;
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

        //g.drawImage(Assets.SingleBoard, 10, 10);
        //g.drawScaledImage(Assets.SingleBoard, originX + (int) (10 * currentScale), originY + (int) (10 * currentScale), (int) (700 * currentScale), (int) (700 * currentScale), 0, 0, 700, 700);
        paintBoards();
        paintMarkers();
        g.drawScaledImage(Assets.blankBackground, 0, game.getFrameBufferHeight() - 300, 720, 300, 0, 0, 720, 300);
    }

    public void paintBoards() {
        Graphics g = game.getGraphics();

        if (gameLogic.getNumBoards() == GameLogic.NumBoards.ONE) {
            g.drawScaledImage(Assets.SingleBoard, originX + (int) (10 * currentScale), originY + (int) (10 * currentScale), (int) (700 * currentScale), (int) (700 * currentScale), 0, 0, 700, 700);
        } else {
            int boardX, boardY;
            for (GameBoard gameBoard : gameLogic.getGameBoards()) {
                boardX = gameBoard.getBoardX();
                boardY = gameBoard.getBoardY();

                g.drawScaledImage(Assets.SingleBoard,
                        originX + (int) ((boardX + 10) * currentScale),
                        originY + (int) ((boardY + 10) * currentScale),
                        (int) (700 * currentScale),
                        (int) (700 * currentScale),
                        0, 0, 700, 700);

                g.drawScaledImage(Assets.SingleBoardBlackBorder,
                        originX + (int) (boardX * currentScale),
                        originY + (int) (boardY * currentScale),
                        (int) (720 * currentScale),
                        (int) (720 * currentScale),
                        0, 0, 720, 720);
            }
        }
    }

    public void paintMarkers() {
        int boardX, boardY;
        Graphics g = game.getGraphics();

        // Place markers on boards
        for (GameBoard gameBoard1 : gameLogic.getGameBoards()) {
            boardX = gameBoard1.getBoardX();
            boardY = gameBoard1.getBoardY();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameBoard1.getBoxState(i, j) == 'X') {
                        g.drawScaledImage(Assets.XMarker,
                                originX + (int) ((boardX + (i * (BOX_SIZE + LINE_SIZE) - 1) + BORDER_SIZE + 34) * currentScale),
                                originY + (int) ((boardY + (j * (BOX_SIZE + LINE_SIZE) - 1) + BORDER_SIZE + 34) * currentScale),
                                (int) (160 * currentScale),
                                (int) (160 * currentScale),
                                0, 0, 160, 160);
                    } else if (gameBoard1.getBoxState(i, j) == 'O') {
                        g.drawScaledImage(Assets.OMarker,
                                originX + (int) ((boardX + (i * (BOX_SIZE + LINE_SIZE) - 1) + BORDER_SIZE + 34) * currentScale),
                                originY + (int) ((boardY + (j * (BOX_SIZE + LINE_SIZE) - 1) + BORDER_SIZE + 34) * currentScale),
                                (int) (160 * currentScale),
                                (int) (160 * currentScale),
                                0, 0, 160, 160);
                    }
                }
            }
        }
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
        optionsScreen.setOldGame(this);
        game.setScreen(optionsScreen);
    }
}
