package com.mjakobczyk.order;

import com.mjakobczyk.location.Location;

/**
 * Order reflects a single call from a customer that expects
 * articles to be delivered to the specific location.
 */
public class Order {

    /**
     * Location where order should be provided.
     */
    private Location location;

    /**
     * Public constructor of the class.
     *
     * @param location of the order
     */
    public Order(final Location location) {
        this.location = location;
    }

    /**
     * Getter for order's location.
     *
     * @return location of the order
     */
    public Location getLocation() {
        return location;
    }
}
