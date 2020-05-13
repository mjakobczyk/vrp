package com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing;


import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.def.solution.impl.annealing.SimulatedAnnealingDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.Coordinates;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.DeliveryLocation;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;
import com.mjakobczyk.vrp.service.VrpInputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

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


    @InjectMocks
    private SimulatedAnnealingDynamicVrpSolutionProviderStrategy testSubject;

    @Mock
    private VrpInputValidator vrpInputValidator;

    @Mock
    private DynamicVrpInput dynamicVrpInput;

    @BeforeEach
    public void prepareTestSubject() {
        testSubject = new SimulatedAnnealingDynamicVrpSolutionProviderStrategy();
        initMocks(this);
    }

    @Test
    public void shouldNotFindOptimalRouteForInvalidInput() {
        // given
        given(vrpInputValidator.isValid(any(VrpInput.class))).willReturn(false);

        // when
        final Optional<VrpOutput> optionalVrpOutput = testSubject.findOptimalRouteFor(dynamicVrpInput);

        // then
        assertThat(optionalVrpOutput.isPresent()).isFalse();
    }

    @Test
    public void shouldFindOptimalRouteWhenAdditionalLocationsAreIncluded() {
        // given
        given(vrpInputValidator.isValid(any(VrpInput.class))).willReturn(true);
        final List<Location> locations = prepareLocationsList();
        final List<Location> additionalLocations = prepareAdditionalLocationsList();
        final VrpInput vrpInput = new DynamicVrpInput(locations, additionalLocations);

        // when
        final Optional<VrpOutput> optionalVrpOutput = testSubject.findOptimalRouteFor(vrpInput);

        // then
        assertThat(optionalVrpOutput.isPresent()).isTrue();
    }

    @Test
    public void shouldIncludeDepotOnStartAndEndOfRouteWhenAdditionalLocationsAreIncluded() {
        // given
        given(vrpInputValidator.isValid(any(VrpInput.class))).willReturn(true);
        final List<Location> locations = prepareLocationsList();
        final List<Location> additionalLocations = prepareAdditionalLocationsList();
        final VrpInput vrpInput = new DynamicVrpInput(locations, additionalLocations);

        // when
        final Optional<VrpOutput> optionalVrpOutput = testSubject.findOptimalRouteFor(vrpInput);
        final DefaultVrpOutput defaultVrpOutput = (DefaultVrpOutput) optionalVrpOutput.orElse(null);
        final Location depot = defaultVrpOutput.getLocations().get(0);

        // then
        assertThat(defaultVrpOutput.getLocations().get(0)).isEqualTo(depot);
        assertThat(defaultVrpOutput.getLocations().get(defaultVrpOutput.getLocations().size() - 1)).isEqualTo(depot);
    }

    private List<Location> prepareLocationsList() {
        final List<Location> locations = new ArrayList<>();
        locations.add(prepareDeliveryLocation(FIRST_LOCATION_FIRST_COORDINATE, FIRST_LOCATION_SECOND_COORDINATE));
        locations.add(prepareDeliveryLocation(SECOND_LOCATION_FIRST_COORDINATE, SECOND_LOCATION_SECOND_COORDINATE));
        locations.add(prepareDeliveryLocation(THIRD_LOCATION_FIRST_COORDINATE, THIRD_LOCATION_SECOND_COORDINATE));

        return locations;
    }

    private List<Location> prepareAdditionalLocationsList() {
        final List<Location> additionalLocations = new ArrayList<>();
        additionalLocations.add(prepareDeliveryLocation(FOURTH_LOCATION_FIRST_COORDINATE, FOURTH_LOCATION_SECOND_COORDINATE));
        additionalLocations.add(prepareDeliveryLocation(FIFTH_LOCATION_FIRST_COORDINATE, FIFTH_LOCATION_SECOND_COORDINATE));

        return additionalLocations;
    }

    private DeliveryLocation prepareDeliveryLocation(final int firstCoordinate, final int secondCoordinate) {
        return new DeliveryLocation(new Coordinates(firstCoordinate, secondCoordinate));
    }
}