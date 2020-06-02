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
    private VrpDataProvider vrpDataProvider;

    /**
     * VrpSolutionProvider as chosen implementation of the algorithm.
     */
    private VrpSolutionProvider vrpSolutionProvider;

    /**
     * Solve performs calculations and provides a solution for given data.
     *
     * @return VrpOutput as a solution
     */
    public Optional<VrpOutput> solve() {
        if (!areInternalComponentsPresent()) {
            return Optional.empty();
        }

        final Optional<VrpInput> optionalVrpInput = retrieveAlgorithmInput();
        if (optionalVrpInput.isPresent()) {
            return getVrpSolutionProvider().solve(optionalVrpInput.get());
        }

        logger().log(Level.SEVERE, "No data was provided from VrpDataProvider to VrpSolver. Algorithm could not proceed.");
        return Optional.empty();
    }

    protected boolean areInternalComponentsPresent() {
        if (getVrpDataProvider() == null) {
            logger().log(Level.SEVERE, "No VrpDataProvider was provided.");
            return false;
        }

        if (getVrpSolutionProvider() == null) {
            logger().log(Level.SEVERE, "No VrpSolutionProvider was provided.");
            return false;
        }

        return true;
    }

    protected Optional<VrpInput> retrieveAlgorithmInput() {
        return getVrpDataProvider().getVrpInput();
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

    public void setVrpDataProvider(VrpDataProvider vrpDataProvider) {
        this.vrpDataProvider = vrpDataProvider;
    }

    public void setVrpSolutionProvider(VrpSolutionProvider vrpSolutionProvider) {
        this.vrpSolutionProvider = vrpSolutionProvider;
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
