package ru.ifmo.morozov.classes;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.Field;
import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

/**
 * Created by vks on 2/27/15.
 */
public class Rules implements Validator {

    private Field field;

    private boolean canBeat(int x, int y) {
        int u;
        int v;
        Player turn = field.getTurn();
        Checker matrix[][] = field.getMatrix();
        if (turn.getDirection() > 0) {
            u = x - 2;
            v = y - 2;
            if ((u >= 0) && (v >= 0)) {
                if (!field.isFree(u, v)) {
                    return false;
                } else if (field.isFree(x - 1, y - 1)) {
                    return false;
                } else if (matrix[x - 1][y - 1].getColour() != turn.getColour()) {
                    return true;
                }
            }

            u = x + 2;
            v = y - 2;
            if ((u < 8) && (v >= 0)) {
                if (!field.isFree(u, v)) {
                    return false;
                } else if (field.isFree(x + 1, y - 1)) {
                    return false;
                } else if (matrix[x + 1][y - 1].getColour() != turn.getColour()) {
                    return true;
                }
            }

        } else {
            u = x - 2;
            v = y + 2;
            if ((u >= 0) && (v < 8)) {
                if (!field.isFree(u, v)) {
                    return false;
                } else if (field.isFree(x - 1, y + 1)) {
                    return false;
                } else if (matrix[x - 1][y + 1].getColour() != turn.getColour()) {
                    return true;
                }
            }

            u = x + 2;
            v = y + 2;
            if ((u < 8) && (v < 8)) {
                if (!field.isFree(u, v)) {
                    return false;
                } else if (field.isFree(x + 1, y + 1)) {
                    return false;
                } else if (matrix[x + 1][y + 1].getColour() != turn.getColour()) {
                    return true;
                }
            }
        }
        return false;
    }

    public State isLegal(Field field, int x1, int y1, int x2, int y2) {
        this.field = field;
        if ((x2 < 0) || (x2 > 7) || (y2 < 0) || (y2 > 7)) {
            return State.Illegal;
        }
        if (field.getMatrix()[x1][y1] != null) {
            if (field.getMatrix()[x1][y1].getColour() != field.getTurn().getColour()) {
                return State.Illegal;
            }
            if ((x2 - x1 == 0) || (y2 - y1 == 0)) {
                return State.Illegal;
            }
            if (!field.isFree(x2, y2)) {
                return State.Illegal;
            }
            if ((field.getMatrix()[x1][y1].getType() == CheckerType.Simple) &&
                    ((Math.abs(y2 - y1) > 2) || Math.abs(x2 - x1) > 2)) {
                return State.Illegal;
            }
            if (Math.abs(x2 - x1) != Math.abs(y2 - y1)) {
                return State.Illegal;
            }


            if (field.getMatrix()[x1][y1].getType() == CheckerType.Simple) {
                if ((field.getTurn().getDirection() > 0) && (y2 - y1 > 0)) {
                    return State.Illegal;
                }
                if ((field.getTurn().getDirection() < 0) && (y2 - y1 < 0)) {
                    return State.Illegal;
                }
                if (Math.abs(y2 - y1) == 2) {
                    if (field.getMatrix()[(x1 + x2) / 2][(y1 + y2) / 2] == null) {
                        return State.Illegal;
                    } else if (field.getMatrix()[x1 + (x2 - x1) / 2][y1 + (y2 - y1) / 2].getColour() == field.getColour()) {
                        return State.Illegal;
                    } else if (canBeat(x2, y2)) {
                        return State.OneMoreMove;
                    } else {
                        return State.Legal;
                    }
                }
            }


            return State.Legal;
        } else {
            return State.Illegal;
        }
    }

    public boolean canMove(int x, int y) {
        if (field.getTurn().getDirection() > 0) {
            return (field.isFree(x + 1, y - 1) || field.isFree(x - 1, y - 1) || canBeat(x, y));
        } else {
            return (field.isFree(x + 1, y + 1) || field.isFree(x - 1, y + 1) || canBeat(x, y));
        }
    }
}
