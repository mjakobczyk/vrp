package com.mjakobczyk.vrp.def;

import com.mjakobczyk.vrp.model.VrpInput;

import java.util.Optional;

/**
 * VrpDataProvider arranges input and output data for Vehicle Routing Problem.
 */
public interface VrpDataProvider {

    /**
     * Gets input data for VRP algorithm.
     *
     * @return input data
     */
    Optional<VrpInput> getVrpInput();
}
