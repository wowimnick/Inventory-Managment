package com.nick.inv.defs;

/**
 * Creates a new instance of an OutSourced object.
 *
 * @author Nick Popelnukh
 */

public class OutSourced extends Part {
    private String company;

    /**
     * Constructor for the new instance of an OutSourced type object.
     * @param id The ID for the part.
     * @param name The name for the part.
     * @param price The price for the part.
     * @param stock The inventory stock for the part.
     * @param min The stock minimum for the part.
     * @param max The part maximum for the part.
     * @param company The parts company name.
     */

    public OutSourced(int id, String name, double price, int stock, int min, int max, String company) {
        super(id, name, price, stock, min, max);

        this.company = company;
    }

    /**
     * The getter for the company name.
     *
     * @return returns the company name for the part
     */

    public String getCompany() {
        return company;
    }

    /**
     * The setter for the company name.
     * @param company The company name.
     */

    public void setCompany(String company) {
        this.company = company;
    }
}
