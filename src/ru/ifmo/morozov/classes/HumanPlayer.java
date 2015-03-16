package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

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

    public void move(Field field) {
        int x1 = field.getCheckCoords().x;
        int y1 = field.getCheckCoords().y;
        int x2 = field.getSetCoords().x;
        int y2 = field.getSetCoords().y;
        if (!field.isChecked()) {
            field.move(x1, y1, x2, y2);
        }
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
