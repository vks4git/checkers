package ru.ifmo.morozov.classes.model;

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

    private boolean canBeat(Checker checker, int direction, Checker[][] matrix, int x, int y) {
        if (checker.getType() == CheckerType.Simple) {
            if (checker.getColour() == Colour.White) {
                return checkWhiteSimpleCheckerBeat(matrix, x, y, direction);
            } else {
                return checkBlackSimpleCheckerBeat(matrix, x, y, direction);
            }
        } else {
            if (checker.getColour() == Colour.White) {
                return checkWhiteQueenCheckerBeat(matrix, x, y);
            } else {
                return checkBlackQueenCheckerBeat(matrix, x, y);
            }
        }
    }

    public State isLegal(Field field, Coordinates move, Player player) {
        Colour colour = player.getColour();
        int direction = player.getDirection();
        Checker checker = field.getMatrix()[move.x1][move.y1];
        if (Math.abs(move.x1 - move.x2) != Math.abs(move.y1 - move.y2)) {
            return State.Illegal;
        }
        if (!field.isFree(move.x2, move.y2)) {
            return State.Illegal;
        }
        if (canAnyCheckerBeat(field.getMatrix(), player)) {
            if (!isBeating(field.getMatrix(), move, player.getColour())) {
                return State.Illegal;
            }
        }
        if (checker.getType() == CheckerType.Simple) {
            if (Math.abs(move.x1 - move.x2) > 2) {
                return State.Illegal;
            }
            if (Math.abs(move.y1 - move.y2) > 2) {
                return State.Illegal;
            }
            if (colour == Colour.White) {
                return checkWhiteSimpleCheckerMove(field.getMatrix(), move, direction);
            } else {
                return checkBlackSimpleCheckerMove(field.getMatrix(), move, direction);
            }
        } else {
            if (colour == Colour.White) {
                return checkWhiteQueenCheckerMove(field.getMatrix(), move);
            } else {
                return checkBlackQueenCheckerMove(field.getMatrix(), move);
            }
        }
    }

    public boolean canMove(Field field, Player player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {


                if (!field.isFree(i, j)) {
                    if (field.getMatrix()[i][j].getColour() == player.getColour()) {
                        if (canBeat(field.getMatrix()[i][j], player.getDirection(), field.getMatrix(), i, j)) {
                            return true;
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

                
            }
        }
        return false;
    }

    private State checkWhiteSimpleCheckerMove(Checker[][] matrix, Coordinates move, int direction) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;

        if (Math.abs(x1 - x2) == 2) {
            if (matrix[(x1 + x2) / 2][(y1 + y2) / 2] == null) {
                return State.Illegal;
            }
            if (matrix[(x1 + x2) / 2][(y1 + y2) / 2].getColour() == Colour.White) {
                return State.Illegal;
            }
        }

        if (direction > 0) {
            if (y2 - y1 < 0) {
                return State.Illegal;
            }
        } else {
            if (y2 - y1 > 0) {
                return State.Illegal;
            }
        }

        if (isBeating(matrix, move, Colour.White)) {
            if (canBeat(new Checker(Colour.White, CheckerType.Simple), direction, matrix, x2, y2)) {
                return State.OneMoreMove;
            }
        }
        return State.Legal;
    }

    private State checkBlackSimpleCheckerMove(Checker[][] matrix, Coordinates move, int direction) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        if (Math.abs(x1 - x2) == 2) {
            if (matrix[(x1 + x2) / 2][(y1 + y2) / 2] == null) {
                return State.Illegal;
            }
            if (matrix[(x1 + x2) / 2][(y1 + y2) / 2].getColour() == Colour.Black) {
                return State.Illegal;
            }
        }

        if (direction > 0) {
            if (y2 - y1 < 0) {
                return State.Illegal;
            }
        } else {
            if (y2 - y1 > 0) {
                return State.Illegal;
            }
        }

        if (isBeating(matrix, move, Colour.Black)) {
            if (canBeat(new Checker(Colour.Black, CheckerType.Simple), direction, matrix, x2, y2)) {
                return State.OneMoreMove;
            }
        }
        return State.Legal;
    }

    private State checkWhiteQueenCheckerMove(final Checker[][] matrix, Coordinates move) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        int dx = (x2 - x1) / Math.abs(x1 - x2);
        int dy = (y2 - y1) / Math.abs(y1 - y2);
        for (int i = 1; i <= Math.abs(x1 - x2); i++) {
            if (matrix[x1 + dx][y1 + dy] != null) {
                if (matrix[x1 + dx][y1 + dy].getColour() == Colour.White) {
                    return State.Illegal;
                }
                if ((x1 + dx * 2 > 7) || (y1 + dy * 2 > 7)) {
                    return State.Illegal;
                }
                if ((x1 + dx * 2 < 0) || (y1 + dy * 2 < 0)) {
                    return State.Illegal;
                }
                if (matrix[x1 + dx * 2][y1 + dy * 2] != null) {
                    return State.Illegal;
                }
            }
            x1 += dx;
            y1 += dy;
        }
        if (isBeating(matrix, move, Colour.White)) {
            int x = move.x1;
            int y = move.y1;
            for (int j = 1; j < Math.abs(move.x2 - move.x1); j++) {
                x += dx;
                y += dy;
                matrix[x][y] = null;
            }
            if (canBeat(new Checker(Colour.White, CheckerType.Queen), -1, matrix, x2, y2)) {
                return State.OneMoreMove;
            }
        }
        return State.Legal;
    }

    private State checkBlackQueenCheckerMove(final Checker[][] matrix, Coordinates move) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        int dx = (x2 - x1) / Math.abs(x1 - x2);
        int dy = (y2 - y1) / Math.abs(y1 - y2);
        for (int i = 1; i <= Math.abs(x1 - x2); i++) {
            if (matrix[x1 + dx][y1 + dy] != null) {
                if (matrix[x1 + dx][y1 + dy].getColour() == Colour.Black) {
                    return State.Illegal;
                }
                if ((x1 + dx * 2 > 7) || (y1 + dy * 2 > 7)) {
                    return State.Illegal;
                }
                if ((x1 + dx * 2 < 0) || (y1 + dy * 2 < 0)) {
                    return State.Illegal;
                }
                if (matrix[x1 + dx * 2][y1 + dy * 2] != null) {
                    return State.Illegal;
                }
            }
            x1 += dx;
            y1 += dy;
        }
        if (isBeating(matrix, move, Colour.Black)) {
            int x = move.x1;
            int y = move.y1;
            for (int j = 1; j < Math.abs(move.x2 - move.x1); j++) {
                x += dx;
                y += dy;
                matrix[x][y] = null;
            }
            if (canBeat(new Checker(Colour.Black, CheckerType.Queen), -1, matrix, x2, y2)) {
                return State.OneMoreMove;
            }
        }
        return State.Legal;
    }

    private boolean checkWhiteSimpleCheckerBeat(Checker[][] matrix, int x, int y, int direction) {
        if ((x + 2 < 8) && (y + direction * 2 < 8) && (y + direction * 2 > -1)) {
            if (matrix[x + 1][y + direction] != null) {
                if (matrix[x + 1][y + direction].getColour() == Colour.Black) {
                    if (matrix[x + 2][y + direction * 2] == null) {
                        return true;
                    }
                }
            }
        }
        if ((x - 2 > -1) && (y + direction * 2 < 8) && (y + direction * 2 > -1)) {
            if (matrix[x - 1][y + direction] != null) {
                if (matrix[x - 1][y + direction].getColour() == Colour.Black) {
                    if (matrix[x - 2][y + direction * 2] == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkBlackSimpleCheckerBeat(Checker[][] matrix, int x, int y, int direction) {
        if ((x + 2 < 8) && (y + direction * 2 < 8) && (y + direction * 2 > -1)) {
            if (matrix[x + 1][y + direction] != null) {
                if (matrix[x + 1][y + direction].getColour() == Colour.White) {
                    if (matrix[x + 2][y + direction * 2] == null) {
                        return true;
                    }
                }
            }
        }
        if ((x - 2 > -1) && (y + direction * 2 < 8) && (y + direction * 2 > -1)) {
            if (matrix[x - 1][y + direction] != null) {
                if (matrix[x - 1][y + direction].getColour() == Colour.White) {
                    if (matrix[x - 2][y + direction * 2] == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkWhiteQueenCheckerBeat(Checker[][] matrix, int x, int y) {
        int u;
        int v;
        for (int i = -1; i < 2; i += 2) {
            for (int j = -1; j < 2; j += 2) {
                u = x + i;
                v = y + j;
                while ((u < 8) && (u > -1) && (v < 8) && (v > -1) && (matrix[u][v] == null)) {
                    u += i;
                    v += j;
                }
                if ((u + i < 8) && (u + i > -1) && (v + j < 8) && (v + j > -1)) {
                    if (matrix[u][v].getColour() == Colour.Black) {
                        if (matrix[u + i][v + j] == null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkBlackQueenCheckerBeat(Checker[][] matrix, int x, int y) {
        int u;
        int v;
        for (int i = -1; i < 2; i += 2) {
            for (int j = -1; j < 2; j += 2) {
                u = x + i;
                v = y + j;
                while ((u < 8) && (u > -1) && (v < 8) && (v > -1) && (matrix[u][v] == null)) {
                    u += i;
                    v += j;
                }
                if ((u + i < 8) && (u + i > -1) && (v + j < 8) && (v + j > -1)) {
                    if (matrix[u][v].getColour() == Colour.White) {
                        if (matrix[u + i][v + j] == null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canAnyCheckerBeat(Checker[][] matrix, Player player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matrix[i][j] != null) {
                    if (matrix[i][j].getColour() == player.getColour()) {
                        if (canBeat(matrix[i][j], player.getDirection(), matrix, i, j)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isBeating(Checker[][] matrix, Coordinates move, Colour colour) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        int dx = (x2 - x1) / Math.abs(x1 - x2);
        int dy = (y2 - y1) / Math.abs(y1 - y2);
        for (int k = 1; k < Math.abs(move.x1 - move.x2); k++) {
            x1 += dx;
            y1 += dy;
            if ((x1 + dx < 8) && (x1 + dx > -1) && (y1 + dy < 8) && (y1 + dy > -1)) {
                if (matrix[x1][y1] != null) {
                    if (matrix[x1][y1].getColour() != colour) {
                        if (matrix[x1 + dx][y1 + dy] == null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
