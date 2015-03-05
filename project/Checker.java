/**
 * Created by vks on 2/22/15.
 */
public class Checker {

    private Colour colour;
    private CheckerType type;

    private float x;
    private float y;
    private float z;             //Координаты шашки в пространстве

    private boolean active;

    public Checker(Colour colour, CheckerType type) {
        this.colour = colour;
        this.type = type;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        active = true;
    }

    public Colour getColour() {
        return colour;
    }

    public CheckerType getType() {
        return type;
    }

    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setInactive() {
        active = false;
    }

    public boolean status() {
        return active;
    }
}
