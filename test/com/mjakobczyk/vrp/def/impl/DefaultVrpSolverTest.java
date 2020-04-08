package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.service.VrpDataProvider;
import com.mjakobczyk.vrp.service.VrpSolutionProvider;
import com.mjakobczyk.vrp.model.VrpOutput;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void shouldNotPerformCalculationsWithDataProviderAndWithoutSolutionProvider() {
        // given
        vrpDataProvider = new DefaultVrpDataProvider();
        testSubject = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);

        // when
        final Optional<VrpOutput> result = testSubject.solve();

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void shouldNotPerformCalculationsWithoutDataProviderAndWithSolutionProvider() {
        // given
        vrpSolutionProvider = new DefaultVrpSolutionProvider();
        testSubject = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);

        // when
        final Optional<VrpOutput> result = testSubject.solve();

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void shouldNotPerformCalculationsWhenDefaultProvidersArePresent() {
        // given
        vrpDataProvider = new DefaultVrpDataProvider();
        vrpSolutionProvider = new DefaultVrpSolutionProvider();
        testSubject = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);

        // when
        final Optional<VrpOutput> result = testSubject.solve();

        // then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void shouldPerformCalculationsWhenDefaultProvidersAndCorrectInputFileArePresent() {
        // given
        vrpDataProvider = new DefaultVrpDataProvider("testDefaultVrpDataFile.txt");
        vrpSolutionProvider = new DefaultVrpSolutionProvider();
        testSubject = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);

        // when
        final Optional<VrpOutput> result = testSubject.solve();

        // then
        assertThat(result.isPresent()).isTrue();
    }

}
