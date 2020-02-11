package com.mjakobczyk.vrp.def.model;

import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.List;

/**
 * DefaultVrpOutput is a default model class for VrpOutput.
 */
public class DefaultVrpOutput extends VrpOutput {

    /**
     * Constructor of DefaultVrpOutput.
     *
     * @param locations list
     */
    public DefaultVrpOutput(final List<Location> locations) {
        super(locations);
    }
}
