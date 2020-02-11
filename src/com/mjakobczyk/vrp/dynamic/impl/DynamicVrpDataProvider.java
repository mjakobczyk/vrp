package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.def.impl.DefaultVrpDataProvider;
import com.mjakobczyk.vrp.def.impl.data.VrpFileDataProvider;

/**
 * DynamicVrpDataProvider is an implementation of data provider
 * for solving Dynamic VRP.
 */
public class DynamicVrpDataProvider extends DefaultVrpDataProvider {

    private static final String DYNAMIC_VRP_DATA_FILE_NAME = "defaultVrpDataFile.txt";

    /**
     * Constructor of DynamicVrpDataProvider.
     *
     * @param fileName            to be read
     * @param vrpFileDataProvider for reading data
     */
    public DynamicVrpDataProvider(final String fileName, final VrpFileDataProvider vrpFileDataProvider) {
        super(fileName, vrpFileDataProvider);
        // TODO: remove?
    }

}
