package ru.ifmo.morozov.interfaces;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;

/**
 * Created by vks on 2/27/15.
 */
public interface Validator {

    State verify(Checker[][] matrix, Coordinates move, Colour colour);

    boolean canMove(Checker [][] matrix, Colour colour);

    boolean isBeating(Checker[][] matrix, Coordinates move, Colour colour);

}
