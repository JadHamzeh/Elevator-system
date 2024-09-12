import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {

    @BeforeEach
    public void beforeAll() {

    }

    @Test
    void testGetFloor() {
        Motor m = new Motor();
        ArrivalSensor as = new ArrivalSensor();
        View v = new View();
        Elevator e = new Elevator(m, as, 69, v);
        m.moveMotor(15);
        assertEquals(e.getFloor(), m.getCurrentFloor());
        assertEquals(e.getFloor(), 15);
        assertEquals(m.getCurrentFloor(), 15);
    }

    @Test
    void testGetPortNumber() {
        Motor motor = new Motor();
        ArrivalSensor arrivalSensor = new ArrivalSensor();
        int portNumber = 69; // Example port number
        View v = new View();
        Elevator elevator = new Elevator(motor, arrivalSensor, portNumber, v);

        assertEquals(portNumber, elevator.getPortNumber());
    }

    @Test
    void receiveAndSend() {
        try {
            // Create a mock motor and arrival sensor
            Motor motor = new Motor();
            ArrivalSensor arrivalSensor = new ArrivalSensor();

            // Example data to send
            byte[] sendData = "Test data".getBytes();

            // Set up a testing environment
            int portNumber = 69; // Example port number
            DatagramSocket sendSocket = new DatagramSocket();
            DatagramSocket receiveSocket = new DatagramSocket(portNumber);
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            View v = new View();

            // Start the elevator thread
            Thread elevatorThread = new Thread(new Elevator(motor, arrivalSensor, portNumber, v));
            elevatorThread.start();

            // Send a packet to the elevator
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(), portNumber);
            sendSocket.send(sendPacket);

            // Receive the packet sent by the elevator
            receiveSocket.receive(receivePacket);
            String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // Assertions
            assertEquals("Test data", receivedData);

            // Close sockets
            sendSocket.close();
            receiveSocket.close();

            // Interrupt elevator thread to stop it
            elevatorThread.interrupt();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}