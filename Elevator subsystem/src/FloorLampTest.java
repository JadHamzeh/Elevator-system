import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorLampTest {

    @Test
    void turnOn() {
        FloorLamp f = new FloorLamp(3);
        f.turnOn();
        boolean x = f.isOn();
        assertEquals(x,true);

    }

    @Test
    void turnOff() {
        FloorLamp f = new FloorLamp(3);
        f.turnOff();
        boolean x = f.isOn();
        assertEquals(x,false);
    }

}