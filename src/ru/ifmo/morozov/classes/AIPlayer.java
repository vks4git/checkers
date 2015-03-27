package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by vks on 2/26/15.
 */
public class AIPlayer implements Player {

    private class Move {
        public int x1;
        public int y1;
        public int x2;
        public int y2;
        public int priority = 0;

        public Move(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

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

    public void move(Field field) {
        List<Move> available = getAvailableMoves(field);
        price(available, field);
        int max = 0;
        int index = 0;
        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).priority > max) {
                max = available.get(i).priority;
                index = i;
            }
        }
        Move move = available.get(index);
        do {
            field.move(move.x1, move.y1, move.x2, move.y2);
        } while (!field.isFinished());
    }

    private List<Move> getAvailableMoves(Field field) {
        List<Move> list = new ArrayList<Move>();
        Checker[][] matrix = field.getMatrix();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matrix[i][j] != null) {
                    if (matrix[i][j].getColour() == field.getColour()) {
                        if (validator.isLegal(field, i, j, i + 1, j + 1) != State.Illegal) {
                            list.add(new Move(i, j, i + 1, j + 1));
                        }
                        if (validator.isLegal(field, i, j, i + 1, j - 1) != State.Illegal) {
                            list.add(new Move(i, j, i + 1, j - 1));
                        }
                        if (validator.isLegal(field, i, j, i - 1, j - 1) != State.Illegal) {
                            list.add(new Move(i, j, i - 1, j - 1));
                        }
                        if (validator.isLegal(field, i, j, i - 1, j + 1) != State.Illegal) {
                            list.add(new Move(i, j, i + 1, j + 1));
                        }
                        if (validator.isLegal(field, i, j, i + 2, j + 2) != State.Illegal) {
                            list.add(new Move(i, j, i + 2, j + 2));
                        }
                        if (validator.isLegal(field, i, j, i + 2, j - 2) != State.Illegal) {
                            list.add(new Move(i, j, i + 2, j - 2));
                        }
                        if (validator.isLegal(field, i, j, i - 2, j - 2) != State.Illegal) {
                            list.add(new Move(i, j, i - 2, j - 2));
                        }
                        if (validator.isLegal(field, i, j, i - 2, j + 2) != State.Illegal) {
                            list.add(new Move(i, j, i + 2, j + 2));
                        }
                    }
                }
            }
        }
        return list;
    }

    private void price(List<Move> list, Field field) {

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
