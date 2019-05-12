package com.mjakobczyk.vrp.impl.def;

import com.mjakobczyk.vrp.VrpInput;
import com.mjakobczyk.vrp.VrpOutput;
import com.mjakobczyk.vrp.VrpSolver;

/**
 * DefaultVrpSolver is a default implementation of VrpSolver.
 */
public class DefaultVrpSolver implements VrpSolver {

    @Override
    public VrpOutput solve(VrpInput vrpInput) {
        // TODO: provide default solve implementation
        System.out.println("Mock: DefaultVrpSolver solve()");
        return null;
    }
}
