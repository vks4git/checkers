package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;
import ru.ifmo.morozov.enums.State;
import ru.ifmo.morozov.interfaces.Player;
import ru.ifmo.morozov.interfaces.Validator;

/**
 * Created by vks on 2/27/15.
 */

public class Rules implements Validator {

    private boolean canBeat(Checker checker, Checker[][] matrix, int x, int y) {
        int direction = checker.getDirection();
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

    public State verify(Checker[][] matrix, Coordinates move, Colour colour) {
        Checker checker = matrix[move.x1][move.y1];
        if (checker == null) {
            return State.Illegal;
        }
        if (checker.getColour() != colour) {
            return State.Illegal;
        }
        int direction = checker.getDirection();
        if (Math.abs(move.x1 - move.x2) != Math.abs(move.y1 - move.y2)) {
            return State.Illegal;
        }
        if (matrix[move.x2][move.y2] != null) {
            return State.Illegal;
        }
        if (canAnyCheckerBeat(matrix, colour)) {
            if (!isBeating(matrix, move, colour)) {
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
                return checkWhiteSimpleCheckerMove(matrix, move, direction);
            } else {
                return checkBlackSimpleCheckerMove(matrix, move, direction);
            }
        } else {
            if (colour == Colour.White) {
                return checkWhiteQueenCheckerMove(matrix, move);
            } else {
                return checkBlackQueenCheckerMove(matrix, move);
            }
        }
    }

    public boolean canMove(Checker [][] matrix, Colour colour) {
        int direction;
        Checker checker;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (matrix[i][j] != null) {
                    checker = matrix[i][j];
                    direction = checker.getDirection();

                    if (checker.getColour() == colour) {
                        if (canBeat(checker, matrix, i, j)) {
                            return true;
                        }

                        if (checker.getType() == CheckerType.Simple) {
                            if ((i + 1 < 8) && (j + direction < 8) && (j + direction > -1)) {
                                if (matrix[i + 1][j + direction] == null) {
                                    return true;
                                }
                            }
                            if ((i - 1 > -1) && (j + direction < 8) && (j + direction > -1)) {
                                if (matrix[i - 1][j + direction] == null) {
                                    return true;
                                }
                            }
                        } else {

                            for (int u = -1; u < 2; u += 2) {
                                for (int v = -1; v < 2; v += 2) {
                                    if ((i + u > -1) && (i + u < 8) && (j + v < 8) && (j + v > -1)) {
                                        if (matrix[i + u][j + v] == null) {
                                            return true;
                                        }
                                    }
                                }
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

        if (direction * (y2 - y1) < 0) {
            return State.Illegal;
        }

        if (isBeating(matrix, move, Colour.White)) {
            if (canBeat(new Checker(Colour.White, CheckerType.Simple, direction), matrix, x2, y2)) {
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

        if (direction * (y2 - y1) < 0) {
            return State.Illegal;
        }

        if (isBeating(matrix, move, Colour.Black)) {
            if (canBeat(new Checker(Colour.Black, CheckerType.Simple, direction), matrix, x2, y2)) {
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
        for (int i = 1; i <= Math.abs(move.x1 - move.x2); i++) {
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
            if (canBeat(new Checker(Colour.White, CheckerType.Queen, -1), matrix, x2, y2)) {
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
        for (int i = 1; i <= Math.abs(move.x1 - move.x2); i++) {
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
            if (canBeat(new Checker(Colour.Black, CheckerType.Queen, -1), matrix, x2, y2)) {
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
                while ((u + i < 8) && (u + i > -1) && (v + j < 8) && (v + j > -1) && (matrix[u][v] == null)) {
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

    public boolean canAnyCheckerBeat(Checker[][] matrix, Colour colour) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matrix[i][j] != null) {
                    Checker checker = matrix[i][j];
                    if (checker.getColour() == colour) {
                        if (canBeat(checker, matrix, i, j)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isBeating(Checker[][] matrix, Coordinates move, Colour colour) {
        int x1 = move.x1;
        int y1 = move.y1;
        int x2 = move.x2;
        int y2 = move.y2;
        int length = Math.abs(x1 - x2);
        int dx = (x2 - x1) / length;
        int dy = (y2 - y1) / length;
        for (int k = 1; k < length; k++) {
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
