package com.mjakobczyk.vrp.service;

import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.VrpOutput;

/**
 * VrpDrawer show VRP solution.
 */
public interface VrpDrawer {

    /**
     * Draw default result of VRP algorithm.
     *
     * @param defaultVrpOutput instance
     */
    void draw(final DefaultVrpOutput defaultVrpOutput);

    /**
     * Draw dynamic result of VRP algorithm.
     *
     * @param dynamicVrpOutput instance
     */
    void draw(final DynamicVrpOutput dynamicVrpOutput);
}
