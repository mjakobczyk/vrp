package com.mjakobczyk.vrp.model;

import java.util.List;

/**
 * VrpLocations describes the way Locations in VRP are handled.
 */
class VrpLocations {

    /**
     * List of locations.
     */
    private List<Location> locations;

    /**
     * Constructor of VrpLocations.
     *
     * @param locations that should be included
     */
    VrpLocations(final List<Location> locations) {
        this.locations = locations;
    }

    /**
     * Getter of locations.
     *
     * @return list of locations
     */
    List<Location> getLocations() {
        return this.locations;
    }

}
