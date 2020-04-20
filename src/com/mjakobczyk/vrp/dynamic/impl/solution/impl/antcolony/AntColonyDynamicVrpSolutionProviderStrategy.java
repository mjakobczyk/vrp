package com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony;

import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.Ant;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.AntLocationsHolder;
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
        final AntUtils antUtils = new AntUtils();
        final AntColonyParameters parameters = new AntColonyParameters();
        final AntLocationsHolder locations = new AntLocationsHolder(dynamicVrpInput);

        final double antsCount = parameters.getAntFactor() * locations.getAllLocations().size();
        final List<Ant> ants = antUtils.generate((int) antsCount);
        antUtils.setUp(ants, locations);

        List<Location> bestSolution = Collections.emptyList();

        for (int i = 0; i < parameters.getIterations(); ++i) {
            antUtils.move(ants, parameters, locations);
            antUtils.updateTrailsUsedBy(ants);
            bestSolution = antUtils.findBestTrailFrom(ants);
        }

        return Optional.of(new DynamicVrpOutput(bestSolution));
    }

}
