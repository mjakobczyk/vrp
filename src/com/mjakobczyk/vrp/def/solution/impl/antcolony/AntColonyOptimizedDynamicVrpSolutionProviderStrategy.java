package com.mjakobczyk.vrp.def.solution.impl.antcolony;

import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.utils.AntOptimization;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.List;
import java.util.Optional;

/**
 * Optimized version of Ant Colony algorithm implementation of {@link com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy}.
 */
public class AntColonyOptimizedDynamicVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {

    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        return runAntColonyOptimizationAlgorithm(vrpInput);
    }

    protected Optional<VrpOutput> runAntColonyOptimizationAlgorithm(final VrpInput vrpInput) {
        // Print initial cost
        System.out.println("Initial solution cost: " + getVrpUtils().countDistanceAndIncludeFirstLocationTwiceFor(vrpInput.getLocations()));

        final AntOptimization antOptimization = new AntOptimization(vrpInput.getLocations());
        final List<Location> bestSolution = antOptimization.startAntOptimization();

        // Print final cost
        System.out.println("Final solution cost: " + getVrpUtils().countDistanceFor(bestSolution));

        return Optional.of(new DefaultVrpOutput(bestSolution));
    }
}
