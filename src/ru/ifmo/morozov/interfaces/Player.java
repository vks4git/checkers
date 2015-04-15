package ru.ifmo.morozov.interfaces;

import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.classes.model.Pointer;
import ru.ifmo.morozov.enums.Colour;

/**
 * Created by vks on 2/22/15.
 */
public interface Player {

    Coordinates move(Field field, Pointer pointer);

    Colour getColour();

    String getName();

    int getDirection();

    boolean keyboardEntry();

}
