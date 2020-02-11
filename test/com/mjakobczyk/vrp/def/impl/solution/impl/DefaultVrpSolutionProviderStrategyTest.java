package com.mjakobczyk.vrp.def.impl.solution.impl;

import com.mjakobczyk.vrp.model.Coordinates;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.DeliveryLocation;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;
import com.mjakobczyk.vrp.def.model.DefaultVrpInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultVrpSolutionProviderStrategyTest {

    private static final int FIRST_LOCATION_FIRST_COORDINATE = 2;
    private static final int FIRST_LOCATION_SECOND_COORDINATE = 3;
    private static final int SECOND_LOCATION_FIRST_COORDINATE = 4;
    private static final int SECOND_LOCATION_SECOND_COORDINATE = 5;
    private static final int LOCATIONS_COUNT = 2;

    private DefaultVrpSolutionProviderStrategy testSubject;

    @BeforeEach
    public void prepareTestSubject() {
        testSubject = new DefaultVrpSolutionProviderStrategy();
    }

    @Test
    public void shouldNotFindOptimalRouterForVrpInputWhenVrpInputDoesNotExist() {
        // when
        final Optional<VrpOutput> optionalVrpOutput = testSubject.findOptimalRouteFor(null);

        // then
        assertThat(optionalVrpOutput).isEmpty();
    }

    @Test
    public void shouldFindOptimalRouterForVrpInputWhenVrpInputExists() {
        // given
        final VrpInput vrpInput = createSampleVrpInput();

        // when
        final Optional<VrpOutput> optionalVrpOutput = testSubject.findOptimalRouteFor(vrpInput);

        // then
        assertThat(optionalVrpOutput).isNotEmpty();
        assertThat(optionalVrpOutput.get().getLocations().size()).isEqualTo(LOCATIONS_COUNT);
        assertThat(optionalVrpOutput.get().getLocations().get(0).getCoordinates().getCoordinateX()).isEqualTo(FIRST_LOCATION_FIRST_COORDINATE);
        assertThat(optionalVrpOutput.get().getLocations().get(0).getCoordinates().getCoordinateY()).isEqualTo(FIRST_LOCATION_SECOND_COORDINATE);
        assertThat(optionalVrpOutput.get().getLocations().get(1).getCoordinates().getCoordinateX()).isEqualTo(SECOND_LOCATION_FIRST_COORDINATE);
        assertThat(optionalVrpOutput.get().getLocations().get(1).getCoordinates().getCoordinateY()).isEqualTo(SECOND_LOCATION_SECOND_COORDINATE);
    }

    private VrpInput createSampleVrpInput() {
        final List<Location> locations = new ArrayList<>(LOCATIONS_COUNT);
        locations.add(new DeliveryLocation(new Coordinates(FIRST_LOCATION_FIRST_COORDINATE, FIRST_LOCATION_SECOND_COORDINATE)));
        locations.add(new DeliveryLocation(new Coordinates(SECOND_LOCATION_FIRST_COORDINATE, SECOND_LOCATION_SECOND_COORDINATE)));
        return new DefaultVrpInput(locations);
    }
}