package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.VrpDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;

import java.io.File;
import java.util.Optional;

/**
 * VrpFileDataProvider is an implementation of service providing
 * data from from file to solve VRP.
 */
public class VrpFileDataProvider implements VrpDataProvider {

    private final String fileName;

    @Override
    public Optional<VrpInput> getVrpInput() {
        //TODO

        return Optional.empty();
    }

    private Optional<File> getFileFromPath(final String fileName) {
        // TODO

        return Optional.empty();
    }

    /**
     * Constructor of VrpFileDataProvider.
     *
     * @param fileName that contains data
     */
    public VrpFileDataProvider(final String fileName) {
        this.fileName = fileName;
    }
}
