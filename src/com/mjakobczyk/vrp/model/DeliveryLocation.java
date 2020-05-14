package com.mjakobczyk.vrp.model;

/**
 * Delivery location describes a place to which a specific
 * payload is going to be provided.
 */
public class DeliveryLocation extends Location {

    private int demand;

    /**
     * Public constructor of the class.
     *
     * @param coordinates of the location
     */
    public DeliveryLocation(final Coordinates coordinates) {
        super(coordinates);
    }

    public DeliveryLocation(final Coordinates coordinates, final int demand) {
        super(coordinates);
        this.demand = demand;
    }

    public int getDemand() {
        return demand;
    }
}
