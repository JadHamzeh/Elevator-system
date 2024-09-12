/**
 * A class modelling the buttons in the elevator
 * @author Group A1-2
 * @version February 24th, 2024
 */
public class ElevatorButton {
    private int floorNumber;
    private boolean pressed;

    public ElevatorButton(int floor) {
        floorNumber = floor;
        pressed = false;
    }

    public void pressButton() {
        pressed = true;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public boolean getPressed() {
        return pressed;
    }
}