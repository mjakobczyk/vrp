package com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing.model;

import com.mjakobczyk.vrp.def.impl.solution.impl.annealing.model.SimulatedAnnealingTravelOrder;
import com.mjakobczyk.vrp.model.Coordinates;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.DeliveryLocation;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimulatedAnnealingTravelOrderTest {

    private static final int FIRST_LOCATION_FIRST_COORDINATE = 1;
    private static final int FIRST_LOCATION_SECOND_COORDINATE = 1;
    private static final int SECOND_LOCATION_FIRST_COORDINATE = 3;
    private static final int SECOND_LOCATION_SECOND_COORDINATE = 3;
    private static final int THIRD_LOCATION_FIRST_COORDINATE = 5;
    private static final int THIRD_LOCATION_SECOND_COORDINATE = 5;

    private SimulatedAnnealingTravelOrder testSubject;

    @BeforeEach
    public void prepareTestSubject() {
        this.testSubject = new SimulatedAnnealingTravelOrder(Lists.emptyList());
    }

    @Test
    public void shouldInitializeEmptyTravelListWhenEmptyLocationsListIsPassed() {
        // when
        testSubject.updateTravelsOrder(Lists.emptyList());

        // then
        assertThat(testSubject.getTravels()).isEmpty();
    }

    @Test
    public void shouldInitializeEmptyTravelListWhenOneLocationIsPassed() {
        // given
        final List<Location> locations = new ArrayList<>();
        locations.add(prepareDeliveryLocation(FIRST_LOCATION_FIRST_COORDINATE, FIRST_LOCATION_SECOND_COORDINATE));

        // when
        testSubject.updateTravelsOrder(locations);

        // then
        assertThat(testSubject.getTravels()).isEmpty();
    }

    @Test
    public void shouldInitializeTravelListWhenTwoLocationsArePassed() {
        // given
        final List<Location> locations = new ArrayList<>();
        locations.add(prepareDeliveryLocation(FIRST_LOCATION_FIRST_COORDINATE, FIRST_LOCATION_SECOND_COORDINATE));
        locations.add(prepareDeliveryLocation(SECOND_LOCATION_FIRST_COORDINATE, SECOND_LOCATION_SECOND_COORDINATE));

        // when
        testSubject.updateTravelsOrder(locations);

        // then
        assertThat(testSubject.getTravels().size()).isEqualTo(1);
        assertThat(testSubject.getTravels().get(0).getFirstLocation()).isEqualTo(0);
        assertThat(testSubject.getTravels().get(0).getSecondLocation()).isEqualTo(1);
    }

    @Test
    public void shouldInitializeTravelListWhenMoreLocationsArePassed() {
        // given
        final List<Location> locations = new ArrayList<>();
        locations.add(prepareDeliveryLocation(FIRST_LOCATION_FIRST_COORDINATE, FIRST_LOCATION_SECOND_COORDINATE));
        locations.add(prepareDeliveryLocation(SECOND_LOCATION_FIRST_COORDINATE, SECOND_LOCATION_SECOND_COORDINATE));
        locations.add(prepareDeliveryLocation(THIRD_LOCATION_FIRST_COORDINATE, THIRD_LOCATION_SECOND_COORDINATE));

        // when
        testSubject.updateTravelsOrder(locations);

        // then
        assertThat(testSubject.getTravels().size()).isEqualTo(3);
        assertThat(testSubject.getTravels().get(0).getFirstLocation()).isEqualTo(0);
        assertThat(testSubject.getTravels().get(0).getSecondLocation()).isEqualTo(1);
        assertThat(testSubject.getTravels().get(1).getFirstLocation()).isEqualTo(0);
        assertThat(testSubject.getTravels().get(1).getSecondLocation()).isEqualTo(2);
        assertThat(testSubject.getTravels().get(2).getFirstLocation()).isEqualTo(1);
        assertThat(testSubject.getTravels().get(2).getSecondLocation()).isEqualTo(2);
    }

    private DeliveryLocation prepareDeliveryLocation(final int firstCoordinate, final int secondCoordinate) {
        return new DeliveryLocation(new Coordinates(firstCoordinate, secondCoordinate));
    }

}