/**
 * A class modelling a scheduler that controls the functions of the elevator, ensuring each component
 * receives instructions
 * @author Group A1-2
 * @version February 24th, 2024
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Scheduler implements Runnable {

    private Motor motor;
    private FloorLamp fl;
    private Door door;
    private DirectionLamp dl;
    private ArrivalSensor as;
    private static String upOrDown;
    private static int currentFloor;
    private static ArrayList<Elevator> elevators;
    public Scheduler(FloorLamp fl, Door door, DirectionLamp dl, ArrivalSensor as, ArrayList<Elevator> elevators) {
        this.motor = motor;
        this.fl = fl;
        this.door = door;
        this.dl = dl;
        this.as = as;
        this.elevators = elevators;
    }

    /**
     * a function that handles the logic of sending and receiving packets
     * it receives a packet from floor, then sends the information to elevator, elevator then sends a response back, etc.
     */
    public void receiveAndSend() {
        try {
            byte[] requestBuffer = new byte[20];
            DatagramSocket receiveSocket = new DatagramSocket(23); // Create DatagramSocket outside the loop

            while(true) {
                DatagramPacket receivePacket = new DatagramPacket(requestBuffer, requestBuffer.length);
                receiveSocket.receive(receivePacket);
                int len = requestBuffer[13] == 0 ? 2 : 4;
                String timeStamp = new String(requestBuffer, 0, 10);
                upOrDown = new String(requestBuffer, 11, len);
                currentFloor = requestBuffer[11+len+1];
                int newFloor = requestBuffer[11+len+3];
                // System.out.printf("CurrentFloor: %d - Going %s to floor %d at %s\n", currentFloor, upOrDown, newFloor, timeStamp);

                fl.setFloorNumber(currentFloor);
                fl.turnOn();
                door.openDoor();
                System.out.printf("Floor %d's %s light ON\n\n", fl.getFloorNumber(), upOrDown);

                DatagramSocket sendSocket = new DatagramSocket(); // Create a new DatagramSocket for sending
                DatagramPacket sendPacket = new DatagramPacket(requestBuffer,requestBuffer.length, InetAddress.getLocalHost(), pickBestElevator().getPortNumber());
                sendSocket.send(sendPacket);

                // Receive the second packet
                DatagramPacket receivePacket2 = new DatagramPacket(requestBuffer,requestBuffer.length);
                sendSocket.receive(receivePacket2);
                fl.turnOff();
                door.closeDoor();
                System.out.printf("Floor %d's %s light OFF\n\n", fl.getFloorNumber(), upOrDown);
                System.out.println("---------------------------------------------------------------------------------\n");

                // Send the second packet
                DatagramPacket sendPacket2 = new DatagramPacket(requestBuffer,requestBuffer.length, InetAddress.getLocalHost(), 32);
                sendSocket.send(sendPacket2);

                // Close the sockets
                sendSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Elevator pickBestElevator() {
        ArrayList<Elevator> possibleCars = new ArrayList<Elevator>();
        if(upOrDown.equals("up")) {
            for(Elevator e : elevators) {
                if(currentFloor > e.getFloor() && e.isWorking()) {
                    possibleCars.add(e);
                }
            }
        } else if(upOrDown.equals("down")) {
            for(Elevator e : elevators) {
                if(currentFloor < e.getFloor() && e.isWorking()) {
                    possibleCars.add(e);
                }
            }
        }

        if(possibleCars.isEmpty()) {
            return closestElevator(elevators);
        }
        return closestElevator(possibleCars);

    }

    public static Elevator closestElevator(ArrayList<Elevator> cars) {
        Elevator closest = cars.get(0);

        for(Elevator e: cars) {
            if(Math.abs(e.getFloor() - currentFloor) < Math.abs(closest.getFloor() - currentFloor)){
                closest = e;
            }
        }

        return closest;
    }

    @Override
    public void run() {
        receiveAndSend();
    }
}