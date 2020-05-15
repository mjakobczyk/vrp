package com.mjakobczyk.vrp.dynamic.impl.data.impl;

import com.mjakobczyk.vrp.def.data.impl.DefaultVrpFileDataProvider;
import com.mjakobczyk.vrp.model.Coordinates;
import com.mjakobczyk.vrp.model.DeliveryLocation;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.VrpInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * MendeleyDynamicVrpFileDataProvider handles specific DVRP input data.
 * This data is organized in an .vrp file and contains specific schema.
 * Please check this website for more details: https://data.mendeley.com/datasets/3fwc3twwn6/1
 */
public class MendeleyDynamicVrpFileDataProvider extends DefaultVrpFileDataProvider {

    @Override
    protected Optional<VrpInput> resolveVrpInputFromFileContent(final Stream<String> streamLines) {
        // TODO: construct Mendley-specific DynamicVrpInput
        final List<Location> locations = new ArrayList<>();
        final List<Location> additionalLocations = new ArrayList<>();
        List<String> lines = streamLines.collect(Collectors.toList());

        removeFirstLinesFrom(lines, 4); // Skip first 4 lines: VRPTEST, COMMENT, COMMENT, NAME
        int numberOfDepots = handleNumberOfDepots(lines);
        int numberOfCapacities = handleNumberOfCapacities(lines);
        int numberOfVisits = handleNumberOfVisits(lines);
        int numberOfLocations = handleNumberOfLocations(lines);
        int numberOfVehicles = handleNumberOfVehicles(lines);
        int capacities = handleCapacities(lines);
        removeFirstLinesFrom(lines, 3); // Skip depots coords, it is known that depot is first location
        int [] demands = handleDemandsSection(lines, numberOfVisits);
        Coordinates[] coordinates = handleCoordinatesSection(lines, numberOfLocations);
        handleDepotLocation(lines);
        handleVisitLocationsSection(lines, numberOfVisits);
        handleDurationsSection(lines, numberOfVisits);
        handleDepotTimeWindowSection(lines);
        handleTimestep(lines);
        handleTimeAvailabilitySection(lines, numberOfVisits);

        return Optional.empty();
    }

    protected List<String> getFirstLinesFrom(final List<String> lines, final int count) {
        return lines.subList(0, count);
    }

    protected void removeFirstLinesFrom(List<String> lines, final int count) {
        for (int i = 0; i < count; ++i) {
            lines.remove(0);
        }
    }

    protected int handleNumberOfDepots(final List<String> lines) {
        return getSecondStringTokenAsIntAndRemoveThatLineFrom(lines);
    }

    protected int handleNumberOfCapacities(final List<String> lines) {
        return getSecondStringTokenAsIntAndRemoveThatLineFrom(lines);
    }

    protected int handleNumberOfVisits(final List<String> lines) {
        return getSecondStringTokenAsIntAndRemoveThatLineFrom(lines);
    }

    protected int handleNumberOfLocations(final List<String> lines) {
        return getSecondStringTokenAsIntAndRemoveThatLineFrom(lines);
    }

    protected int handleNumberOfVehicles(final List<String> lines) {
        return getSecondStringTokenAsIntAndRemoveThatLineFrom(lines);
    }

    protected int handleCapacities(final List<String> lines) {
        return getSecondStringTokenAsIntAndRemoveThatLineFrom(lines);
    }

    protected int[] handleDemandsSection(final List<String> lines, final int count) {
        removeFirstLinesFrom(lines, 1);
        int [] demands = new int[count];
        for (int i = 0; i < count; ++i) {
            final List<String> splitLines = splitLineBySpace(lines.get(0));
            demands[i] = convertStringToInt(splitLines.get(3));
            removeFirstLinesFrom(lines, 1);
        }

        return demands;
    }

    protected Coordinates[] handleCoordinatesSection(final List<String> lines, final int count) {
        removeFirstLinesFrom(lines, 1);
        Coordinates [] coordinates = new Coordinates[count];
        for (int i = 0; i < count; ++i) {
            final List<String> splitLines = splitLineBySpace(lines.get(0));
            final int X = convertStringToInt(splitLines.get(3));
            final int Y = convertStringToInt(splitLines.get(4));
            coordinates[i] = new Coordinates(X, Y);
            removeFirstLinesFrom(lines, 1);
        }

        return coordinates;
    }

    protected void handleDepotLocation(final List<String> lines) {
        removeFirstLinesFrom(lines, 2);
    }

    protected int[] handleVisitLocationsSection(final List<String> lines, final int count) {
        removeFirstLinesFrom(lines, 1);
        int [] visits = new int[count];
        for (int i = 0; i < count; ++i) {
            final List<String> splitLines = splitLineBySpace(lines.get(0));
            visits[i] = convertStringToInt(splitLines.get(3));
            removeFirstLinesFrom(lines, 1);
        }

        return visits;
    }

    protected int[] handleDurationsSection(final List<String> lines, final int count) {
        // TODO
        return new int[count];
    }

    protected void handleDepotTimeWindowSection(final List<String> lines) {
        // TODO
    }

    protected int handleTimestep(final List<String> lines) {
        // TODO
        return 0;
    }

    protected int[] handleTimeAvailabilitySection(final List<String> lines, final int count) {
        // TODO
        return new int[count];
    }

    @Override
    protected Location  getLocationFromInputFileLine(final String line) {
        final List<String> coordinates = Arrays.asList(line.split(" "));
        return new DeliveryLocation(new Coordinates(Integer.parseInt(coordinates.get(0)), Integer.parseInt(coordinates.get(1))));
    }

    protected int getSecondStringTokenAsIntAndRemoveThatLineFrom(final List<String> lines) {
        List<String> splitLines = splitLineBySpace(lines.get(0));
        final int value = convertStringToInt(splitLines.get(1));
        removeFirstLinesFrom(lines, 1);
        return value;
    }

    protected int convertStringToInt(final String line) {
        return Integer.parseInt(line);
    }

    protected List<String> splitLineBySpace(final String line) {
        return Arrays.asList(line.split(" "));
    }
}
