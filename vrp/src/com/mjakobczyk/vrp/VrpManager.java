package com.mjakobczyk.vrp;

/**
 * VrpManager allows consistent solution for VRP.
 */
public interface VrpManager {

    /**
     * Solve starts calculations and provides a solution for given data.
     *
     * @return VrpOutput as a solution
     */
    VrpOutput solve();
}
