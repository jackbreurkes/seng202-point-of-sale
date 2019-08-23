package seng202.team1.data;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class StorageMemoryTest {

    @Test
    void testSingleton() {
        StorageMemory instance1 = StorageMemory.getInstance();
        StorageMemory instance2 = StorageMemory.getInstance();
        assertSame(instance1, instance2);
        // ^^ is this the right way to handle this?
    }

}