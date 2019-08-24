package seng202.team1.data;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class InMemoryDAOTest {

    @Test
    void testSingleton() {
        InMemoryDAO instance1 = InMemoryDAO.getInstance();
        InMemoryDAO instance2 = InMemoryDAO.getInstance();
        assertSame(instance1, instance2);
        // ^^ is this the right way to handle this?
    }

}