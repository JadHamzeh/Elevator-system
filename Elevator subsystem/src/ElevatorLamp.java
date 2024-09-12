/**
 * A class representing the lights on the buttons inside the elevator
 * @author Group A1-2
 * @version February 24th, 2024
 */
public class ElevatorLamp {

    private boolean on;
    private int lampNumber;

    public ElevatorLamp(int lampNumber){
        this.lampNumber = lampNumber;
    }

    public void turnOn(boolean on){
        this.on = on;
    }

    public boolean isOn(){
        return on;
    }





}