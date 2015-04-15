package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vks on 2/26/15.
 */
public class AIPlayer implements Player {

    private int direction;
    private Colour colour;
    private String name;
    private Validator validator;

    public AIPlayer(Colour colour, String name, int direction, Validator validator) {
        this.colour = colour;
        this.name = name;
        this.direction = direction;
        this.validator = validator;
    }

    public Coordinates move(Field field, Pointer pointer) {
        Coordinates result = new Coordinates();
        result.x1 = 0;
        return result;
    }

    public Colour getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }

    public int getDirection() {
        return direction;
    }

    public boolean keyboardEntry() {
        return false;
    }
}
