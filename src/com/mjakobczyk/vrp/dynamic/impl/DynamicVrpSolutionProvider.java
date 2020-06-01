package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.def.DefaultVrpSolutionProvider;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.DefaultDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.*;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DynamicVrpSolutionProvider is an implementation of VrpSolutionProvider considering
 * incoming income of data.
 */
public class DynamicVrpSolutionProvider extends DefaultVrpSolutionProvider {

    /**
     * DynamicVrpSolutionProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DynamicVrpSolutionProvider.class));

    /**
     * List of currently used vehicles.
     */
    private VehicleManager vehicleManager;

    /**
     * Default constructor of DynamicVrpSolutionProvider.
     */
    public DynamicVrpSolutionProvider() {
        this.setVrpSolutionProviderStrategy(new DefaultDynamicVrpSolutionProviderStrategy());
        this.setVrpInputValidator(new DynamicVrpInputValidator());
    }

    /**
     * Constructor of DynamicVrpSolutionProvider.
     *
     * @param vrpSolutionProviderStrategy to inject
     */
    public DynamicVrpSolutionProvider(final VrpSolutionProviderStrategy vrpSolutionProviderStrategy) {
        this.setVrpSolutionProviderStrategy(vrpSolutionProviderStrategy);
        this.setVrpInputValidator(new DynamicVrpInputValidator());
    }

    @Override
    public Optional<VrpOutput> solve(VrpInput vrpInput) {
        if (!getVrpInputValidator().isValid(vrpInput)) {
            LOG.log(Level.SEVERE, "DynamicVrpSolutionProvider - incorrect input data provided!");
            return Optional.empty();
        }

        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;

        // dynamismDegree can be a value between 0 and 100.
        final int dynamismDegree = 0;
        // Originally getLocation() will return depot only, so there is a necessity to split
        // additional locations on basis of dynamismDegree.
        final List<Location> locations = new ArrayList<>(dynamicVrpInput.getLocations());
        locations.addAll(dynamicVrpInput.getAdditionalLocations());

        final int originalLocationsCount = locations.size();
        final int dynamicLocationsCount = (originalLocationsCount * dynamismDegree) / 100;
        final int staticLocationsCount = originalLocationsCount - dynamicLocationsCount;
        final List<Location> staticLocations = new ArrayList<>(locations.subList(0, staticLocationsCount));
        final List<Location> dynamicLocations = new ArrayList<>(locations.subList(staticLocationsCount, originalLocationsCount));

        final DynamicVrpInput parametrizedDynamicVrpInput = new DynamicVrpInput(staticLocations, dynamicLocations);

        this.vehicleManager = new VehicleManager(dynamicVrpInput.getMaxVehicleCapacity(), dynamicVrpInput.getVehiclesCount(), locations.get(0), this.getVrpSolutionProviderStrategy());

        // If static location contain anything more than depot coordinates
        if (staticLocations.size() > 1) {
            System.out.println(">>> Handling static locations");
            final Optional<VrpOutput> optionalVrpOutput = getVrpSolutionProviderStrategy().findOptimalRouteFor(parametrizedDynamicVrpInput);
            if (optionalVrpOutput.isEmpty()) {
                LOG.log(Level.SEVERE, "DynamicVrpSolutionProvider - static solution could not be retrieved from strategy!");
                return Optional.empty();
            }

            final VrpOutput vrpOutput = optionalVrpOutput.get();
            // If having static solution then divide it between available vehicles
            this.vehicleManager.splitLocationsBetweenVehicles(vrpOutput.getLocations());
            System.out.println(">>> Successfully split locations between vehicles");
        }

        // If there are any dynamic requests
        System.out.println(">>> Handling dynamic locations");
        while (!dynamicLocations.isEmpty()) {
            this.vehicleManager.handleDynamic(dynamicLocations.get(0));
            dynamicLocations.remove(0);
            System.out.println("There are " + dynamicLocations.size() + " dynamic locations left");
        }

        System.out.println(">>> Gathering final data");
        final List<List<Location>> tours = new ArrayList<>();
        double totalCost = 0.0D;
        for (final Vehicle vehicle : vehicleManager.getVehicles()) {
            final List<Location> vehicleTour = vehicle.getPlannedTour();
            tours.add(vehicleTour);
            totalCost += getVrpSolutionProviderStrategy().getVrpUtils().countDistanceFor(vehicleTour);
        }

        System.out.println("Total cost is : " + totalCost);

        return Optional.of(new DynamicVrpOutput(tours)); // temporary, refactor to include routes
    }

}
