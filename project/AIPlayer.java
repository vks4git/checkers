import java.util.Random;

/**
 * Created by vks on 2/26/15.
 */
public class AIPlayer implements Player {

    private boolean status;

    private int direction;

    private Colour colour;

    private String name;

    public AIPlayer(Colour colour, String name) {
        this.colour = colour;
        status = false;
        this.name = name;
    }

    public void move(Field field, Validator validator) {
        int x = 0;
        int y = 0;
        Random random = new Random();
        do {
            x = random.nextInt(7);
            y = random.nextInt(7);
        } while ((validator.isLegal(x, y, x + 1, y + 1) == State.Illegal) || (validator.isLegal(x, y, x - 1, y + 1) == State.Illegal));

        if (validator.isLegal(x, y, x + 1, y + 1) == State.Legal) {
            field.move(x, y, x + 1, y + 1);
        } else if (validator.isLegal(x, y, x - 1, y + 1) == State.Illegal) {
            field.move(x, y, x - 1, y + 1);
        }
    }

    public Colour getColour() {
        return colour;
    }

    public boolean canMove(Field field, Validator validator) {
        return true;
    }

    public boolean isVictorious() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
