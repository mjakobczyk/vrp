package com.mjakobczyk.vrp.def.impl.solution;

import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;

/**
 * VrpSolutionProviderStrategy describes actual implementation of dealing with VRP.
 */
public interface VrpSolutionProviderStrategy {

    /**
     * Returns found optimal router for given VrpInput.
     *
     * @param vrpInput to analyze
     * @return found optimal route
     */
    Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput);
}
