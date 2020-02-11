package com.mjakobczyk.vrp.model;

/**
 * Coordinates reflect position in space.
 */
public class Coordinates {

    /**
     * First coordinate.
     */
    private int coordinateX;

    /**
     * Second coordinate.
     */
    private int coordinateY;

    /**
     * Public constructor of the class.
     *
     * @param coordinateX which is first coordinate
     * @param coordinateY which is second coordinate
     */
    public Coordinates(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    /**
     * Getter for first coordinate.
     *
     * @return first coordinate
     */
    public int getCoordinateX() {
        return coordinateX;
    }

    /**
     * Getter for second coordinate.
     *
     * @return second coordinate
     */
    public int getCoordinateY() {
        return coordinateY;
    }
}
