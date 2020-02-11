package com.mjakobczyk.vrp.service;

import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * VrpSolver allows consistent solution for VRP.
 */
public abstract class VrpSolver {

    /**
     * VrpDataProvider for VRP.
     */
    private final VrpDataProvider vrpDataProvider;

    /**
     * VrpSolutionProvider as chosen implementation of the algorithm.
     */
    private final VrpSolutionProvider vrpSolutionProvider;

    /**
     * Solve performs calculations and provides a solution for given data.
     *
     * @return VrpOutput as a solution
     */
    public Optional<VrpOutput> solve() {
        if (getVrpDataProvider() == null) {
            logger().log(Level.SEVERE, "No VrpDataProvider was provided.");
            return Optional.empty();
        }

        final Optional<VrpInput> optionalVrpInput = getVrpDataProvider().getVrpInput();

        if (getVrpSolutionProvider() == null) {
            logger().log(Level.SEVERE, "No VrpSolutionProvider was provided.");
            return Optional.empty();
        }

        if (optionalVrpInput.isPresent()) {
            return getVrpSolutionProvider().solve(optionalVrpInput.get());
        }

        logger().log(Level.SEVERE, "No data was provided from VrpDataProvider to VrpSolver. Algorithm could not proceed.");
        return Optional.empty();
    }

    protected abstract Logger logger();

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
     * Constructor for VrpSolver.
     *
     * @param vrpDataProvider     for data
     * @param vrpSolutionProvider for algorithm implementation
     */
    public VrpSolver(final VrpDataProvider vrpDataProvider, final VrpSolutionProvider vrpSolutionProvider) {
        this.vrpDataProvider = vrpDataProvider;
        this.vrpSolutionProvider = vrpSolutionProvider;
    }
}
