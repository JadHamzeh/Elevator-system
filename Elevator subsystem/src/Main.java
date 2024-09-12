import java.util.ArrayList;

/**
 * A Class that is used to run the program
 *
 * @author Group A1-2
 * @version February 24th, 2024
 */
public class Main {
    public static void main(String[] args) {
        View v = new View();
        Motor m1 = new Motor();
        Motor m2 = new Motor();
        Motor m3 = new Motor();
        Motor m4 = new Motor();
        FloorLamp fl = new FloorLamp(1);
        Door door = new Door();
        DirectionLamp dl = new DirectionLamp();
        ArrivalSensor as = new ArrivalSensor();
        Elevator e1 = new Elevator(m1, as, 67, v);
        Elevator e2 = new Elevator(m2, as, 68, v);
        Elevator e3 = new Elevator(m3, as, 69, v);
        Elevator e4 = new Elevator(m4, as, 70, v);

        ArrayList<Elevator> cars = new ArrayList<Elevator>();
        cars.add(e1);
        cars.add(e2);
        cars.add(e3);
        cars.add(e4);

        ArrayList<Motor> motors = new ArrayList<Motor>();
        motors.add(m1);
        motors.add(m2);
        motors.add(m3);
        motors.add(m4);

        Thread floor = new Thread(new Floor(motors, v), "floor");
        Thread scheduler = new Thread(new Scheduler(fl, door, dl, as, cars), "scheduler");
        Thread elevator1 = new Thread(e1, "1");
        Thread elevator2 = new Thread(e2, "2");
        Thread elevator3 = new Thread(e3, "3");
        Thread elevator4 = new Thread(e4, "4");

        scheduler.start();
        elevator1.start();
        elevator2.start();
        elevator3.start();
        elevator4.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        floor.start();
    }
}