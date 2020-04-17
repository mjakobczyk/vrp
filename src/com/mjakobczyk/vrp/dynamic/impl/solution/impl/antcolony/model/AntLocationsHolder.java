package com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model;

import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.model.Location;

import java.util.List;

/**
 * AntLocationsHolder keeps information about {@link com.mjakobczyk.vrp.model.Location}
 * to be processed in Ant Colony algorithm.
 */
public class AntLocationsHolder {

    /**
     * AllLocations that were passed by {@link com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput}.
     */
    private List<Location> allLocations;

    /**
     * AdditionalLocations that were passed by {@link com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput}.
     */
    private List<Location> additionalLocations;

    /**
     * Depot is the first Location defined in allLocations;
     */
    private Location depot;

    /**
     * LocationsToVisit are all locations without depot.
     */
    private List<Location> locationsToVisit;

    /**
     * Default constructor of AntLocationsHolder.
     *
     * @param dynamicVrpInput that contains locations data
     */
    public AntLocationsHolder(final DynamicVrpInput dynamicVrpInput) {
        this.allLocations = dynamicVrpInput.getLocations();
        this.additionalLocations = dynamicVrpInput.getAdditionalLocations();
        this.depot = allLocations.get(0);
        this.locationsToVisit = allLocations.subList(1, allLocations.size());
    }

    /**
     * Getter for allLocations.
     *
     * @return allLocations
     */
    public List<Location> getAllLocations() {
        return allLocations;
    }

    /**
     * Getter for additionalLocations.
     *
     * @return additionalLocations
     */
    public List<Location> getAdditionalLocations() {
        return additionalLocations;
    }

    /**
     * Getter for depot.
     *
     * @return depot
     */
    public Location getDepot() {
        return depot;
    }

    /**
     * Getter for locationsToVisit.
     *
     * @return locationsToVisit
     */
    public List<Location> getLocationsToVisit() {
        return locationsToVisit;
    }

}
