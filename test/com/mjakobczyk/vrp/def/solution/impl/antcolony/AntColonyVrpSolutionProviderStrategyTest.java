package com.mjakobczyk.vrp.def.solution.impl.antcolony;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.MockitoAnnotations.initMocks;

public class AntColonyVrpSolutionProviderStrategyTest {

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
    private AntColonyVrpSolutionProviderStrategy testSubject;

    @BeforeEach
    public void setUp() {
        this.testSubject = new AntColonyVrpSolutionProviderStrategy();
        initMocks(this);
    }

    @Test
    public void shouldNotFindOptimalRouteForInvalidInput() {
    }

}