package com.mjakobczyk.vrp.model;

import java.util.List;

/**
 * VrpOutput describes output data from a VRP algorithm.
 */
public abstract class VrpOutput {

    /**
     * List of locations.
     */
    private VrpLocations vrpLocations;

    /**
     * Cost of tour.
     */
    private double cost;

    /**
     * Default constructor of VrpOutput. Does nothing by design.
     */
    public VrpOutput() {}

    /**
     * Constructor of VrpOutput.
     *
     * @param locations to initialize VrpInput with
     */
    public VrpOutput(final List<Location> locations) {
        this.vrpLocations = new VrpLocations(locations);
    }

    /**
     * Getter of Locations.
     *
     * @return list of locations
     */
    public List<Location> getLocations() {
        return getVrpLocations().getLocations();
    }

    /**
     * Getter of VrpLocations.
     *
     * @return VrpLocations
     */
    private VrpLocations getVrpLocations() {
        return vrpLocations;
    }

    public void setVrpLocations(VrpLocations vrpLocations) {
        this.vrpLocations = vrpLocations;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(final double cost) {
        this.cost = cost;
    }
}
