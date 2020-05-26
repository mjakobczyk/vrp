package com.mjakobczyk.vrp.model;

import java.util.ArrayList;
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
        this.vehicles = new ArrayList<>();
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
        return vehicles.size() < this.maxVehiclesCount;
    }

    public void splitLocationsBetweenVehicles(final List<Location> locations) {
        final Location depot = locations.get(0);
        final List<Location> locationsToSplit = locations.subList(1, locations.size());
        final int locationsCount = locationsToSplit.size();
        final int maxVehiclesCount = this.getMaxVehiclesCount();
        final int optionalValue = locationsCount < 50 ? locationsCount / 5 : locationsCount / 10;

        while (!locationsToSplit.isEmpty()) {
            Vehicle vehicle = null;
            if (canCreateMore()) {
                final Optional<Vehicle> optionalVehicle = create();
                if (optionalVehicle.isPresent()) {
                    vehicle = optionalVehicle.get();
                }
            }

            vehicle.addToPlan((DeliveryLocation) depot);
            for (int i = 0; i < optionalValue; ++i) {
                if (locationsToSplit.isEmpty()) {
                    break;
                }
                final DeliveryLocation deliveryLocation = (DeliveryLocation) locationsToSplit.get(0);
                if (!vehicle.canHandle(deliveryLocation)) {
                    break;
                }
                vehicle.addToPlan(deliveryLocation);
                locationsToSplit.remove(0);
            }
        }



        System.out.println("Split done");
    }

    /**
     * Getter for vehicles.
     *
     * @return vehicles instances
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Informs about active vehicles count.
     *
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
