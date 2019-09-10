package com.mjakobczyk.vrp.def.impl.data.impl;

import com.mjakobczyk.coordinates.Coordinates;
import com.mjakobczyk.location.Location;
import com.mjakobczyk.location.impl.DeliveryLocation;
import com.mjakobczyk.vrp.def.impl.data.VrpFileDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.def.model.DefaultVrpInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            final Stream<String> lines = bufferedReader.lines();

            return resolveVrpInputFromFileContent(lines);
        } catch (final FileNotFoundException e) {
            LOG.log(Level.SEVERE, this.getClass().getName() + " " + e.getMessage());
            return Optional.empty();
        }
    }

    protected Optional<VrpInput> resolveVrpInputFromFileContent(final Stream<String> lines) {
        final List<Location> locations = new ArrayList<>();
        lines.forEach(line -> locations.add(getLocationFromInputFileLine(line)));
        return Optional.of(new DefaultVrpInput(locations));
    }

    protected Location getLocationFromInputFileLine(final String line) {
        final List<String> coordinates = Arrays.asList(line.split(" "));
        return new DeliveryLocation(new Coordinates(Integer.parseInt(coordinates.get(0)), Integer.parseInt(coordinates.get(1))));
    }

    /**
     * Constructor of DefaultVrpFileDataProvider.
     */
    public DefaultVrpFileDataProvider() {
    }

}
