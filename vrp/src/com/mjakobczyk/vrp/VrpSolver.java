package com.mjakobczyk.vrp;

/**
 * VrpSolver describes solving Vehicle Routing Problem.
 */
public interface VrpSolver {

    /**
     * Solve performs all the necessary calculations to solve
     * Vehicle Routing Problem.
     */
    VrpOutput solve(final VrpInput vrpInput);
}
