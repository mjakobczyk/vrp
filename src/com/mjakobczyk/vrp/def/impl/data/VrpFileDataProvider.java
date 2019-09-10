package com.mjakobczyk.vrp.def.impl.data;

import com.mjakobczyk.vrp.model.VrpInput;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * VrpFileDataProvider describes providing data from file to solve VRP.
 */
public interface VrpFileDataProvider {

    /**
     * Returns file resolved from given path.
     *
     * @param path to the file
     * @return file to read data from
     */
    Optional<File> resolveFileFromPath(final String path);

    /**
     * Returns file resolved from file name that should be included in resources.
     *
     * @param fileName from resource
     * @return file to read data from
     */
    Optional<File> resolveFileFromResources(final String fileName);

    /**
     * Reads file containing VRP data and maps it to VrpInput.
     *
     * @param file to read data from
     * @return VrpInput as a data for VRP
     */
    Optional<VrpInput> resolveVrpInputDataFromFile(final File file) throws IOException;
}
