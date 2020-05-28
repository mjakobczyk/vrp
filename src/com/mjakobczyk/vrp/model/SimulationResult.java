package com.mjakobczyk.vrp.model;

import java.util.List;

/**
 * SimulationResult resembles result of simulation that includes cost increase and locations list.
 */
public class SimulationResult {

    private List<Location> locationList;
    private double costDifference;

    public SimulationResult(final List<Location> locationList, final double costDifference) {
        this.locationList = locationList;
        this.costDifference = costDifference;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public double getCostDifference() {
        return costDifference;
    }
}
