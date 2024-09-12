/**
 * A class modelling the actual elevator
 * @author Group A1-2
 * @version February 24th, 2024
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Elevator implements Runnable{

    private Motor motor;
    private ArrivalSensor as;
    private int portNumber;
    private boolean working;
    private Door door;
    private String timeStamp;
    private String upOrDown;
    private int currentFloor;
    private int newFloor;
    private View view;
    private int elevatorNum;

    public Elevator(Motor motor, ArrivalSensor aSensor, int portNumber, View view) {
        this.motor = motor;
        this.as = aSensor;
        this.portNumber = portNumber;
        this.working = true;
        this.door = new Door();
        this.view = view;
        elevatorNum = portNumber - 66;
    }

    public int getFloor() {
        return motor.getCurrentFloor();
    }

    public int getPortNumber() {
        return portNumber;
    }

    public boolean isWorking() {
        return motor.isWorking();
    }

    public void setWorking(boolean w) {
        this.working = w;
        motor.setWorking(w);
    }

    /**
     * a function used to handle the sending of packets between elevcator and scheduler
     */
    public void receiveAndSend() {
        try {
            DatagramSocket receiveSocket = new DatagramSocket(portNumber);

            byte[] data = new byte[20];
            DatagramPacket receive = new DatagramPacket(data, data.length);
            receiveSocket.receive(receive);
            receiveSocket.close();

            int len = data[13] == 0 ? 2 : 4;

            timeStamp = new String(data, 0, 10);
            upOrDown = new String(data, 11, len);
            currentFloor = data[11+len+1];
            newFloor = data[11+len+3];


            if(timeStamp.startsWith("25") || timeStamp.startsWith("26")) {
                handleError(timeStamp, data, receive);
            }
            else {
                moveElevator(data, receive);
            }

        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleError(String timeStamp, byte[] data, DatagramPacket receive) {
        if(timeStamp.startsWith("25")){
            System.out.printf("Elevator %s Hard Fault. Elevator Stuck. Shutting Down\n", Thread.currentThread().getName() );
            Scheduler.pickBestElevator();
            setWorking(false);
            DatagramSocket sendSocket;
            try {
                sendSocket = new DatagramSocket();
                DatagramPacket sendPacket = new DatagramPacket(data,data.length,InetAddress.getLocalHost(),receive.getPort());
                sendSocket.send(sendPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sendSocket.close();
        } else {
            view.singleUpdate(elevatorNum, true, 0);
            door.closeDoor();
            System.out.printf("Elevator %s Transient Fault. Door Not Closed. Closing Door.\n", Thread.currentThread().getName() );
            moveElevator(data, receive);

        }
    }

    public void moveElevator(byte[] data, DatagramPacket receive) {
        try {
            if(motor.getCurrentFloor() == currentFloor) {
                System.out.printf("Elevator " + Thread.currentThread().getName() +  "  at floor %d. Going %s to floor %d\n", currentFloor, upOrDown, newFloor);
                motor.moveMotor(newFloor);
                as.detectArrival(newFloor, newFloor - currentFloor, true);
                System.out.printf("Arrived at destination floor %d. Passenger disembarked\n\n", motor.getCurrentFloor());
                view.singleUpdate(elevatorNum, false, motor.getCurrentFloor());
                DatagramSocket sendSocket = new DatagramSocket();
                DatagramPacket sendPacket = new DatagramPacket(data,data.length,InetAddress.getLocalHost(),receive.getPort());
                sendSocket.send(sendPacket);
                sendSocket.close();

            } else if (motor.getCurrentFloor() < currentFloor) {
                System.out.printf("Elevator " + Thread.currentThread().getName() +  " at floor %d. Going up to %d\n", motor.getCurrentFloor(), currentFloor);
                as.detectArrival(currentFloor, currentFloor - motor.getCurrentFloor(), false);
                motor.moveMotor(currentFloor);
                view.singleUpdate(elevatorNum, false, motor.getCurrentFloor());
                System.out.printf("Elevator " + Thread.currentThread().getName() +  " arrived at floor %d. Going %s to floor %d\n", currentFloor, upOrDown, newFloor);
                motor.moveMotor(newFloor);
                as.detectArrival(newFloor,  newFloor - currentFloor, true);
                view.singleUpdate(elevatorNum, false, motor.getCurrentFloor());
                System.out.printf("Arrived at destination floor %d. Passenger disembarked\n\n", motor.getCurrentFloor());
                DatagramSocket sendSocket = new DatagramSocket();
                DatagramPacket sendPacket = new DatagramPacket(data,data.length,InetAddress.getLocalHost(),receive.getPort());
                sendSocket.send(sendPacket);
                sendSocket.close();
            } else {
                System.out.printf("Elevator " + Thread.currentThread().getName() +  " at floor %d. Going down to %d\n", motor.getCurrentFloor(), currentFloor);
                as.detectArrival(currentFloor, currentFloor - motor.getCurrentFloor(), false);
                motor.moveMotor(currentFloor);
                view.singleUpdate(elevatorNum, false, motor.getCurrentFloor());
                System.out.printf("Elevator " + Thread.currentThread().getName() +  " arrived at floor %d. Going %s to floor %d\n", currentFloor, upOrDown, newFloor);
                motor.moveMotor(newFloor);
                view.singleUpdate(elevatorNum, false, motor.getCurrentFloor());
                as.detectArrival(newFloor, newFloor - currentFloor, true);
                System.out.printf("Arrived at destination floor %d. Passenger disembarked\n\n", motor.getCurrentFloor());
                DatagramSocket sendSocket = new DatagramSocket();
                DatagramPacket sendPacket = new DatagramPacket(data,data.length,InetAddress.getLocalHost(),receive.getPort());
                sendSocket.send(sendPacket);
                sendSocket.close();
            }

        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(true) {
            receiveAndSend();
        }

    }
}