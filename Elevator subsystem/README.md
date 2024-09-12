# Three-Part System: Floor-Elevator-Scheduler

#### v1.0.0 2024-02-24

Table of Contents
* Installation process
* Introduction
* Code Structure
* Usage
* Contributors

### Installation process
1. Ensure the most up-to-date versions of Java are installed on your personal device.
2. Download program through the Iteration2 zip folder and save to personal device. Ensure to extract all files from the zip folder.
3. Ensure that all files are stored within the same folder as the program.
4. **Note**: MAKE SURE THAT THE "*example.txt*" FILE IS IN THE ROOT FOLDER. IT HAS BEEN OBSERVED TO BE WITHIN THE WRONG FOLDER WHEN DOWNLOADING ON SOME SYSTEMS. PROGRAM WON'T RUN OTHERWISE. CONTACT US IF THIS HAPPENS.

### Introduction
Welcome to the Three-Part System: Floor-Elevator-Scheduler , a Java program designed to showcase knowledge of UDP/IP and Threads, DatagramPackets, and DatagramSockets in a real-time concurrent system.

### Code Structure
The code is structured into several classes. The main ones of note are:
* Main: The main class that starts all threads for the simulation.
* Floor: Sends the information to the scheduler about all requests from all different floors.
* Scheduler: Intermediate host that passes messages between the floor and elevator. It acts like a usual internet service provider, which connects clients to their desired servers. It also controls all the lights.
* Elevator: The elevator that moves into the appropriate floor with the help of the motor.


### Usage
1. Through IDE of choice. Run the main method within Main.java.
*or*
1. Through terminal:
- javac Elevator.java Floor.java ArrivalSensor.java Scheduler.java Door.java ElevatorLamp.java ElevatorButton.java FloorLamp.java Main.java Motor.java 
- java Main

### Contributors
- Ilyaas Hussein 101230436
- Jake Swann 101193571
- Jad Hamzeh 101219884
- 

