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

    @Override
    public Optional<File> resolveFileFromPath(final String path) {
        LOG.log(Level.INFO, this.getClass().getName() + "#resolveFileFromPath does not contain any valuable implementation yet.");

        return Optional.empty();
    }

    @Override
    public Optional<File> resolveFileFromResources(final String fileName) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(fileName);

        if (resource == null) {
            LOG.log(Level.SEVERE,
                    "File " + fileName + " could not have been found!");
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
     * Constructor of DefaultVrpFileDataProvider.
     */
    public DefaultVrpFileDataProvider() {
    }
}
