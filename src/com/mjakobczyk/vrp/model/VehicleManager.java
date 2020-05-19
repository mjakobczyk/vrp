package com.mjakobczyk.vrp.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * VehicleManager responsible for taking care of {@link Vehicle}s.
 */
public class VehicleManager {

    private int maxVehicleCapacity;
    private int maxVehiclesCount;
    private List<Vehicle> vehicles;
    private Map<Vehicle, Location> map;

    /**
     * Default constructor of VehicleManager.
     *
     * @param maxVehicleCapacity that each {@link Vehicle} is initialized with
     * @param maxVehiclesCount   that can be managed
     */
    public VehicleManager(final int maxVehicleCapacity, final int maxVehiclesCount) {
        this.maxVehicleCapacity = maxVehicleCapacity;
        this.maxVehiclesCount = maxVehiclesCount;
    }

    public Optional<Vehicle> create() {
        if (canCreateMore()) {
            final Vehicle vehicle = new Vehicle(this.maxVehicleCapacity);
            this.vehicles.add(vehicle);
            return Optional.of(vehicle);
        }

        return Optional.empty();
    }

    public boolean canCreateMore() {
        return vehicles.size() > this.maxVehiclesCount;
    }

    /**
     * Informs about active vehicles count.
     * @return vehicles count
     */
    public int getVehiclesCount() {
        return vehicles.size();
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
     * Getter for maxVehiclesCount.
     *
     * @return maxVehiclesCount value
     */
    public int getMaxVehiclesCount() {
        return maxVehiclesCount;
    }
}