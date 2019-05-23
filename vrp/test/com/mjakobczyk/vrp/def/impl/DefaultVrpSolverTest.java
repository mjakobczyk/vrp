package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.def.VrpDataProvider;
import com.mjakobczyk.vrp.def.VrpSolutionProvider;
import com.mjakobczyk.vrp.model.VrpOutput;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultVrpSolverTest {

    private DefaultVrpSolver testSubject;

    private VrpDataProvider vrpDataProvider = null;
    private VrpSolutionProvider vrpSolutionProvider = null;

    @Test
    public void shouldNotPerformCalculationsWithoutDataAndSolutionProviders() {
        // given
        testSubject = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);

        // when
        final Optional<VrpOutput> result = testSubject.solve();

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldNotPerformCalculationsWithDataProviderAndWithoutSolutionProvider() {
        // given
        vrpDataProvider = new DefaultVrpDataProvider();
        testSubject = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);

        // when
        final Optional<VrpOutput> result = testSubject.solve();

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldNotPerformCalculationsWithoutDataProviderAndWithSolutionProvider() {
        // given
        vrpSolutionProvider = new DefaultVrpSolutionProvider();
        testSubject = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);

        // when
        final Optional<VrpOutput> result = testSubject.solve();

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldNotPerformCalculationsWhenNoDataIsProvided() {
        // given
        vrpDataProvider = new DefaultVrpDataProvider();
        vrpSolutionProvider = new DefaultVrpSolutionProvider();
        testSubject = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);

        // when
        final Optional<VrpOutput> result = testSubject.solve();

        // then
        assertTrue(result.isEmpty());
    }
}