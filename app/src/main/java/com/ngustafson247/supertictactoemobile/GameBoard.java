package com.ngustafson247.supertictactoemobile;

/**
 * Created by Nick on 3/22/2015.
 */
public class GameBoard {

    private char[][] gameBoardState = new char[3][3];

    private int boardX, boardY;

    private int numMarkers;

    public enum Owner {
        PLAYER1, PLAYER2, NONE
    }

    private Owner currentOwner;

    public GameBoard() {

    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoardState[i][j] = ' ';
            }
        }

        numMarkers = 0;
        currentOwner = Owner.NONE;
    }

    public Owner getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(Owner owner) {
        currentOwner = owner;
    }

    public boolean isOwned() {
        return currentOwner != Owner.NONE;
    }

    public boolean checkForOwnership(char playerCharacter) {
        for (int value = 0; value < 3; value++) {
            if (gameBoardState[value][0] == playerCharacter &&
                    gameBoardState[value][1] == playerCharacter &&
                    gameBoardState[value][2] == playerCharacter) {
                return true;
            }
            if (gameBoardState[0][value] == playerCharacter &&
                    gameBoardState[1][value] == playerCharacter &&
                    gameBoardState[2][value] == playerCharacter) {
                return true;
            }
        }

        if (gameBoardState[0][0] == playerCharacter &&
                gameBoardState[1][1] == playerCharacter &&
                gameBoardState[2][2] == playerCharacter) {
            return true;
        }

        if (gameBoardState[2][0] == playerCharacter &&
                gameBoardState[1][1] == playerCharacter &&
                gameBoardState[0][2] == playerCharacter) {
            return true;
        }

        return false;
    }

    public void addMarker() {
        numMarkers++;
    }

    public boolean isFull() {
        if (numMarkers == 9) {
            System.out.println(" a board is full");
            return true;
        } else return false;
    }

    public void setBoxState(int boxI, int boxJ, char marker) {
        gameBoardState[boxI][boxJ] = marker;
    }

    public char getBoxState(int boxI, int boxJ) {
        return gameBoardState[boxI][boxJ];
    }

    public int getBoardX() {
        return boardX;
    }

    public void setBoardX(int BoardX) {
        this.boardX = BoardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public void setBoardY(int BoardY) {
        this.boardY = BoardY;
    }
}
