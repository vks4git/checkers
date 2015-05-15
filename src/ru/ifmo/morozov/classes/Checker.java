package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;

/**
 * Created by vks on 2/22/15.
 */
public class Checker {

    private Colour colour;
    private CheckerType type;
    private int direction;


    public Checker(Colour colour, CheckerType type, int direction) {
        this.colour = colour;
        this.type = type;
        this.direction = direction;
    }

    public Colour getColour() {
        return colour;
    }

    public int getDirection() {
        return direction;
    }

    public CheckerType getType() {
        return type;
    }

    public void setType(CheckerType type) {
        this.type = type;
    }
}

