package com.mjakobczyk.location.impl;

import com.mjakobczyk.coordinates.Coordinates;
import com.mjakobczyk.location.Location;

/**
 * Delivery location describes a place to which a specific
 * payload is going to be provided.
 */
public class DeliveryLocation extends Location {

    /**
     * Public constructor of the class.
     *
     * @param coordinates of the location
     */
    public DeliveryLocation(Coordinates coordinates) {
        super(coordinates);
    }
}
