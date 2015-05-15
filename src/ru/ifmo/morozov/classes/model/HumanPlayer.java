package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.MoveSequence;
import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.interfaces.Player;

/**
 * Created by vks on 2/26/15.
 */
public class HumanPlayer implements Player {

    private Colour colour;
    private String name;

    public HumanPlayer(Colour colour, String name) {
        this.colour = colour;
        this.name = name;
    }

    public MoveSequence move(Checker[][] matrix, Pointer pointer) {
        while (!pointer.isCompleted()) {
            delay(1);
        }
        Coordinates move = new Coordinates();
        move.x1 = pointer.getCheckPosition().x;
        move.y1 = pointer.getCheckPosition().y;
        move.x2 = pointer.getReleasePosition().x;
        move.y2 = pointer.getReleasePosition().y;
        pointer.reset();
        MoveSequence sequence = new MoveSequence();
        sequence.add(move);
        return sequence;
    }

    public Colour getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }

    public boolean keyboardEntry() {
        return true;
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
