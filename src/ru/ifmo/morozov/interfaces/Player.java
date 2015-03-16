package ru.ifmo.morozov.interfaces;

import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.enums.Colour;

/**
 * Created by vks on 2/22/15.
 */
public interface Player {

    void move(Field field);

    Colour getColour();

    String getName();

    int getDirection();

    boolean keyboardEntry();

}
