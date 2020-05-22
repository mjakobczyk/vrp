package com.mjakobczyk.vrp.def.solution.impl.antcolony;

import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.Ant;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.AntLocationsHolder;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.utils.AntUtils;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Ant Colony Optimization implementation of {@link com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy}.
 */
public class AntColonyVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {


    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        return runAntColonyOptimizationAlgorithm(vrpInput);
    }

    protected Optional<VrpOutput> runAntColonyOptimizationAlgorithm(final VrpInput vrpInput) {
        final AntColonyParameters parameters = new AntColonyParameters();
        final AntLocationsHolder locations = new AntLocationsHolder(vrpInput);
        final AntUtils antUtils = new AntUtils(locations);

        final double antsCount = parameters.getAntFactor() * locations.getAllLocations().size();
        final List<Ant> ants = antUtils.generate((int) antsCount);

        List<Location> bestSolution = new ArrayList<>();
        bestSolution.addAll(locations.getAllLocations());
        bestSolution.add(locations.getDepot());

        // Print initial cost
        System.out.println("Initial solution cost: " + getVrpUtils().countDistanceFor(bestSolution));

        for (int i = 0; i < parameters.getIterations(); ++i) {
            antUtils.setUp(ants, locations);
            antUtils.move(ants, parameters, locations);
            antUtils.updateTrailsUsedBy(ants, locations, parameters);
            final List<Location> currentBestSolution = antUtils.findBestTrailFrom(ants);

            if (getVrpUtils().countDistanceFor(currentBestSolution) < getVrpUtils().countDistanceFor(bestSolution)) {
                bestSolution = currentBestSolution;
            }
            antUtils.clear(ants);
        }

        // Print the final cost
        System.out.println("Best solution cost: " + getVrpUtils().countDistanceAndIncludeFirstLocationTwiceFor(bestSolution));

        return Optional.of(new DefaultVrpOutput(bestSolution));
    }

}
