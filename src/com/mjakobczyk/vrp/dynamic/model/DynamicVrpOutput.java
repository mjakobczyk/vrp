package com.mjakobczyk.vrp.dynamic.model;

import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.List;

/**
 * DynamicVrpOutput is a model class adjusted for dynamic implementation of VrpOutput.
 */
public class DynamicVrpOutput extends VrpOutput {

    private List<List<Location>> listOfListsOfLocations;

    public DynamicVrpOutput(final List<List<Location>> input) {
        this.listOfListsOfLocations = input;
    }

    /**
     * Getter for list of lists of locations.
     *
     * @return list of lists of locations data
     */
    public List<List<Location>> getListOfListsOfLocations() {
        return listOfListsOfLocations;
    }
}
