package com.mjakobczyk.vrp.def.solution.impl;

import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;
import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * DefaultVrpSolutionProviderStrategy provides default implementation of strategy
 * allowing to find optimal route for VRP.
 */
public class DefaultVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {

    /**
     * DefaultVrpSolutionProviderStrategy logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpSolutionProviderStrategy.class));

    /**
     * Default constructor of {@link com.mjakobczyk.vrp.def.solution.impl.DefaultVrpSolutionProviderStrategy}.
     */
    public DefaultVrpSolutionProviderStrategy() {
        super.setVrpInputValidator(null);
    }

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
