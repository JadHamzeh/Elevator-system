/**
 * A class that represents the arrival sensor of an elevators floor, handling the logic of letting the system know the elevator has arrived
 * @author Group A1-2
 * @version February 24th, 2024
 */
public class ArrivalSensor {
    private int currentFloor;
    private long TotalTime;

    public ArrivalSensor() {
        currentFloor = 1; // Assuming the elevator starts at floor 1
    }

    public void setCurrentFloor(int floor) {
        currentFloor = floor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }


    /**
     * function used to handle the logic of detecting the arrival of the eleavtor on a floor
     * @param floor
     * @param n represents the difference between floors, used to calculate time
     * @param passenger used to see if the elevator is picking someone up
     */
    public boolean detectArrival(int floor, int n, boolean passenger) {
        setCurrentFloor(floor);
        n = Math.abs(n);
        try {
            Thread.sleep(2000*n); // sped up to make program run faster
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TotalTime += 5;
        if(passenger) {
            System.out.println("Door is Open. Passenger has entered the elevator.");
        }
        System.out.println("Arrived at floor " + floor + ". Took " + n * 5 + " seconds to get there.");
        if(passenger)
            return true;
        return false;
    }


}