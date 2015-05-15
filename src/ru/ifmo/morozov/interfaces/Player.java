package ru.ifmo.morozov.interfaces;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.MoveSequence;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.classes.model.Pointer;
import ru.ifmo.morozov.enums.Colour;

/**
 * Created by vks on 2/22/15.
 */
public interface Player {

    MoveSequence move(Checker [][] matrix, Pointer pointer);

    Colour getColour();

    String getName();

    boolean keyboardEntry();

}
