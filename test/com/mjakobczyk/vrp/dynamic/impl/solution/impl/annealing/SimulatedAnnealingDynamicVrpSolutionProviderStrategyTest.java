package com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing;


import com.mjakobczyk.vrp.model.Coordinates;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.DeliveryLocation;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SimulatedAnnealingDynamicVrpSolutionProviderStrategyTest {

    private static final int FIRST_LOCATION_FIRST_COORDINATE = 1;
    private static final int FIRST_LOCATION_SECOND_COORDINATE = 1;
    private static final int SECOND_LOCATION_FIRST_COORDINATE = 1;
    private static final int SECOND_LOCATION_SECOND_COORDINATE = 3;
    private static final int THIRD_LOCATION_FIRST_COORDINATE = 3;
    private static final int THIRD_LOCATION_SECOND_COORDINATE = 3;
    private static final int FOURTH_LOCATION_FIRST_COORDINATE = 5;
    private static final int FOURTH_LOCATION_SECOND_COORDINATE = 3;
    private static final int FIFTH_LOCATION_FIRST_COORDINATE = 5;
    private static final int FIFTH_LOCATION_SECOND_COORDINATE = 5;
    private static final int EXPECTED_TOTAL_DISTANCE = 4;

    private SimulatedAnnealingDynamicVrpSolutionProviderStrategy testSubject;

    @BeforeEach
    public void prepareTestSubject() {
        testSubject = new SimulatedAnnealingDynamicVrpSolutionProviderStrategy();
    }

    @Test
    public void shouldCorrectlyCountTotalDistanceForThreeCities() {
        // given
        final List<Location> locations = new ArrayList<>();
        locations.add(prepareDeliveryLocation(FIRST_LOCATION_FIRST_COORDINATE, FIRST_LOCATION_SECOND_COORDINATE));
        locations.add(prepareDeliveryLocation(SECOND_LOCATION_FIRST_COORDINATE, SECOND_LOCATION_SECOND_COORDINATE));
        locations.add(prepareDeliveryLocation(THIRD_LOCATION_FIRST_COORDINATE, THIRD_LOCATION_SECOND_COORDINATE));

        // when
        final double totalDistance = testSubject.countDistanceFor(locations);

        // then
        assertThat(totalDistance).isEqualTo(EXPECTED_TOTAL_DISTANCE);
    }

    @Test
    public void shouldFindOptimalRoutWhenListOfLocationsIsPassed() {
        // given
        final List<Location> locations = new ArrayList<>();
        locations.add(prepareDeliveryLocation(FIRST_LOCATION_FIRST_COORDINATE, FIRST_LOCATION_SECOND_COORDINATE));
        locations.add(prepareDeliveryLocation(FIFTH_LOCATION_FIRST_COORDINATE, FIFTH_LOCATION_SECOND_COORDINATE));
        locations.add(prepareDeliveryLocation(SECOND_LOCATION_FIRST_COORDINATE, SECOND_LOCATION_SECOND_COORDINATE));

        final List<Location> additionalLocations = new ArrayList<>();
        additionalLocations.add(prepareDeliveryLocation(FOURTH_LOCATION_FIRST_COORDINATE, FOURTH_LOCATION_SECOND_COORDINATE));
        additionalLocations.add(prepareDeliveryLocation(THIRD_LOCATION_FIRST_COORDINATE, THIRD_LOCATION_SECOND_COORDINATE));

        final VrpInput vrpInput = new DynamicVrpInput(locations, additionalLocations);

        // when
        final Optional<VrpOutput> optionalVrpOutput = testSubject.findOptimalRouteFor(vrpInput);

        // then
        assertThat(optionalVrpOutput.isPresent()).isTrue();
    }

    private DeliveryLocation prepareDeliveryLocation(final int firstCoordinate, final int secondCoordinate) {
        return new DeliveryLocation(new Coordinates(firstCoordinate, secondCoordinate));
    }
}