package com.mjakobczyk.vrp.model.def;

import com.mjakobczyk.location.Location;
import com.mjakobczyk.vrp.model.VrpInput;

import java.util.List;

/**
 * DefaultVrpInput is a default model class for VrpInput.
 */
public class DefaultVrpInput extends VrpInput {

    /**
     * Constructor of DefaultVrpInput.
     *
     * @param locations list
     */
    public DefaultVrpInput(List<Location> locations) {
        super(locations);
    }
}
