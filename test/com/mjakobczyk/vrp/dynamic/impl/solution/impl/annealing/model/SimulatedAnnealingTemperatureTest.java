package com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing.model;


import com.mjakobczyk.vrp.def.solution.impl.annealing.model.SimulatedAnnealingTemperature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimulatedAnnealingTemperatureTest {

    private static final int LOW_TEMPERATURE = 1;
    private static final int MEDIUM_TEMPERATURE = 2;
    private static final int HIGH_TEMPERATURE = 3;
    private static final int STEP_TEMPERATURE = 1;

    private SimulatedAnnealingTemperature testSubject;

    @BeforeEach
    public void prepareTestSubject() {
        this.testSubject = new SimulatedAnnealingTemperature.Builder().build();
    }

    @Test
    public void shouldBeValidWhenCurrentTemperatureIsHigherThantMinimumTemperature() {
        // given
        testSubject = new SimulatedAnnealingTemperature
                .Builder().current(HIGH_TEMPERATURE).minimum(MEDIUM_TEMPERATURE).step(STEP_TEMPERATURE).build();

        // when
        boolean isValid = testSubject.isValid();

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotBeValidWhenCurrentTemperatureIsLowerThanMinimumTemperature() {
        // given
        testSubject = new SimulatedAnnealingTemperature
                .Builder().current(LOW_TEMPERATURE).minimum(MEDIUM_TEMPERATURE).step(STEP_TEMPERATURE).build();

        // when
        boolean isValid = testSubject.isValid();

        // then
        assertThat(isValid).isFalse();
    }

}