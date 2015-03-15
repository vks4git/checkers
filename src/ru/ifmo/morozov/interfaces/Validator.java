package ru.ifmo.morozov.interfaces;

import ru.ifmo.morozov.enums.State;

/**
 * Created by vks on 2/27/15.
 */
public interface Validator {

    State isLegal(int X1, int Y1, int X2, int Y2);

    boolean canMove(int x, int y);

}
