package com.ngustafson247.supertictactoemobile;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 3/22/2015.
 */
public class GameLogic {

    public enum NumBoards {
        ONE, NINE
    }

    private enum Player {
        PLAYER1, PLAYER2
    }

    private enum PlayerType {
        HUMAN, COMPUTER
    }

    private PlayerType player1, player2;
    private Player currentTurn;
    private int currentBoard;

    private NumBoards numBoards;
    //private GameType gameType;


    private final String TAG = "Game-Logic";

    private List<GameBoard> gameBoards = new ArrayList<>();

    public GameLogic() {}

    private void initGameBoards() {
        if (gameBoards.isEmpty()) {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    GameBoard tempBoard = new GameBoard();
                    tempBoard.setBoardX((720 * i));
                    tempBoard.setBoardY((720 * j));

                    gameBoards.add(tempBoard);
                }
            }
        }
        for (GameBoard gameBoard1 : gameBoards) {
            gameBoard1.resetBoard();
        }

    }

    // Set game options specified on options menu
    public void setGameOptions(int boards) {
        if (boards == 1) {
            numBoards = NumBoards.ONE;

            // Initialize current board
            currentBoard = 0;

        } else if (boards == 9) {
            numBoards = NumBoards.NINE;

            // Initialize current board to 4 (Middle Board)
            currentBoard = 4;

        }

        // Clear the game board
        initGameBoards();

        // Set current player turn
        currentTurn = Player.PLAYER1;

        //gameType = GameType.LOCAL_MP;
        player1 = PlayerType.HUMAN;
        player2 = PlayerType.HUMAN;

    }

    // This method is called after a box is clicked
    // Take care of player turns
    // Confirms if box can be clicked and carries out proper action
    public void makePlay(int boardIndex, int boxI, int boxJ) {
        if (boardIndex == currentBoard) {
            if (gameBoards.get(boardIndex).getBoxState(boxI, boxJ) == ' ') {
                if (currentTurn == Player.PLAYER1 && player1 == PlayerType.HUMAN) {
                    gameBoards.get(boardIndex).setBoxState(boxI, boxJ, 'X');
                    gameBoards.get(boardIndex).addMarker();
                    changeTurn(boxI, boxJ);
                } else if (currentTurn == Player.PLAYER2 && player2 == PlayerType.HUMAN) {
                    gameBoards.get(boardIndex).setBoxState(boxI, boxJ, 'O');
                    gameBoards.get(boardIndex).addMarker();
                    changeTurn(boxI, boxJ);
                }

            }
        }
    }

    private void changeTurn(int boxI, int boxJ) {
        if (currentTurn == Player.PLAYER1) {
            currentTurn = Player.PLAYER2;
        } else {
            currentTurn = Player.PLAYER1;
        }

        if (numBoards == NumBoards.NINE) {
            changeBoard((boxJ * 3) + boxI);
        }

    }

    private void changeBoard(int newBoard) {

        int firstTry = newBoard;

        while (gameBoards.get(newBoard).isFull()) {
            newBoard++;
            if (newBoard == firstTry) {
                break;
            }
        }

        currentBoard = newBoard;
    }


    public void logBoard() {
        GameBoard gameBoard = gameBoards.get(0);
        Log.d(TAG, "\nStart board log");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Log.d(TAG, "(" + i + ", " + j + "): " + gameBoard.getBoxState(i, j));
            }
        }
        Log.d(TAG, "End board log\n");
    }

    public List<GameBoard> getGameBoards() {
        return gameBoards;
    }

    public int getCurrentBoard() {
        return currentBoard;
    }

    public NumBoards getNumBoards() {
        return numBoards;
    }

}
