package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.def.DefaultVrpSolutionProvider;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.DefaultDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.Vehicle;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

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
    private List<Vehicle> vehicles;

    /**
     * Default constructor of DynamicVrpSolutionProvider.
     */
    public DynamicVrpSolutionProvider() {
        this.setVrpSolutionProviderStrategy(new DefaultDynamicVrpSolutionProviderStrategy());
    }

    /**
     * Constructor of DynamicVrpSolutionProvider.
     *
     * @param vrpSolutionProviderStrategy to inject
     */
    public DynamicVrpSolutionProvider(final VrpSolutionProviderStrategy vrpSolutionProviderStrategy) {
        super(vrpSolutionProviderStrategy);
        setVrpInputValidator(new DynamicVrpInputValidator());
    }

    @Override
    public Optional<VrpOutput> solve(VrpInput vrpInput) {
        if (!getVrpInputValidator().isValid(vrpInput)) {
            LOG.log(Level.SEVERE, "DynamicVrpSolutionProvider - incorrect input data provided!");
            return Optional.empty();
        }

        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;
        final List<Location> locations = dynamicVrpInput.getLocations();
        final List<Location> additionalLocations = dynamicVrpInput.getAdditionalLocations();



        // Step 1 - solve static problem if
        final Optional<VrpOutput> optionalVrpOutput = getVrpSolutionProviderStrategy().findOptimalRouteFor(dynamicVrpInput);
        if (optionalVrpOutput.isEmpty()) {
            LOG.log(Level.SEVERE, "DynamicVrpSolutionProvider - static solution could not be retrieved from strategy!");
            return Optional.empty();
        }

        final VrpOutput vrpOutput = optionalVrpOutput.get();
        // final List<Location> additionalLocations

        // Step 2 - if having static solution then divide it between available vehicles
        // TODO: split locations between avaiable vehicles

        // Step 3 - get information about dynamic requests
        // TODO

        // Step 4 - process all dynamic requests and split them betwen vehicles
        // TODO

        return Optional.of(new DynamicVrpOutput(locations)); // temporary, refactor to include routes
    }
}
