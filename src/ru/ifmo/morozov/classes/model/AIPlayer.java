package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Coordinates;
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
        return getMoves(field).get(0);
    }

    public Colour getColour() {
        return colour;
    }

    private List<Coordinates> getMoves(Field field) {
        int xOffs;
        int yOffs;
        Coordinates move = new Coordinates();
        List<Coordinates> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!field.isFree(i, j)) {
                    if (field.getMatrix()[i][j].getColour() == colour) {

                        for (int u = -1; u < 2; u += 2) {
                            for (int v = -1; v < 2; v += 2) {
                                xOffs = 0;
                                yOffs = 0;

                                do {
                                    xOffs += u;
                                    yOffs += v;
                                    move.x1 = i;
                                    move.y1 = j;
                                    move.x2 = i + xOffs;
                                    move.y2 = j + yOffs;
                                    if (validator.isLegal(field, move, this) != State.Illegal) {
                                        moves.add(move);
                                        move = new Coordinates();
                                    }
                                }
                                while ((move.x2 + xOffs < 8) && (move.y2 + yOffs < 8) && (move.x2 + xOffs > -1) && (move.y2 + yOffs > -1));
                            }
                        }
                    }
                }
            }
        }
        return moves;
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
