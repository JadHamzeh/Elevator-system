import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ArrivalSensorTest {

    @Test
    void testDetectArrival() {
        ArrivalSensor a = new ArrivalSensor();

        boolean x = a.detectArrival(3,2,true);

        assertEquals(x,true);
        assertFalse(x==false);


    }
}