package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.def.impl.DefaultVrpDataProvider;
import com.mjakobczyk.vrp.def.impl.data.VrpFileDataProvider;

import java.util.logging.Logger;

/**
 * DynamicVrpDataProvider is an implementation of data provider
 * for solving Dynamic VRP.
 */
public class DynamicVrpDataProvider extends DefaultVrpDataProvider {

    /**
     * Constructor of DynamicVrpDataProvider.
     *
     * @param fileName            to be read
     * @param vrpFileDataProvider for reading data
     */
    public DynamicVrpDataProvider(final String fileName, final VrpFileDataProvider vrpFileDataProvider) {
        super(fileName, vrpFileDataProvider);
        this.setDefaultVrpDataFileName("dynamicVrpDataFile.txt");
    }
}
