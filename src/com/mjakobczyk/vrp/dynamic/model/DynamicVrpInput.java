package com.mjakobczyk.vrp.dynamic.model;

import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.VrpInput;

import java.util.List;

/**
 * DynamicVrpInput is a model class adjusted for dynamic implementation of VrpInput.
 */
public class DynamicVrpInput extends VrpInput {

    /**
     * Additional locations represent requests that may appear during calculations.
     */
    private List<Location> additionalLocations;

    /**
     * maxVehicleCapacity available to each Vehicle.
     */
    private int maxVehicleCapacity;

    /**
     * vehiclesCount that can be used in algorithm.
     */
    private int vehiclesCount;

    /**
     * Constructor of DynamicVrpInput.
     *
     * @param locations           list
     * @param additionalLocations list
     */
    public DynamicVrpInput(final List<Location> locations, final List<Location> additionalLocations) {
        super(locations);
        this.additionalLocations = additionalLocations;
        this.vehiclesCount = 1;
        this.maxVehicleCapacity = 100;
    }

    public DynamicVrpInput(final List<Location> locations, final List<Location> additionalLocations,
                           final int maxVehicleCapacity, final int vehiclesCount) {
        super(locations);
        this.additionalLocations = additionalLocations;
        this.maxVehicleCapacity = maxVehicleCapacity;
        this.vehiclesCount = vehiclesCount;
    }

    /**
     * Getter of additional locations.
     *
     * @return additionalLocations list
     */
    public List<Location> getAdditionalLocations() {
        return additionalLocations;
    }

    /**
     * Getter for maxVehicleCapacity.
     *
     * @return maxVehicleCapacity value
     */
    public int getMaxVehicleCapacity() {
        return maxVehicleCapacity;
    }

    /**
     * Getter for vehiclesCount.
     *
     * @return vehiclesCount count
     */
    public int getVehiclesCount() {
        return vehiclesCount;
    }
}
