
/**
 * A class modelling a motor in an elevator
 * @author Group A1-2
 * @version February 24th, 2024
 */
public class Motor {
    private int currentFloor;
    private boolean working;

    public Motor() {
        currentFloor = 1;
        working = true;
    }
    
    public void moveMotor(int newFloor) {
        currentFloor = newFloor;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean w) {
        this.working = w;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
}