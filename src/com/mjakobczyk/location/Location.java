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
    private Coordinates coordinates;

    /**
     * Public constructor of the class.
     *
     * @param coordinates of the location
     */
    public Location(final Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Getter for location's coordinates.
     *
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Counts distance to the location passed as an arugment.
     *
     * @param location that should be analyzed
     * @return distance to the passed location
     */
    public double distanceTo(final Location location) {
        return Math.sqrt(
                Math.pow(location.getCoordinates().getCoordinateX() - this.getCoordinates().getCoordinateX(), 2) +
                        Math.pow(location.getCoordinates().getCoordinateY() - this.getCoordinates().getCoordinateY(), 2));
    }

}
