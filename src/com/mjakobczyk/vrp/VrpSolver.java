package com.mjakobczyk.vrp;

import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;

/**
 * VrpSolver allows consistent solution for VRP.
 */
public interface VrpSolver {

    /**
     * Solve starts calculations and provides a solution for given data.
     *
     * @return VrpOutput as a solution
     */
    Optional<VrpOutput> solve();
}
