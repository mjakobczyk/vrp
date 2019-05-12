package com.mjakobczyk.vrp.impl.def;

import com.mjakobczyk.vrp.*;

/**
 * DefaultVrpManager integrates multiple services together to provide a consistent\
 * solution for VRP.
 */
public class DefaultVrpManager implements VrpManager {

    /**
     * VrpDataProvider for VRP.
     */
    private final VrpDataProvider vrpDataProvider;

    /**
     * VrpSolver as chosen implementation of the algorithm.
     */
    private final VrpSolver vrpSolver;


    @Override
    public VrpOutput solve() {
        return this.vrpSolver.solve(this.vrpDataProvider.getVrpInput());
    }

    /**
     * Getter for VrpDataProvider.
     *
     * @return VrpDataProvider
     */
    public VrpDataProvider getVrpDataProvider() {
        return vrpDataProvider;
    }

    /**
     * Getter for VrpSolver.
     *
     * @return VrpSolver
     */
    public VrpSolver getVrpSolver() {
        return vrpSolver;
    }

    /**
     * Constructor for DefaultVrpManager.
     *
     * @param vrpDataProvider for data
     * @param vrpSolver       for algorithm implementation
     */
    public DefaultVrpManager(VrpDataProvider vrpDataProvider, VrpSolver vrpSolver) {
        this.vrpDataProvider = vrpDataProvider;
        this.vrpSolver = vrpSolver;
    }
}
