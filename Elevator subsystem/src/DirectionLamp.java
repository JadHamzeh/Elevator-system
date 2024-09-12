/**
 * A class modelling a light for the direction on an elevator
 * @author Group A1-2
 * @version February 24th, 2024
 */
public class DirectionLamp {
    private boolean up;
    private boolean down;

    public DirectionLamp() {
        up = false;
        down = false;
    }

    public void indicateUp() {
        up = true;
        down = false;
    }

    public void indicateDown() {
        up = false;
        down = true;
    }

    public void turnOff() {
        up = false;
        down = false;
    }

    public boolean isUpIndicated() {
        return up;
    }

    public boolean isDownIndicated() {
        return down;
    }
}