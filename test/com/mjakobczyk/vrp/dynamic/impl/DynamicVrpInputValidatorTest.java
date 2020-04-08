package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.def.model.DefaultVrpInput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.model.Coordinates;
import com.mjakobczyk.vrp.model.DeliveryLocation;
import com.mjakobczyk.vrp.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class DynamicVrpInputValidatorTest {

    private static final int LOCATION_FIRST_COORDINATE = 1;
    private static final int LOCATION_SECOND_COORDINATE = 1;


    @InjectMocks
    private DynamicVrpInputValidator testSubject;

    @Mock
    private DynamicVrpInput dynamicVrpInput;

    @Mock
    private DefaultVrpInput defaultVrpInput;

    @BeforeEach
    public void setUp() {
        this.testSubject = new DynamicVrpInputValidator();
        initMocks(this);
    }

    @Test
    public void shouldNotBeValidWhenInputDoesNotExist() {
        // when
        final boolean isValid = testSubject.isValid(null);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotBeValidWhenInputIsNotDynamicInput() {
        // when
        final boolean isValid = testSubject.isValid(defaultVrpInput);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotBeValidWhenInputLocationsDoNotExist() {
        // given
        given(dynamicVrpInput.getLocations()).willReturn(null);

        // when
        final boolean isValid = testSubject.isValid(dynamicVrpInput);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotBeValidWhenInputDoesNotContainLocations() {
        // given
        given(dynamicVrpInput.getLocations()).willReturn(Collections.emptyList());

        // when
        final boolean isValid = testSubject.isValid(dynamicVrpInput);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotBeValidWhenAdditionalLocationsDoNotExist() {
        // given
        final List<Location> locations = Collections.singletonList(
                prepareDeliveryLocation(LOCATION_FIRST_COORDINATE, LOCATION_FIRST_COORDINATE));
        given(dynamicVrpInput.getLocations()).willReturn(locations);
        given(dynamicVrpInput.getAdditionalLocations()).willReturn(null);

        // when
        final boolean isValid = testSubject.isValid(dynamicVrpInput);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotBeValidWhenInputDoesNotContainAdditionalLocations() {
        // given
        final List<Location> locations = Collections.singletonList(
                prepareDeliveryLocation(LOCATION_FIRST_COORDINATE, LOCATION_FIRST_COORDINATE));
        given(dynamicVrpInput.getLocations()).willReturn(locations);
        given(dynamicVrpInput.getAdditionalLocations()).willReturn(Collections.emptyList());

        // when
        final boolean isValid = testSubject.isValid(dynamicVrpInput);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldBeValidForAllInputCriteriaMet() {
        // given
        final List<Location> locations = Collections.singletonList(
                prepareDeliveryLocation(LOCATION_FIRST_COORDINATE, LOCATION_FIRST_COORDINATE));
        given(dynamicVrpInput.getLocations()).willReturn(locations);
        given(dynamicVrpInput.getAdditionalLocations()).willReturn(locations);

        // when
        final boolean isValid = testSubject.isValid(dynamicVrpInput);

        // then
        assertThat(isValid).isTrue();
    }

    private DeliveryLocation prepareDeliveryLocation(final int firstCoordinate, final int secondCoordinate) {
        return new DeliveryLocation(new Coordinates(firstCoordinate, secondCoordinate));
    }

}
