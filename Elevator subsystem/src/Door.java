/**
 * A class modelling the door of an elevator
 * @author Group A1-2
 * @version February 24th, 2024
 */
public class Door {

    private boolean open;

    public Door() {
        open = false;
    }

    public void closeDoor() {
        open = false;
        try {
            Thread.sleep(1000); // sped up to make program run faster
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Door is closed. Took 2 seconds to close");
    }

    public void openDoor() {
        open = true;
        try {
            Thread.sleep(1000); // sped up to make program run faster
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Door is open. Took 2 seconds to open");

    }

    public boolean checkOpen() {
        return open;
    }
}