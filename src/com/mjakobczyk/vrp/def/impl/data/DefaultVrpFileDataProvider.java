package com.mjakobczyk.vrp.def.impl.data;

import com.mjakobczyk.vrp.model.VrpInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * DefaultVrpFileDataProvider is a default implementation of service providing
 * data from from file to solve VRP.
 */
public class DefaultVrpFileDataProvider implements VrpFileDataProvider {

    /**
     * DefaultVrpDataProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpFileDataProvider.class));

    /**
     * Name of the file that contains VRP data.
     */
    private static final String DEFAULT_VRP_DATA_FILE_NAME = "defaultVrpDataFile";

    /**
     * Name of the file containg VRP data.
     */
    private final String fileName;

    @Override
    public Optional<VrpInput> getVrpInput() {
        final Optional<File> optionalFile;

        if (this.fileName.isEmpty()) {
            optionalFile = this.resolveFileFromResources(DefaultVrpFileDataProvider.DEFAULT_VRP_DATA_FILE_NAME);
        } else {
            optionalFile = this.resolveFileFromPath(this.fileName);
        }

        if (optionalFile.isEmpty()) {
            LOG.log(Level.INFO, "Provided default VRP data file does not contain data or does not exist.");
            return Optional.empty();
        }

        LOG.log(Level.INFO, "DefaultVrpSolutionProvider#getVrpInput does not contain valuable implementation yet.");

        return Optional.empty();
    }

    @Override
    public Optional<File> resolveFileFromPath(final String path) {
        // TODO

        return Optional.empty();
    }

    @Override
    public Optional<File> resolveFileFromResources(final String fileName) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(DefaultVrpFileDataProvider.DEFAULT_VRP_DATA_FILE_NAME);

        if (resource == null) {
            LOG.log(Level.SEVERE,
                    "File " + DefaultVrpFileDataProvider.DEFAULT_VRP_DATA_FILE_NAME + " could not have been found!");
            return Optional.empty();
        } else {
            return Optional.of(new File(resource.getFile()));
        }
    }

    @Override
    public Optional<VrpInput> resolveVrpInputDataFromFile(final File file) throws IOException {
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

    /**
     * Constructor of VrpFileDataProvider.
     *
     * @param fileName that contains data
     */
    public DefaultVrpFileDataProvider(final String fileName) {
        this.fileName = fileName;
    }
}
