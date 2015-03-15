import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/**
 * Created by vks on 2/22/15.
 */
public class Field {

    private Checker matrix[][];
    private Pointer pointer;
    private boolean checked;
    private Colour colour;
    private Pointer checkCoords;
    private Pointer setCoords;
    private boolean keyboardEntry;
    private Player turn;

    private Player player1;
    private Player player2;

    public Field(Player player1, Player player2) {
        matrix = new Checker[8][8];
        pointer = new Pointer();
        this.player1 = player1;
        this.player2 = player2;
        checked = false;
        checkCoords = new Pointer();
        setCoords = new Pointer();
        keyboardEntry = false;

        pointer.x = 4;
        pointer.y = 4;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i + j) % 2 == 1) {
                    matrix[i][j] = new Checker(player2.getColour(), CheckerType.Simple);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 7; j > 4; j--) {
                if ((i + j) % 2 == 1) {
                    matrix[i][j] = new Checker(player1.getColour(), CheckerType.Simple);
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

    private void free(int X, int Y) {
        matrix[X][Y] = null;
    }

    public void remove(int X, int Y) {
        free(X, Y);
    }

    public void move(int X1, int Y1, int X2, int Y2) {
        matrix[X2][Y2] = matrix[X1][Y1];
        free(X1, Y1);
    }

    public final Pointer getPointer() {
        return pointer;
    }

    public void resetPointer(int x, int y) {
        if ((pointer.x + x < 8) && (pointer.x + x >= 0)) {
            pointer.x += x;
        }
        if ((pointer.y + y < 8) && (pointer.y + y >= 0)) {
            pointer.y += y;
        }
    }

    public boolean isChecked() {
        return checked;
    }

    public void check() {
        if (matrix[pointer.x][pointer.y] != null) {
            colour = matrix[pointer.x][pointer.y].getColour();
            if (turn.getColour() == colour) {
                checkCoords.x = pointer.x;
                checkCoords.y = pointer.y;
                checked = true;
            }
        }
    }

    public void uncheck() {
        checked = false;
        setCoords.x = pointer.x;
        setCoords.y = pointer.y;
    }

    public final Colour getColour() {
        return colour;
    }

    public void setKeybdEntry() {
        keyboardEntry = true;
    }

    public void unsetKeybdEntry() {
        keyboardEntry = false;
    }

    public boolean getKeybdEntry() {
        return keyboardEntry;
    }

    public void setTurn(Player player) {
        turn = player;
    }

    public final Player getTurn() {
        return turn;
    }

    public void reset() {
        checkCoords.x = 0;
        checkCoords.y = 0;
        setCoords.x = 0;
        setCoords.y = 0;
    }

    public final Pointer getSetCoords() {
        return setCoords;
    }

    public final Pointer getCheckCoords() {
        return checkCoords;
    }

}
