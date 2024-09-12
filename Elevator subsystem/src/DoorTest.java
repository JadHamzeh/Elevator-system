import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {

    @Test
    void closeDoor() {
        Door d = new Door();
        d.closeDoor();
        boolean x;
        x = d.checkOpen();
        assertEquals(x,false);

    }

    @Test
    void openDoor() {
        Door d = new Door();
        d.openDoor();
        boolean x;
        x = d.checkOpen();
        assertEquals(x,true);
    }


}