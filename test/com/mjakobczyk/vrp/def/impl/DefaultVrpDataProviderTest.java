package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.def.impl.data.VrpFileDataProvider;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultVrpDataProviderTest {

    private static final String FILE_NAME = "fileName";

    @Spy
    @InjectMocks
    private DefaultVrpDataProvider testSubject;

    private VrpFileDataProvider vrpFileDataProvider;
    private File file;

    @BeforeEach
    public void setUp() {
        vrpFileDataProvider = mock(VrpFileDataProvider.class);
        file = mock(File.class);
    }

    @Test
    public void shouldResolveFileFromResourcesForEmptyFileName() {
        // given
//        prepareTestSubjectWithEmptyFileName();
//        when(testSubject.getVrpFileDataProvider()).thenReturn(vrpFileDataProvider);
        vrpFileDataProvider = mock(VrpFileDataProvider.class);
        when(vrpFileDataProvider.resolveFileFromResources(any(String.class))).thenReturn(Optional.of(file));

        // when
        testSubject.getVrpInput();

        // then
        verify(vrpFileDataProvider).resolveFileFromResources(any(String.class));
    }

    private void prepareTestSubjectWithEmptyFileName() {
        testSubject = new DefaultVrpDataProvider(StringUtils.EMPTY);
        testSubject.setVrpFileDataProvider(vrpFileDataProvider);
    }

    private void prepareTestSubjectInitializedWithFileName() {
        testSubject = new DefaultVrpDataProvider(FILE_NAME);
        testSubject.setVrpFileDataProvider(vrpFileDataProvider);
    }
}