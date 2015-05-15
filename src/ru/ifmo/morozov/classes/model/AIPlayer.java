package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.MoveSequence;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vks on 2/26/15.
 */
public class AIPlayer implements Player {

    private Colour colour;
    private String name;
    private Validator validator;

    public AIPlayer(Colour colour, String name, Validator validator) {
        this.colour = colour;
        this.validator = validator;
        this.name = name;
    }

    public MoveSequence move(Checker[][] matrix, Pointer pointer) {
        return getMoves(matrix, colour).get(0);
    }

    public Colour getColour() {
        return colour;
    }

    private List<MoveSequence> getMoves(Checker[][] matrix, Colour colour) {
        List<MoveSequence> moves = new ArrayList<>();
        List<MoveSequence> movesFromPoint;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                movesFromPoint = getMovesFromPoint(matrix, colour, i, j);
                moves.addAll(movesFromPoint.stream().collect(Collectors.toList()));
            }
        }
        return moves;
    }

    private List<MoveSequence> getMovesFromPoint(Checker[][] matrix, Colour colour, int x, int y) {
        int xOffs;
        int yOffs;

        Coordinates move = new Coordinates();
        MoveSequence sequence = new MoveSequence();
        List<MoveSequence> moves = new ArrayList<>();
        State state;

        for (int u = -1; u < 2; u += 2) {
            for (int v = -1; v < 2; v += 2) {
                xOffs = x + u;
                yOffs = y + v;
                while ((xOffs < 8) && (xOffs > -1) && (yOffs < 8) && (yOffs > -1)) {
                    move.x1 = x;
                    move.y1 = y;
                    move.y2 = yOffs;
                    move.x2 = xOffs;
                    state = validator.verify(matrix, move, colour);
                    if (state == State.Legal) {
                        sequence.add(move);
                        moves.add(sequence);
                        move = new Coordinates();
                        sequence = new MoveSequence();
                    }
                    if (state == State.OneMoreMove) {
                        sequence.add(move);
                        moves.add(sequence);
                        multiply(moves, getBeatsFromPoint(matrix, colour, move.x2, move.y2));
                        move = new Coordinates();
                        sequence = new MoveSequence();
                    }
                    xOffs += u;
                    yOffs += v;
                }
            }
        }
        return moves;
    }

    private List<MoveSequence> getBeatsFromPoint(Checker[][] matrix, Colour colour, int x, int y) {
        int xOffs;
        int yOffs;
        Checker[][] field = cloneMatrix(matrix);
        Coordinates move = new Coordinates();
        MoveSequence sequence = new MoveSequence();
        List<MoveSequence> moves = new ArrayList<>();
        for (int u = -1; u < 2; u += 2) {
            for (int v = -1; v < 2; v += 2) {
                xOffs = x + u;
                yOffs = y + v;
                while ((xOffs < 8) && (xOffs > -1) && (yOffs < 8) && (yOffs > -1)) {
                    move.x1 = x;
                    move.y1 = y;
                    move.y2 = yOffs;
                    move.x2 = xOffs;
                    if (validator.isBeating(field, move, colour)) {
                        sequence.add(move);
                        moves.add(sequence);
                        if (validator.verify(field, move, colour) == State.OneMoreMove) {
                            field[move.x2][move.y2] = field[move.x1][move.y1];
                            int dirX = (move.x2 - move.x1) / Math.abs(move.x2 - move.x1);
                            int dirY = (move.y2 - move.y1) / Math.abs(move.y2 - move.y1);
                            int p = move.x1;
                            int q = move.y1;
                            for (int j = 0; j < Math.abs(move.x2 - move.x1); j++) {
                                field[p][q] = null;
                                p += dirX;
                                q += dirY;
                            }
                            multiply(moves, getBeatsFromPoint(field, colour, move.x2, move.y2));
                        }
                        move = new Coordinates();
                        sequence = new MoveSequence();
                    }
                    xOffs += u;
                    yOffs += v;
                }
            }
        }
        return moves;
    }

    private void multiply(List<MoveSequence> dest, List<MoveSequence> src) {
        int size = dest.size() - 1;
        MoveSequence last = dest.get(size);
        dest.remove(size);
        for (MoveSequence source : src) {
            MoveSequence sequence = new MoveSequence();
            sequence.concat(last);
            sequence.concat(source);
            dest.add(sequence);
        }
    }

    private Checker[][] cloneMatrix(Checker[][] matrix) {
        Checker[][] result = new Checker[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(matrix[i], 0, result[i], 0, 8);
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public boolean keyboardEntry() {
        return false;
    }
}
