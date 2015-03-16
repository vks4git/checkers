package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

/**
 * Created by vks on 2/22/15.
 */
public class Field {

    private Checker matrix[][];
    private Pointer pointer;
    private boolean checked;
    private Colour colour;
    private Pointer checkCoords;
    private Pointer setCoords;
    private boolean keyboardEntry;
    private Player turn;
    private Validator validator;
    private boolean finished;

    public Field(Colour colour1, Colour colour2, Validator validator) {
        matrix = new Checker[8][8];
        pointer = new Pointer();
        checked = false;
        checkCoords = new Pointer();
        setCoords = new Pointer();
        keyboardEntry = false;

        pointer.x = 4;
        pointer.y = 4;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i + j) % 2 == 1) {
                    matrix[i][j] = new Checker(colour2, CheckerType.Simple);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 7; j > 4; j--) {
                if ((i + j) % 2 == 1) {
                    matrix[i][j] = new Checker(colour1, CheckerType.Simple);
                }
            }
        }
        this.validator = validator;
        finished = false;
    }


    public boolean isFree(int x, int y) {
        if ((x > 7) || (x < 0) || (y > 7) || (y < 0)) {
            return false;
        }
        return (matrix[x][y] == null);
    }

    public final Checker[][] getMatrix() {
        return matrix;
    }

    private void free(int X, int Y) {
        matrix[X][Y] = null;
    }

    public void move(int X1, int Y1, int X2, int Y2) {
        finished = false;
        State state = validator.isLegal(this, X1, Y1, X2, Y2);
        if (state == State.Legal) {
            matrix[X2][Y2] = matrix[X1][Y1];
            free(X1, Y1);
            finished = true;
            if (Math.abs(X1 - X2) == 2) {
                free((X1 + X2) / 2, (Y1 + Y2) / 2);
            }
        } else if (state == State.OneMoreMove) {
            matrix[X2][Y2] = matrix[X1][Y1];
            free(X1, Y1);
            if (Math.abs(X1 - X2) == 2) {
                free((X1 + X2) / 2, (Y1 + Y2) / 2);
            }
        }
    }

    public final Pointer getPointer() {
        return pointer;
    }

    public void resetPointer(int x, int y) {
        if ((pointer.x + x < 8) && (pointer.x + x >= 0)) {
            pointer.x += x;
        }
        if ((pointer.y + y < 8) && (pointer.y + y >= 0)) {
            pointer.y += y;
        }
    }

    public boolean isChecked() {
        return checked;
    }

    public void check() {
        if (matrix[pointer.x][pointer.y] != null) {
            colour = matrix[pointer.x][pointer.y].getColour();
            if (turn.getColour() == colour) {
                checkCoords.x = pointer.x;
                checkCoords.y = pointer.y;
                checked = true;
            }
        }
    }

    public void uncheck() {
        checked = false;
        setCoords.x = pointer.x;
        setCoords.y = pointer.y;
    }

    public final Colour getColour() {
        return colour;
    }

    public void setKeybdEntry() {
        keyboardEntry = true;
    }

    public void unsetKeybdEntry() {
        keyboardEntry = false;
    }

    public boolean getKeybdEntry() {
        return keyboardEntry;
    }

    public void setTurn(Player player) {
        turn = player;
    }

    public final Player getTurn() {
        return turn;
    }

    public void reset() {
        checkCoords.x = 0;
        checkCoords.y = 0;
        setCoords.x = 0;
        setCoords.y = 0;
    }

    public final Pointer getSetCoords() {
        return setCoords;
    }

    public final Pointer getCheckCoords() {
        return checkCoords;
    }

    public boolean canMove(Player player) {
        return true;
    }

    public boolean isFinished() {
        return finished;
    }

}