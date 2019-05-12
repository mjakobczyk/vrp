package com.mjakobczyk.vrp;

/**
 * VrpDataProvider arranges input and output data for Vehicle Routing Problem.
 */
public interface VrpDataProvider {

    /**
     * Gets input data for VRP algorithm.
     *
     * @return input data
     */
    VrpInput getVrpInput();
}
