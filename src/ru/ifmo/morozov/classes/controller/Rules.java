package ru.ifmo.morozov.classes.controller;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import java.io.FileNotFoundException;

/**
 * Created by vks on 2/27/15.
 */
public class Rules implements Validator {

    private Field field;


    private boolean canBeat(int x, int y) {
        int u;
        int v;

        return false;
    }

    public State isLegal(Field field, Coordinates move, Player player) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        Colour colour = player.getColour();
        int direction = player.getDirection();
        Checker checker = field.getMatrix()[x1][y1];

        if (checker.getType() == CheckerType.Simple) {
            if (Math.abs(x2 - x1) != 2) {
                return State.Illegal;
            }
            if (Math.abs(y2 - y1) != 2) {
                return State.Illegal;
            }

        } else {

        }
        return State.Illegal;
    }

    public boolean canMove(Field field, Player player) {
        return true;
    }
}
