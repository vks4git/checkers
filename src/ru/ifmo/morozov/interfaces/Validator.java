package ru.ifmo.morozov.interfaces;

import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.enums.State;

/**
 * Created by vks on 2/27/15.
 */
public interface Validator {

    State isLegal(Field field, Coordinates move, Player player);

    boolean canMove(Field field, Player player);

}
