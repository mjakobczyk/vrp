package com.mjakobczyk.vrp.def.impl.solution.impl.annealing.model;

/**
 * LocationsPair stores identity of two locations that can be used
 * for swapping purposes.
 */
public class LocationsPair {

    /**
     * First location ID.
     */
    private int firstLocation;

    /**
     * Second location ID.
     */
    private int secondLocation;

    /**
     * Constructor of LocationsPair.
     *
     * @param firstLocation  ID
     * @param secondLocation ID
     */
    public LocationsPair(final int firstLocation, final int secondLocation) {
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
    }

    /**
     * Getter of first location ID.
     *
     * @return first location ID
     */
    public int getFirstLocation() {
        return firstLocation;
    }

    /**
     * Getter of second location ID.
     *
     * @return second location ID.
     */
    public int getSecondLocation() {
        return secondLocation;
    }
}
