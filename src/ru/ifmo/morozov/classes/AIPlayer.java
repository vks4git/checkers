package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import java.util.Random;

/**
 * Created by vks on 2/26/15.
 */
public class AIPlayer implements Player {

    private int direction;

    private Colour colour;

    private String name;

    public AIPlayer(Colour colour, String name, int direction) {
        this.colour = colour;
        this.name = name;
        this.direction = direction;
    }

    public void move(Field field) {
        do {} while (true);
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
