package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.R2Point;
import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;

/**
 * Created by vks on 15/04/15.
 */
public class Pointer {
    private R2Point currentPosition;
    private R2Point checkPosition;
    private R2Point releasePosition;
    private Checker[][] matrix;
    private boolean checked;
    private boolean usePointer;
    private boolean completed;
    private Colour turn;
    private CheckerType type;

    public Pointer(Checker[][] matrix, int x, int y) {
        currentPosition = new R2Point();
        currentPosition.x = x;
        currentPosition.y = y;
        checkPosition = new R2Point();
        releasePosition = new R2Point();
        checked = false;
        usePointer = false;
        completed = false;
        this.matrix = matrix;
    }

    public boolean move(int xOffset, int yOffset) {
        if ((currentPosition.x + xOffset < 8) && (currentPosition.x + xOffset >= 0)) {
            currentPosition.x += xOffset;
        } else {
            return false;
        }
        if ((currentPosition.y + yOffset < 8) && (currentPosition.y + yOffset >= 0)) {
            currentPosition.y += yOffset;
        } else {
            return false;
        }
        return true;
    }

    public final R2Point getCurrentPosition() {
        return currentPosition;
    }

    public final R2Point getCheckPosition() {
        return checkPosition;
    }

    public final R2Point getReleasePosition() {
        return releasePosition;
    }

    public void get() {
        if (!checked) {
            if ((matrix[currentPosition.x][currentPosition.y] != null) && (matrix[currentPosition.x][currentPosition.y].getColour() == turn)) {
                checked = true;
                completed = false;
                checkPosition.x = currentPosition.x;
                checkPosition.y = currentPosition.y;
                type = matrix[currentPosition.x][currentPosition.y].getType();
            }
        }
    }

    public void release() {
        if (checked) {
            checked = false;
            releasePosition.x = currentPosition.x;
            releasePosition.y = currentPosition.y;
            completed = true;
        }
    }

    public void reset() {
        completed = false;
        checkPosition.x = 0;
        checkPosition.y = 0;
        releasePosition.x = 0;
        releasePosition.y = 0;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isUsed() {
        return usePointer;
    }

    public void usePointer() {
        usePointer = true;
    }

    public void doNotUsePointer() {
        usePointer = false;
    }

    public void setTurn(Colour turn) {
        this.turn = turn;
    }

    public Colour getTurn() {
        return turn;
    }

    public CheckerType getType() {
        return type;
    }

    public boolean isCompleted() {
        return completed;
    }
}
