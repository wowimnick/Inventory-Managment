package com.nick.inv.defs;

/**
 * Creates an in-house part.
 *
 * @author Nick Popelnukh
 *
 */

public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for the new instance of an InHouse type object.
     *
     * @param id the ID for the part
     * @param name the name for the part
     * @param price the price for the part
     * @param stock the inventory stock for the part
     * @param min the stock minimum for the part
     * @param max the part maximum for the part
     * @param machineId the machine ID for the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);

        this.machineId = machineId;
    }

    /**
     * The getter for the machineID
     *
     * @return returns the machineID for the part
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * The setter for machineID
     *
     * @param machineId the machine ID of the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
