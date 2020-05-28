package com.mjakobczyk.vrp.model;

import com.mjakobczyk.vrp.def.DefaultVrpUtils;
import com.mjakobczyk.vrp.def.model.DefaultVrpInput;
import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.service.VrpUtils;

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
    private Location depot;
    private List<Vehicle> vehicles;
    private Map<Vehicle, Location> map;
    private VrpSolutionProviderStrategy strategy;
    private VrpUtils vrpUtils;

    /**
     * Default constructor of VehicleManager.
     *
     * @param maxVehicleCapacity that each {@link Vehicle} is initialized with
     * @param maxVehiclesCount   that can be managed
     */
    public VehicleManager(final int maxVehicleCapacity, final int maxVehiclesCount, final Location depot, final VrpSolutionProviderStrategy strategy) {
        this.maxVehicleCapacity = maxVehicleCapacity;
        this.maxVehiclesCount = maxVehiclesCount;
        this.vehicles = new ArrayList<>();
        this.strategy = strategy;
        this.vrpUtils = new DefaultVrpUtils();
        this.depot = depot;
    }

    public Optional<Vehicle> create() {
        if (canCreateMore()) {
            final Vehicle vehicle = new Vehicle(this.maxVehicleCapacity);
            vehicle.addToPlan((DeliveryLocation)this.depot);
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
    }

    public void handleDynamic(final Location location) {
        final DeliveryLocation deliveryLocation  = (DeliveryLocation) location;

        final Optional<Vehicle> optionalVehicle = chooseMatchingVehicleFor(deliveryLocation);
        if (optionalVehicle.isPresent()) {
            final Vehicle vehicle = optionalVehicle.get();
            vehicle.addToPlan(deliveryLocation);
        }
    }

    protected Optional<Vehicle> chooseMatchingVehicleFor(final Location location) {
        if (vehicles.isEmpty()) {
            return this.create();
        }

        return findVehicleThatGivesLowestCost(location);
    }

    protected Optional<Vehicle> findVehicleThatGivesLowestCost(final Location location) {
        final DeliveryLocation deliveryLocation  = (DeliveryLocation) location;
        Vehicle bestVehicle = null;
        double lowestCostIncrease = Double.MAX_VALUE;
        List<Location> bestLocationsList = new ArrayList<>();

        for (final Vehicle vehicle : this.vehicles) {

            if (!vehicle.canHandle(deliveryLocation)) {
                continue;
            }

            final SimulationResult result = this.simulateAdditionOfLocationTo(vehicle, location);

            if (result.getCostDifference() < lowestCostIncrease) {
                bestVehicle = vehicle;
                lowestCostIncrease = result.getCostDifference();
                bestLocationsList.clear();
                bestLocationsList = new ArrayList<>(result.getLocationList());
            }
        }

        if (bestVehicle != null) {
            bestVehicle.establishNewPlan(bestLocationsList);
            return Optional.of(bestVehicle);
        }

        return this.create();
    }

    protected SimulationResult simulateAdditionOfLocationTo(final Vehicle vehicle, final Location location) {
        final DeliveryLocation deliveryLocation  = (DeliveryLocation) location;
        final Vehicle simulationVehicle = new Vehicle(vehicle.getCurrentCapacity());
        simulationVehicle.establishNewPlan(vehicle.getAllLocations());

        final double costBefore = this.vrpUtils.countDistanceFor(simulationVehicle.getPlannedTour());
        final List<Location> locationsBefore = new ArrayList<>(simulationVehicle.getAllLocations());

        final Optional<VrpOutput> optionalVrpOutput = this.strategy.findOptimalRouteFor(new DefaultVrpInput(locationsBefore));
        if (optionalVrpOutput.isEmpty()) {
            return new SimulationResult(null, Double.MAX_VALUE);
        }

        final DefaultVrpOutput defaultVrpOutput = (DefaultVrpOutput) optionalVrpOutput.get();
        final double costAfter = this.vrpUtils.countDistanceAndIncludeFirstLocationTwiceFor(defaultVrpOutput.getLocations());
        final List<Location> locationsAfter = new ArrayList<>(defaultVrpOutput.getLocations());

        return new SimulationResult(locationsAfter, costAfter - costBefore);
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
