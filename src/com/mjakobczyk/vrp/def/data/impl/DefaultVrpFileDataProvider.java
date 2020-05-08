package com.mjakobczyk.vrp.def.data.impl;

import com.mjakobczyk.vrp.model.Coordinates;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.DeliveryLocation;
import com.mjakobczyk.vrp.def.data.VrpFileDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.def.model.DefaultVrpInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private static final String RESOURCES_PREFIX = "resources/";

    @Override
    public Optional<File> resolveFileFromPath(final String path) {
        return Optional.of(new File(path));
    }

    @Override
    public Optional<File> resolveFileFromResources(final String fileName) {
        return Optional.of(new File(RESOURCES_PREFIX + fileName));
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

}
