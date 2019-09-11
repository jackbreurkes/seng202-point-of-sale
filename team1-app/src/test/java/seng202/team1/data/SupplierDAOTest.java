package seng202.team1.data;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import seng202.team1.model.Supplier;
import seng202.team1.util.InvalidDataCodeException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static seng202.team1.util.PhoneType.WORK;

@Disabled
class SupplierDAOTest {
    private SupplierDAO supplierStorage;
    private Supplier testSupplier;
    @BeforeEach
    void setupStorage() {
        testSupplier = new Supplier("s1",  "CountUp",  "12 High Street Christchurch",  "3451234", WORK, "dave@countup.co.nz", "countup.co.nz/high");

    }

    @Test
    void testGetAllSuppliers() {
        Set<Supplier> items = supplierStorage.getAllSuppliers();
        assertEquals(0, items.size());

        supplierStorage.addSupplier(testSupplier);

        items = supplierStorage.getAllSuppliers();
        assertEquals(1, items.size());
        assertTrue(items.contains(testSupplier));
    }

    @Test
    void testGetSupplierByID() {

        assertNull(supplierStorage.getSupplierById(testSupplier.getId()));

        supplierStorage.addSupplier(testSupplier);
        assertEquals(testSupplier, supplierStorage.getSupplierById(testSupplier.getId()));

        assertNull(supplierStorage.getSupplierById("UNUSED CODE"));

        assertNull(supplierStorage.getSupplierById(null));

    }

    @Test
    void testAddSupplier() {
        Set<Supplier> items = supplierStorage.getAllSuppliers();
        assertEquals(0, items.size());

        supplierStorage.addSupplier(testSupplier);

        items = supplierStorage.getAllSuppliers();
        assertEquals(1, items.size());

        //test adding duplicate suppliers
        assertThrows(InvalidDataCodeException.class, () -> {
            supplierStorage.addSupplier(testSupplier);
        });

        // test adding null
        assertThrows(NullPointerException.class, () -> {
            supplierStorage.addSupplier(null);
        });
    }

    @Test
    void testUpdateSupplier() {
        supplierStorage.addSupplier(testSupplier);

        Supplier alteredSupplier = new Supplier("s1",  "COUNTBEYOND",  "14 High Street Christchurch",  "3451269", WORK, "dave2@countup.co.nz", "COUNTBEYOND.co.nz/high");
        supplierStorage.updateSupplier(alteredSupplier);
        assertEquals(alteredSupplier, supplierStorage.getSupplierById(testSupplier.getId()));

        // the item's code does not exist within storage
        Supplier nonExistantSupplier = new Supplier("s1",  "Mystery",  "Somewhere or other",  "0000000", WORK, "some@where.com", "jhusdfuifh.com");
        assertThrows(InvalidDataCodeException.class, () -> {
            supplierStorage.updateSupplier(nonExistantSupplier);
        });

        //cant update a null value
        assertThrows(NullPointerException.class, () -> {
            supplierStorage.updateSupplier(null);
        });

    }

    @Test
    void testRemoveSupplier() {
        supplierStorage.addSupplier(testSupplier);

        supplierStorage.removeSupplier(testSupplier.getId());
        assertNull(supplierStorage.getSupplierById(testSupplier.getId()));

        //cant remove an item not in storage
        assertThrows(InvalidDataCodeException.class, () -> {
            supplierStorage.removeSupplier(testSupplier.getId());
        });

    }
}
