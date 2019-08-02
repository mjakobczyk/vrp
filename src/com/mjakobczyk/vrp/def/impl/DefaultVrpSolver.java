package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.*;
import com.mjakobczyk.vrp.VrpDataProvider;
import com.mjakobczyk.vrp.VrpSolutionProvider;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * DefaultVrpSolver integrates multiple services together to provide a consistent
 * solution for VRP.
 */
public class DefaultVrpSolver extends VrpSolver {

    /**
     * DefaultVrpSolver logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpSolver.class));

    /**
     * Default implementation of solving VRP.
     *
     * @return VrpOutput containing solution
     */
    @Override
    public Optional<VrpOutput> solve() {
        return super.solve();
    }

    /**
     * Logger provider for DefaultVrpSolver.
     *
     * @return logger
     */
    @Override
    protected Logger logger() {
        return LOG;
    }

    /**
     * Constructor of DefaultVrpSolver.
     *
     * @param vrpDataProvider     for data
     * @param vrpSolutionProvider for algorithm implementation
     */
    public DefaultVrpSolver(final VrpDataProvider vrpDataProvider, final VrpSolutionProvider vrpSolutionProvider) {
        super(vrpDataProvider, vrpSolutionProvider);
    }
}
