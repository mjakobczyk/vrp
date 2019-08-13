package com.mjakobczyk.vrp.dynamic.model;

import com.mjakobczyk.location.Location;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.List;

/**
 * DynamicVrpOutput is a model class adjusted for dynamic implementation of VrpOutput.
 */
public class DynamicVrpOutput extends VrpOutput {

    /**
     * Constructor of DynamicVrpOutput
     *
     * @param locations list
     */
    public DynamicVrpOutput(final List<Location> locations) {
        super(locations);
    }
}
