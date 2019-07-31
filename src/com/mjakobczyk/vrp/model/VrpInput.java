package com.mjakobczyk.vrp.model;

import com.mjakobczyk.location.Location;

import java.util.List;

/**
 * VrpInput describes input data for a VRP algorithm.
 */
public class VrpInput {

    /**
     * List of locations.
     */
    private VrpLocations vrpLocations;

    /**
     * Constructor of VrpInput.
     *
     * @param locations to initialize VrpInput with
     */
    public VrpInput(final List<Location> locations) {
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
     * Getter of VrpLocations
     *
     * @return VrpLocations
     */
    private VrpLocations getVrpLocations() {
        return vrpLocations;
    }

}
