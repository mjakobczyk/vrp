package com.mjakobczyk.vrp;

import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;

/**
 * VrpSolutionProvider describes solving Vehicle Routing Problem.
 */
public interface VrpSolutionProvider {

    /**
     * Solve performs all the necessary calculations to solve
     * Vehicle Routing Problem.
     */
    Optional<VrpOutput> solve(final VrpInput vrpInput);
}
