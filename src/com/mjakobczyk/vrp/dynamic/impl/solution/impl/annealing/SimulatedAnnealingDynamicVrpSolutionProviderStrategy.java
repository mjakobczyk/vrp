package com.mjakobczyk.vrp.dynamic.impl.solution.impl;

import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing.model.SimulatedAnnealingTemperature;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * SimulatedAnnealingDynamicVrpSolutionProviderStrategy provides implementation of
 * simulated annealing algorithm, choosing optimal route for VRP. This implementation
 * includes dynamic location requests.
 */
public class SimulatedAnnealingDynamicVrpSolutionProviderStrategy implements VrpSolutionProviderStrategy {

    /**
     * SimulatedAnnealingDynamicVrpSolutionProviderStrategy logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(SimulatedAnnealingDynamicVrpSolutionProviderStrategy.class));

    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        if (vrpInput != null) {
            return vrpInput.getLocations().isEmpty() ? Optional.empty() : runSimulatedAnnealingAnalysisFor(vrpInput);
        }

        return Optional.empty();
    }

    protected Optional<VrpOutput> runSimulatedAnnealingAnalysisFor(final VrpInput vrpInput) {
        final SimulatedAnnealingTemperature temperature = new SimulatedAnnealingTemperature();

        while (temperature.isValid()) {


            temperature.decrease();
        }

        return Optional.empty();
    }
}
