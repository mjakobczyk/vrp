package com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony;

import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.Ant;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.utils.AntUtils;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Ant Colony Optimization implementation of {@link com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy}.
 */
public class AntColonyDynamicVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {


    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;
        return runAntColonyOptimizationAlgorithm(dynamicVrpInput);
    }

    protected Optional<VrpOutput> runAntColonyOptimizationAlgorithm(final DynamicVrpInput dynamicVrpInput) {
        final List<Location> allLocations = dynamicVrpInput.getLocations();
        final List<Location> additionalLocations = dynamicVrpInput.getAdditionalLocations();
        final Location depot = allLocations.get(0);
        final List<Location> locationsToVisit = allLocations.subList(1, allLocations.size());
        List<Location> bestSolution = Collections.emptyList();

        final AntColonyParameters antColonyParameters = new AntColonyParameters();
        final AntUtils antUtils = new AntUtils();
        final double antsCount = antColonyParameters.getAntFactor() * allLocations.size();
        final List<Ant> ants = antUtils.generate((int) antsCount, allLocations);
        antUtils.setUp(ants);

        for (int i = 0; i < antColonyParameters.getIterations(); ++i) {
            antUtils.move(ants, antColonyParameters);
            antUtils.updatePathsUsedBy(ants);
            bestSolution = antUtils.findBestPathFrom(ants);
        }

        return Optional.of(new DynamicVrpOutput(bestSolution));
    }

}
