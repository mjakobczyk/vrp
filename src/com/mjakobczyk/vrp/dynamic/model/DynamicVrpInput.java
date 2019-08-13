package com.mjakobczyk.vrp.dynamic.model;

import com.mjakobczyk.location.Location;
import com.mjakobczyk.vrp.model.VrpInput;

import java.util.List;

/**
 * DynamicVrpInput is a model class adjusted for dynamic implementation of VrpInput.
 */
public class DynamicVrpInput extends VrpInput {

    /**
     * Additional locations represent requests that may appear during calculations.
     */
    private List<Location> additionalLocations;

    /**
     * Constructor of DynamicVrpInput.
     *
     * @param locations           list
     * @param additionalLocations list
     */
    public DynamicVrpInput(final List<Location> locations, final List<Location> additionalLocations) {
        super(locations);
        this.additionalLocations = additionalLocations;
    }

    /**
     * Getter of additional locations.
     *
     * @return additionalLocations list
     */
    private List<Location> getAdditionalLocations() {
        return additionalLocations;
    }
}
