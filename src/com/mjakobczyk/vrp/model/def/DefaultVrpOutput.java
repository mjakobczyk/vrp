package com.mjakobczyk.vrp.model.def;

import com.mjakobczyk.location.Location;
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
    public DefaultVrpOutput(List<Location> locations) {
        super(locations);
    }
}
