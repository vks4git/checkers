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

    private boolean status;

    private int direction;

    private Colour colour;

    private String name;

    public HumanPlayer(Colour colour, String name) {
        this.colour = colour;
        status = false;
        this.name = name;
    }

    public void move(Field field, Validator validator) {
        boolean finished = false;
        int x1;
        int x2;
        int y1;
        int y2;
            field.setKeybdEntry();
            do {
                if (!field.isChecked()) {
                    x1 = field.getCheckCoords().x;
                    y1 = field.getCheckCoords().y;
                    x2 = field.getSetCoords().x;
                    y2 = field.getSetCoords().y;

                    if (validator.isLegal(x1, y1, x2, y2) == State.Legal) {
                        field.move(x1, y1, x2, y2);
                        if (Math.abs(x2 - x1) == 2) {
                            field.remove((x1 + x2)/2, (y1 + y2)/2);
                        }
                        finished = true;
                        field.reset();
                    } else if (validator.isLegal(x1, y1, x2, y2) == State.OneMoreMove) {
                        field.move(x1, y1, x2, y2);
                        field.remove((x1 + x2)/2, (y1 + y2)/2);
                        field.reset();
                    }
                }
            } while (!finished);
            field.unsetKeybdEntry();
    }

    public Colour getColour() {
        return colour;
    }

    public boolean canMove(Field field, Validator validator) {
        return true;
    }

    public boolean isVictorious() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
