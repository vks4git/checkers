package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.interfaces.Player;

/**
 * Created by vks on 2/26/15.
 */
public class HumanPlayer implements Player {

    private int direction;
    private Colour colour;
    private String name;

    public HumanPlayer(Colour colour, String name, int direction) {
        this.colour = colour;
        this.name = name;
        this.direction = direction;
    }

    public Coordinates move(Field field, Pointer pointer) {
        Coordinates result = new Coordinates();
        result.x1 = pointer.getCheckPosition().x;
        result.y1 = pointer.getCheckPosition().y;
        result.x2 = pointer.getReleasePosition().x;
        result.y2 = pointer.getReleasePosition().y;
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
        return true;
    }
}
