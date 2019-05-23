package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.*;
import com.mjakobczyk.vrp.def.VrpDataProvider;
import com.mjakobczyk.vrp.def.VrpSolutionProvider;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DefaultVrpSolver integrates multiple services together to provide a consistent\
 * solution for VRP.
 */
public class DefaultVrpSolver implements VrpSolver {

    /**
     * DefaultVrpSolver logger, providing data about runtime behaviour.
     */
    private static final Logger log = Logger.getLogger(String.valueOf(DefaultVrpSolver.class));

    /**
     * VrpDataProvider for VRP.
     */
    private final VrpDataProvider vrpDataProvider;

    /**
     * VrpSolutionProvider as chosen implementation of the algorithm.
     */
    private final VrpSolutionProvider vrpSolutionProvider;

    /**
     * Default implementation of solving VRP.
     *
     * @return VrpOutput containing solution
     */
    @Override
    public Optional<VrpOutput> solve() {
        final Optional<VrpInput> optionalVrpInput = getVrpDataProvider().getVrpInput();

        if (optionalVrpInput.isPresent()) {
            return getVrpSolutionProvider().solve(optionalVrpInput.get());
        }

        log.log(Level.SEVERE, "No data was provided from VrpDataProvider to VrpSolver. Algorithm could not proceed");
        return Optional.empty();
    }

    /**
     * Getter for VrpDataProvider.
     *
     * @return VrpDataProvider
     */
    protected VrpDataProvider getVrpDataProvider() {
        return vrpDataProvider;
    }

    /**
     * Getter for VrpSolutionProvider.
     *
     * @return VrpSolutionProvider
     */
    protected VrpSolutionProvider getVrpSolutionProvider() {
        return vrpSolutionProvider;
    }

    /**
     * Constructor for DefaultVrpSolver.
     *
     * @param vrpDataProvider     for data
     * @param vrpSolutionProvider for algorithm implementation
     */
    public DefaultVrpSolver(final VrpDataProvider vrpDataProvider, final VrpSolutionProvider vrpSolutionProvider) {
        this.vrpDataProvider = vrpDataProvider;
        this.vrpSolutionProvider = vrpSolutionProvider;
    }
}
