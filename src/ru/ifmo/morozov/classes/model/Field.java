package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Checker;
import ru.ifmo.morozov.classes.Coordinates;
import ru.ifmo.morozov.enums.CheckerType;
import ru.ifmo.morozov.enums.Colour;

/**
 * Created by vks on 2/22/15.
 */
public class Field {

    private Checker matrix[][];

    public Field(Colour colour1, Colour colour2) {
        matrix = new Checker[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i + j) % 2 == 0) {
                    matrix[i][j] = new Checker(colour1, CheckerType.Simple, 1);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 7; j > 4; j--) {
                if ((i + j) % 2 == 0) {
                    matrix[i][j] = new Checker(colour2, CheckerType.Simple, -1);
                }
            }
        }

    }

    public final Checker[][] getMatrix() {
        return matrix;
    }

    public void free(int x, int y) {
        matrix[x][y] = null;
    }

    public void move(Coordinates move) {
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
            free(x, y);
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
    }

}
