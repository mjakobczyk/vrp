package com.mjakobczyk.vrp.dynamic.impl.data.impl;

import com.mjakobczyk.vrp.def.data.impl.DefaultVrpFileDataProvider;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
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
        int [] visits = handleVisitLocationsSection(lines, numberOfVisits);
        int [] durations = handleDurationsSection(lines, numberOfVisits);
        int [] timeWindow = handleDepotTimeWindowSection(lines);
        int depotFrom = timeWindow[0];
        int depotTo = timeWindow[1];
        int timeStep = handleTimeStep(lines);
        int [] availabilities = handleTimeAvailabilitySection(lines, numberOfVisits);
        removeFirstLinesFrom(lines, 1); // Skip EOF sign

        final DeliveryLocation depot = new DeliveryLocation(coordinates[0]);
        final List<Location> deliveryLocations = new ArrayList<>();
        for (int i = 1; i < numberOfVisits; ++i) {
            deliveryLocations.add(new DeliveryLocation(coordinates[i], demands[i]));
        }

        return Optional.of(new DynamicVrpInput(List.of(depot), deliveryLocations, capacities, numberOfVehicles));
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
        removeFirstLinesFrom(lines, 1);
        int [] durations = new int[count];
        for (int i = 0; i < count; ++i) {
            final List<String> splitLines = splitLineBySpace(lines.get(0));
            durations[i] = convertStringToInt(splitLines.get(3));
            removeFirstLinesFrom(lines, 1);
        }

        return durations;
    }

    protected int[] handleDepotTimeWindowSection(final List<String> lines) {
        removeFirstLinesFrom(lines, 1);
        int [] depotTimeWindows = new int[2];
        final List<String> splitLines = splitLineBySpace(lines.get(0));
        depotTimeWindows[0] = convertStringToInt(splitLines.get(3));
        depotTimeWindows[1] = convertStringToInt(splitLines.get(4));
        removeFirstLinesFrom(lines, 1);

        return depotTimeWindows;
    }

    protected int handleTimeStep(final List<String> lines) {
        int timeStep;
        final List<String> splitLines = splitLineBySpace(lines.get(0));
        timeStep = convertStringToInt(splitLines.get(2));
        removeFirstLinesFrom(lines, 1);

        return timeStep;
    }

    protected int[] handleTimeAvailabilitySection(final List<String> lines, final int count) {
        removeFirstLinesFrom(lines, 1);
        int [] timeAvailability = new int[count];
        for (int i = 0; i < count; ++i) {
            final List<String> splitLines = splitLineBySpace(lines.get(0));
            timeAvailability[i] = convertStringToInt(splitLines.get(3));
            removeFirstLinesFrom(lines, 1);
        }

        return timeAvailability;
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
