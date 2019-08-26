package seng202.team1.data;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class MemoryStorageTest {

    @Test
    void testSingleton() {
        MemoryStorage instance1 = MemoryStorage.getInstance();
        MemoryStorage instance2 = MemoryStorage.getInstance();
        assertSame(instance1, instance2);
        // ^^ is this the right way to handle this?
    }

}