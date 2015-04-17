package ru.ifmo.morozov.classes.controller;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.classes.model.Field;
import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

/**
 * Created by vks on 2/27/15.
 */

public class Rules implements Validator {

    private boolean canBeat(Checker checker, int direction, Field field, int x, int y) {
        if (checker.getType() == CheckerType.Simple) {
            if (direction > 0) {
                if (field.isFree(x - 2, y + 2)) {
                    if (!field.isFree(x - 1, y + 1)) {
                        if (field.getMatrix()[x - 1][y + 1].getColour() != checker.getColour()) {
                            return true;
                        }
                    }
                }
                if (field.isFree(x + 2, y + 2)) {
                    if (!field.isFree(x + 1, y + 1)) {
                        if (field.getMatrix()[x + 1][y + 1].getColour() != checker.getColour()) {
                            return true;
                        }
                    }
                }
            } else {
                if (field.isFree(x - 2, y - 2)) {
                    if (!field.isFree(x - 1, y - 1)) {
                        if (field.getMatrix()[x - 1][y - 1].getColour() != checker.getColour()) {
                            return true;
                        }
                    }
                }
                if (field.isFree(x + 2, y - 2)) {
                    if (!field.isFree(x + 1, y - 1)) {
                        if (field.getMatrix()[x + 1][y - 1].getColour() != checker.getColour()) {
                            return true;
                        }
                    }
                }
            }
        } else {
            int u = x;
            int v = y;

            while ((u + 1 < 8) && (v + 1 < 8)) {
                u++;
                v++;
                if (!field.isFree(u, v)) {
                    if (field.getMatrix()[u][v].getColour() != checker.getColour()) {
                        if (field.isFree(u + 1, v + 1)) {
                            return true;
                        }
                    }
                }
            }

            u = x;
            v = y;

            while ((u - 1 >= 0) && (v + 1 < 8)) {
                u--;
                v++;
                if (!field.isFree(u, v)) {
                    if (field.getMatrix()[u][v].getColour() != checker.getColour()) {
                        if (field.isFree(u - 1, v + 1)) {
                            return true;
                        }
                    }
                }
            }

            u = x;
            v = y;

            while ((u - 1 >= 0) && (v - 1 < 8)) {
                u--;
                v--;
                if (!field.isFree(u, v)) {
                    if (field.getMatrix()[u][v].getColour() != checker.getColour()) {
                        if (field.isFree(u - 1, v - 1)) {
                            return true;
                        }
                    }
                }
            }

            u = x;
            v = y;

            while ((u + 1 >= 0) && (v - 1 < 8)) {
                u++;
                v--;
                if (!field.isFree(u, v)) {
                    if (field.getMatrix()[u][v].getColour() != checker.getColour()) {
                        if (field.isFree(u + 1, v - 1)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public State isLegal(Field field, Coordinates move, Player player) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        Colour colour = player.getColour();
        int direction = player.getDirection();
        Checker checker = field.getMatrix()[x1][y1];

        if (checker == null) {
            return State.Illegal;
        }
        if (!field.isFree(x2, y2)) {
            return State.Illegal;
        }
        if (Math.abs(x2 - x1) != Math.abs(y2 - y1)) {
            return State.Illegal;
        }

        if (checker.getType() == CheckerType.Simple) {
            if (Math.abs(x2 - x1) > 2) {
                return State.Illegal;
            }
            if (Math.abs(x2 - x1) == 0) {
                return State.Illegal;
            }
            if (Math.abs(y2 - y1) > 2) {
                return State.Illegal;
            }
            if (Math.abs(y2 - y1) == 0) {
                return State.Illegal;
            }
            if ((direction > 0) && (y2 - y1 < 0)) {
                return State.Illegal;
            }
            if ((direction < 0) && (y2 - y1 > 0)) {
                return State.Illegal;
            }
            if (Math.abs(y2 - y1) == 2) {
                int xMedium = (x1 + x2) / 2;
                int yMedium = (y1 + y2) / 2;
                if (!field.isFree(xMedium, yMedium)) {
                    if (field.getMatrix()[xMedium][yMedium].getColour() == colour){
                        return State.Illegal;
                    }
                } else {
                    return State.Illegal;
                }
            }
        } else {
            if (hasSameColour(move, colour, field)) {
                return State.Illegal;
            }
            if (hasStride(move, field)) {
                return State.Illegal;
            }
        }
        if (canBeat(checker, direction, field, x2, y2)) {
            return State.OneMoreMove;
        }
        boolean beat = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!field.isFree(i, j)) {
                    if (field.getMatrix()[i][j].getColour() == colour) {
                        if (canBeat(field.getMatrix()[i][j], player.getDirection(), field, i, j)) {
                            beat |= ((x1 == i) && (y1 == j) && Math.abs(x1 - x2) > 1);
                        }
                    }
                }
            }
        }

        if (canBeat(checker, player.getDirection(), field, x1, y1) && !beat) {
            return State.Illegal;
        }

        return State.Legal;
    }

    private boolean hasSameColour(Coordinates move, Colour colour, Field field) {
        int dirX = (move.x2 - move.x1) / Math.abs(move.x2 - move.x1);
        int dirY = (move.y2 - move.y1) / Math.abs(move.y2 - move.y1);
        int x = move.x1 + dirX;
        int y = move.y1 + dirY;
        for (int j = 0; j <= Math.abs(move.x2 - move.x1); j++) {
            if (!field.isFree(x, y)) {
                if (field.getMatrix()[x][y].getColour() == colour) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasStride(Coordinates move, Field field) {
        int dirX = (move.x2 - move.x1) / Math.abs(move.x2 - move.x1);
        int dirY = (move.y2 - move.y1) / Math.abs(move.y2 - move.y1);
        int x = move.x1 + dirX;
        int y = move.y1 + dirY;
        for (int j = 0; j <= Math.abs(move.x2 - move.x1); j++) {
            if (field.getMatrix()[x][y] != null) {
                if (field.getMatrix()[x + dirX][y + dirY] != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMove(Field field, Player player) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (!field.isFree(i, j)) {
                    if (field.getMatrix()[i][j].getColour() == player.getColour()) {
                        if (canBeat(field.getMatrix()[i][j], player.getDirection(), field, i, j)) {
                            return true;
                        }
                    }
                }
                if (player.getDirection() > 0) {
                    if (field.isFree(i + 1, j + 1)) {
                        return true;
                    }
                }
                if (player.getDirection() > 0) {
                    if (field.isFree(i - 1, j + 1)) {
                        return true;
                    }
                }
                if (player.getDirection() < 0) {
                    if (field.isFree(i + 1, j - 1)) {
                        return true;
                    }
                }
                if (player.getDirection() < 0) {
                    if (field.isFree(i - 1, j - 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
