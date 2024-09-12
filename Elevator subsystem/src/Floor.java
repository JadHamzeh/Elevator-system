/**
 * A class modelling floors in a building
 *
 * @author Group A1-2
 * @version February 24th, 2024
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class Floor implements Runnable{

    private final String path = "iteration5_input_v1.txt";
    private String timeStamp;
    private int currentFloor;
    private String upOrDown;
    private int destinationFloor;
    private ArrayList<Motor> motors;
    private View view;

    public Floor(ArrayList<Motor> motors, View view) {
        this.motors = motors;
        this.view = view;
    }

    public ArrayList<Motor> getMotors() {
        return this.motors;
    }

    /**
     *
     */
    public void readAndHandleInput() {
        String filePath = path;
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {


                String[] input = scanner.nextLine().split("\\s+");
                if(input[0].equals("exit")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                if(input[0].equals("time")) {
                    continue;
                }
                System.out.printf("Floor: Elevator 1 is at floor %d, Working: %b\n", motors.get(0).getCurrentFloor(), motors.get(0).isWorking());
                System.out.printf("Floor: Elevator 2 is at floor %d, Working: %b\n", motors.get(1).getCurrentFloor(), motors.get(1).isWorking());
                System.out.printf("Floor: Elevator 3 is at floor %d, Working: %b\n", motors.get(2).getCurrentFloor(), motors.get(2).isWorking());
                System.out.printf("Floor: Elevator 4 is at floor %d, Working: %b\n\n", motors.get(3).getCurrentFloor(), motors.get(3).isWorking());
                view.update(this.motors);
                timeStamp = input[0];
                currentFloor = Integer.valueOf(input[1]);
                upOrDown = input[2];
                destinationFloor = Integer.valueOf(input[3]);

                byte[] data = convertToByteArray();
                DatagramSocket sendSocket = new DatagramSocket();
                DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 23);
                sendSocket.send(packet);
                sendSocket.close();

                DatagramSocket receiveSocket = new DatagramSocket(32);
                receiveSocket.receive(packet);
                receiveSocket.close();



                // System.out.println(timeStamp + currentFloor + upOrDown + destinationFloor);
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(UnknownHostException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    byte[] convertToByteArray() {
        byte[] data = new byte[timeStamp.length() + upOrDown.length() + 6];
        byte[] ts = timeStamp.getBytes();
        byte[] ud = upOrDown.getBytes();


        System.arraycopy(ts, 0, data, 0, ts.length);
        data[ts.length] = 0;
        System.arraycopy(ud, 0, data, ts.length + 1, ud.length);
        data[ts.length + ud.length + 1] = 0;
        data[ts.length + ud.length + 2] = (byte) currentFloor;
        data[ts.length + ud.length + 3] = 0;
        data[ts.length + ud.length + 4] = (byte) destinationFloor;
        data[ts.length + ud.length + 5] = 0;

        System.out.println(Arrays.toString(data));
        System.out.println(timeStamp + " " + upOrDown + " " + currentFloor + " " + destinationFloor);

        return data;
    }

    @Override
    public void run() {
        readAndHandleInput();
    }
}