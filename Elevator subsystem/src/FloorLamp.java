/**
 * A class modelling the lights on the floor
 * @author Group A1-2
 * @version February 24th, 2024
 */
public class FloorLamp {
    private int floorNumber;
    private boolean on;

    public FloorLamp(int floorNumber) {
        this.floorNumber = floorNumber;
        this.on = false;
    }

    public void turnOn() {
        this.on = true;
        System.out.println("Floor lamp on at floor: " + floorNumber);
    }

    public void turnOff() {
        this.on = false;
        System.out.println("Floor lamp off at floor: " + floorNumber);
    }

    public boolean isOn() {
        return on;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }
}