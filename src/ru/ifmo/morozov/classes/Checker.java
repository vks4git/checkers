package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;

/**
 * Created by vks on 2/22/15.
 */
public class Checker {

    private Colour colour;
    private CheckerType type;


    public Checker(Colour colour, CheckerType type) {
        this.colour = colour;
        this.type = type;
    }

    public Colour getColour() {
        return colour;
    }

    public CheckerType getType() {
        return type;
    }
}

