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
        final List<Location> locations = new ArrayList<>(dynamicVrpInput.getLocations());
        locations.addAll(dynamicVrpInput.getAdditionalLocations());
//        final List<Location> additionalLocations = dynamicVrpInput.getAdditionalLocations();
        final List<Location> additionalLocations = Collections.emptyList();

        final DynamicVrpInput parametrizedDynamicVrpInput = new DynamicVrpInput(locations, additionalLocations);

        this.vehicleManager = new VehicleManager(dynamicVrpInput.getMaxVehicleCapacity(), dynamicVrpInput.getVehiclesCount());

        // If static location contain anything more than depot coordinates
        if (locations.size() > 1) {
            final Optional<VrpOutput> optionalVrpOutput = getVrpSolutionProviderStrategy().findOptimalRouteFor(parametrizedDynamicVrpInput);
            if (optionalVrpOutput.isEmpty()) {
                LOG.log(Level.SEVERE, "DynamicVrpSolutionProvider - static solution could not be retrieved from strategy!");
                return Optional.empty();
            }

            final VrpOutput vrpOutput = optionalVrpOutput.get();
            // If having static solution then divide it between available vehicles
            this.vehicleManager.splitLocationsBetweenVehicles(vrpOutput.getLocations());
        }

        // If there are any dynamic requests
        if (!additionalLocations.isEmpty()) {
            // Get information about dynamic requests
            // TODO

            // Process all dynamic requests and split them betwen vehicles
            // TODO
        }

        final List<List<Location>> tours = new ArrayList<>();
        for (final Vehicle vehicle : vehicleManager.getVehicles()) {
            final List<Location> vehicleTour = vehicle.getPlannedTour();
            tours.add(vehicleTour);
//            final List<Location> debuggingList = vehicleTour.subList(0, 10); // TODO: remove
//            debuggingList.add(vehicleTour.get(0));  // TODO: remove
//            tours.add(debuggingList);

        }

        return Optional.of(new DynamicVrpOutput(tours)); // temporary, refactor to include routes
    }

    protected void splitStaticLocationsBetweenVehicles(final List<Location> locations, final List<Vehicle> vehicles) {

    }
}
