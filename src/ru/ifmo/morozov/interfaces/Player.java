package ru.ifmo.morozov.interfaces;

import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.enums.Colour;

/**
 * Created by vks on 2/22/15.
 */
public interface Player {

    void move(Field field, Validator validator);

    Colour getColour();

    boolean canMove(Field field, Validator validator);

    boolean isVictorious();

    void setStatus(boolean status);

    String getName();

    int getDirection();

    void setDirection(int direction);

}
