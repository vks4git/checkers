package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.MoveSequence;
import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by vks on 2/26/15.
 */
public class AIPlayer implements Player {

    private final int recursionDepth = 6;
    private final Colour[] colours = new Colour[2];
    private Colour colour;
    private String name;
    private Validator validator;

    public AIPlayer(Colour colour, String name, Validator validator) {
        this.colour = colour;
        this.validator = validator;
        this.name = name;
        colours[0] = this.colour;
        colours[1] = (this.colour == Colour.Black) ? Colour.White : Colour.Black;
    }


    /*
    Оценочная функция. Изначальная оценка любой позиции равна нулю. Стоимость событий:
    * Взятие шашки соперника -- 1.
    * Взятие дамки соперника -- 5.
    * Превращение шашки в дамку -- 4.
    * Взятие соперником шашки -- -1.
    * Потеря дамки -- -5.
    * Превращение шашки соперника в дамку -- -6.
     */

    private int evaluate(Checker[][] field, MoveSequence sequence, Colour colour) {
        int position = 0;
        Checker[][] matrix = cloneMatrix(field);
        for (Coordinates move : sequence) {
            Checker checker = matrix[move.x1][move.y1];
            if (validator.isBeating(matrix, move, checker.getColour())) {
                int dirX = (move.x2 - move.x1) / Math.abs(move.x2 - move.x1);
                int dirY = (move.y2 - move.y1) / Math.abs(move.y2 - move.y1);
                int p = move.x1;
                int q = move.y1;
                for (int j = 0; j < Math.abs(move.x2 - move.x1); j++) {
                    if (matrix[p][q] != null) {
                        checker = matrix[p][q];
                    }
                    p += dirX;
                    q += dirY;
                }
                if (checker.getColour() != colour) {
                    if (checker.getType() == CheckerType.Simple) {
                        position++;
                    } else {
                        position += 5;
                    }
                } else {
                    if (checker.getType() == CheckerType.Simple) {
                        position--;
                    } else {
                        position -= 5;
                    }
                }
            }
            checker = matrix[move.x1][move.y1];
            if (checker.getColour() == colour) {
                if (checker.getDirection() == 1) {
                    if (move.y2 == 7) {
                        if (checker.getType() == CheckerType.Simple) {
                            position += 4;
                        }
                    }
                } else {
                    if (move.y2 == 0) {
                        if (checker.getType() == CheckerType.Simple) {
                            position += 4;
                        }
                    }
                }
            } else {
                if (checker.getDirection() == 1) {
                    if (move.y2 == 7) {
                        if (checker.getType() == CheckerType.Simple) {
                            position -= 6;
                        }
                    }
                } else {
                    if (move.y2 == 0) {
                        if (checker.getType() == CheckerType.Simple) {
                            position -= 6;
                        }
                    }
                }
            }
            move(matrix, move);
        }
        return position;
    }

    private int[] minimax(List<MoveSequence> moves, Checker[][] matrix, int depth) {
        int[] result = new int[2];

        result[0] = -10000;
        result[1] = 0;
        int i = 0;
        for (MoveSequence sequence : moves) {
            int position = evaluate(matrix, sequence, colours[depth % 2]);
            if (depth < recursionDepth) {
                Checker[][] field = cloneMatrix(matrix);
                for (Coordinates move : sequence) {
                    move(field, move);
                }
                if (depth % 2 == 0) {
                    position -= minimax(getMoves(field, colours[(depth + 1) % 2]), field, depth + 1)[0];
                } else {
                    position += minimax(getMoves(field, colours[(depth + 1) % 2]), field, depth + 1)[0];
                }
            }
            if (position > result[0]) {
                result[0] = position;
                result[1] = i;
            }
            i++;
        }
        return result;
    }

    public MoveSequence move(Checker[][] matrix, Pointer pointer) {
        List<MoveSequence> moveSequences = getMoves(matrix, colour);
        int index = minimax(moveSequences, matrix, 0)[1];
        return moveSequences.get(index);
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
        Checker checker;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                checker = matrix[i][j];
                if (checker != null) {
                    result[i][j] = new Checker(checker.getColour(), checker.getType(), checker.getDirection());
                }
            }
        }
        return result;
    }

    private Checker[][] move(Checker[][] matrix, Coordinates move) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        matrix[x2][y2] = matrix[x1][y1];

        int dirX = (x2 - x1) / Math.abs(x2 - x1);
        int dirY = (y2 - y1) / Math.abs(y2 - y1);
        int x = x1;
        int y = y1;
        for (int j = 0; j < Math.abs(x2 - x1); j++) {
            matrix[x][y] = null;
            x += dirX;
            y += dirY;
        }

        if (matrix[x2][y2].getDirection() > 0) {
            if (y2 == 7) {
                matrix[x2][y2].setType(CheckerType.Queen);
            }
        } else {
            if (y2 == 0) {
                matrix[x2][y2].setType(CheckerType.Queen);
            }
        }
        return matrix;
    }

    public String getName() {
        return name;
    }

    public boolean keyboardEntry() {
        return false;
    }

    public Colour getColour() {
        return colour;
    }

}
