package com.mjakobczyk.vrp.def.solution.impl.antcolony;

import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.AntColonyParameters;
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

    private AntColonyParameters parameters;

    public AntColonyOptimizedDynamicVrpSolutionProviderStrategy(final AntColonyParameters parameters) {
        super();
        this.parameters = parameters;
    }

    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        return runAntColonyOptimizationAlgorithm(vrpInput);
    }

    protected Optional<VrpOutput> runAntColonyOptimizationAlgorithm(final VrpInput vrpInput) {
        final AntOptimization antOptimization = new AntOptimization(vrpInput.getLocations(), this.parameters);
        final List<Location> bestSolution = antOptimization.startAntOptimization();

        return Optional.of(new DefaultVrpOutput(bestSolution));
    }

    public void setParameters(final AntColonyParameters parameters) {
        this.parameters = parameters;
    }
}
