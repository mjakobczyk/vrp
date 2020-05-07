package com.mjakobczyk.vrp.def.impl.solution.impl.antcolony;

import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.impl.solution.impl.antcolony.utils.AntOptimization;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.List;
import java.util.Optional;

/**
 * Optimized version of Ant Colony algorithm implementation of {@link com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy}.
 */
public class AntColonyOptimizedDynamicVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {

    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;
        return runAntColonyOptimizationAlgorithm(dynamicVrpInput);
    }

    protected Optional<VrpOutput> runAntColonyOptimizationAlgorithm(final DynamicVrpInput dynamicVrpInput) {
        // Print initial cost
        System.out.println("Initial solution cost: " + getVrpUtils().countDistanceAndIncludeFirstLocationTwiceFor(dynamicVrpInput.getLocations()));

        final AntOptimization antOptimization = new AntOptimization(dynamicVrpInput.getLocations());
        final List<Location> bestSolution = antOptimization.startAntOptimization();

        System.out.println("Final solution cost: " + getVrpUtils().countDistanceFor(bestSolution));
        System.out.println(bestSolution.size());
        System.out.println(bestSolution.containsAll(dynamicVrpInput.getLocations()));
//
        return Optional.of(new DynamicVrpOutput(dynamicVrpInput.getLocations()));
    }
}
