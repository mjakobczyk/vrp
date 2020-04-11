package com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony;

import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;

/**
 * Ant Colony Optimization implementation of {@link com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy}.
 */
public class AntColonyDynamicVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {


    @Override
    public Optional<VrpOutput> findOptimalRouteFor(VrpInput vrpInput) {
        return runAntColonyOptimizationAlgorithm();
    }

    protected Optional<VrpOutput> runAntColonyOptimizationAlgorithm() {
        // step 1. - take algorithm input parameters (AntColonyParameters)
        final AntColonyParameters antColonyParameters = new AntColonyParameters();

        // step 2. TODO - spawn ants (Ant, create ant factory service)
        // step 3. TODO - setup spawned ants
        // step 4. TODO - move ants
        // step 5. TODO - update paths
        // step 6. TODO - update the best solution
        // step 7. TODO - collect the best solution

        return Optional.empty();
    }

}
