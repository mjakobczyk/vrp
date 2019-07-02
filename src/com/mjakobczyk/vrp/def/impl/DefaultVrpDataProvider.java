package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.VrpDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * DefaultVrpDataProvider is a default implementation of data provider
 * for solving VRP.
 */
public class DefaultVrpDataProvider implements VrpDataProvider {

    /**
     * DefaultVrpDataProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpDataProvider.class));

    /**
     * Name of the file that contains VRP data.
     */
    private static final String DEFAULT_VRP_DATA_FILE_NAME = "defaultVrpDataFile";

    @Override
    public Optional<VrpInput> getVrpInput() {
        final Optional<File> optionalFile = this.getFileFromResources();

        if (optionalFile.isEmpty()) {
            LOG.log(Level.INFO, "Provided default VRP data file does not contain data or does not exist.");
            return Optional.empty();
        }

        LOG.log(Level.INFO, "DefaultVrpSolutionProvider#getVrpInput does not contain valuable implementation yet.");

        return Optional.empty();
    }

    private Optional<File> getFileFromResources() {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(DefaultVrpDataProvider.DEFAULT_VRP_DATA_FILE_NAME);

        if (resource == null) {
            LOG.log(Level.SEVERE,
                    "File " + DefaultVrpDataProvider.DEFAULT_VRP_DATA_FILE_NAME + " could not have been found!");
            return Optional.empty();
        } else {
            return Optional.of(new File(resource.getFile()));
        }
    }

    private Optional<VrpInput> resolveVrpInputDataFromFile(final File file) throws IOException {
        if (file == null) {
            LOG.log(Level.SEVERE, "File was null and could not be read!");
            return Optional.empty();
        }

        try (final FileReader fileReader = new FileReader(file);
             final BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            // TODO
            final Stream<String> lines = bufferedReader.lines();
        }

        return Optional.empty();
    }

}
