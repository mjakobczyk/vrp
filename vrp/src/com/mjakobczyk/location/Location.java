package com.mjakobczyk.location;

import com.mjakobczyk.coordinates.Coordinates;

/**
 * Location is an abstract class to define a general description
 * of source or destination place.
 */
public abstract class Location {

    /**
     * Every location has its own coordinates that allows to define its position.
     */
    protected Coordinates coordinates;

    /**
     * Getter for location's coordinates.
     *
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Public constructor of the class.
     *
     * @param coordinates of the location
     */
    public Location(final Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
