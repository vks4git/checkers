package ru.ifmo.morozov.classes.model;

import ru.ifmo.morozov.classes.Checker;
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
                    matrix[i][j] = new Checker(colour1, CheckerType.Simple);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 7; j > 4; j--) {
                if ((i + j) % 2 == 0) {
                    matrix[i][j] = new Checker(colour2, CheckerType.Simple);
                }
            }
        }
    }


    public boolean isFree(int x, int y) {
        if ((x > 7) || (x < 0) || (y > 7) || (y < 0)) {
            return false;
        }
        return (matrix[x][y] == null);
    }

    public final Checker[][] getMatrix() {
        return matrix;
    }

    public void free(int X, int Y) {
        matrix[X][Y] = null;
    }

    public void move(int X1, int Y1, int X2, int Y2) {
        matrix[X2][Y2] = matrix[X1][Y1];
        free(X1, Y1);
    }

}
