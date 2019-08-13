package com.mjakobczyk.vrp.def.impl.solution.impl;

import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;
import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * DefaultVrpSolutionProviderStrategy provides default implementation of strategy
 * allowing to find optimal route for VRP.
 */
public class DefaultVrpSolutionProviderStrategy implements VrpSolutionProviderStrategy {

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
        return new DefaultVrpOutput(vrpInput.getLocations());
    }
}
