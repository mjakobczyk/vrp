package com.mjakobczyk.vrp.dynamic.impl.solution.impl;

import com.mjakobczyk.location.Location;
import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.impl.solution.impl.DefaultVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * DynamicVrpSolutionProviderStrategy provides implementation of strategy
 * allowing to find optimal route for Dynamic VRP.
 */
public class DynamicDefaultVrpSolutionProviderStrategy implements VrpSolutionProviderStrategy {

    /**
     * DefaultVrpSolutionProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpSolutionProviderStrategy.class));

    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        if (vrpInput != null) {
            return Optional.of(mapVrpInputToVrpOutput(vrpInput));
        }

        return Optional.empty();
    }

    protected VrpOutput mapVrpInputToVrpOutput(final VrpInput vrpInput) {
        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;
        final List<Location> locations = dynamicVrpInput.getLocations();
        locations.addAll(dynamicVrpInput.getAdditionalLocations());

        return new DynamicVrpOutput(locations);
    }
}
