import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotorTest {

    @Test
    void moveMotor() {
        Motor motor = new Motor();
        motor.moveMotor(3);
        assertEquals(motor.getCurrentFloor(),3);
    }
}