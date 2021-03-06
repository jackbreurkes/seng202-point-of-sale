package seng202.team1.data;

import seng202.team1.model.Supplier;

import java.util.Set;

/**
 * Data access object interface specification for interacting with Suppliers stored within the system.
 */
public interface SupplierDAO {

    /**
     * returns all the Suppliers stored in the system.
     * @return a List of all Suppliers stored in the system
     */
    Set<Supplier> getAllSuppliers();

    /**
     * gets a single Supplier from the system.
     * @param code the Supplier's unique code
     * @return the desired Supplier or null if not found
     */
    Supplier getSupplierById(String code);

    /**
     * adds a Supplier to storage. the supplier will be stored using its code attribute.
     * @param supplier the Supplier to store
     */
    void addSupplier(Supplier supplier);

    /**
     * sets the properties of a Supplier in storage to those of a new Supplier.
     * the new supplier's code should be the same as the one that is being changed.
     * @param alteredSupplier the Supplier
     */
    void updateSupplier(Supplier alteredSupplier);

    /**
     * remove a Supplier from storage.
     * @param code the code of the Supplier to remove
     */
    void removeSupplier(String code);

}
