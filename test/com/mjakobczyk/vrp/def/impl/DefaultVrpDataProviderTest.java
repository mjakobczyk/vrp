package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.def.DefaultVrpDataProvider;
import com.mjakobczyk.vrp.def.data.VrpFileDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DefaultVrpDataProviderTest {

    private static final String FILE_NAME = "fileName";
    private static final String FILE_ABSOLUTE_PATH = "fileAbsolutePath";

    @Spy
    private DefaultVrpDataProvider testSubject;

    @Mock
    private VrpFileDataProvider vrpFileDataProvider;

    @Mock
    private File file;

    @Mock
    private VrpInput vrpInput;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldResolveFileFromResourcesWhenFileNameIsEmpty() {
        // given
        prepareTestSubjectWithEmptyFileName();
        when(vrpFileDataProvider.resolveFileFromPath(any(String.class))).thenReturn(Optional.empty());

        // when
        Optional<VrpInput> optionalVrpInput = testSubject.getVrpInput();

        // then
        assertThat(optionalVrpInput).isEmpty();
        verify(vrpFileDataProvider).resolveFileFromResources(any(String.class));
    }

    @Test
    public void shouldResolveFileFromPathWhenFileNameIsNotEmpty()
    {
        // given
        prepareTestSubjectInitializedWithFileName();
        when(vrpFileDataProvider.resolveFileFromPath(any(String.class))).thenReturn(Optional.empty());

        // when
        Optional<VrpInput> optionalVrpInput = testSubject.getVrpInput();

        // then
        assertThat(optionalVrpInput).isEmpty();
        verify(vrpFileDataProvider).resolveFileFromPath(any(String.class));
    }

    @Test
    public void shouldNotReturnVrpInputDataWhenIOExceptionIsThrownWhenResolvingDataFromFile() throws IOException {
        // given
        prepareTestSubjectInitializedWithFileName();
        when(vrpFileDataProvider.resolveFileFromPath(any(String.class))).thenReturn(Optional.of(file));
        when(file.getAbsolutePath()).thenReturn(FILE_ABSOLUTE_PATH);
        when(vrpFileDataProvider.resolveVrpInputDataFromFile(any(File.class))).thenThrow(IOException.class);

        // when
        Optional<VrpInput> optionalVrpInput = testSubject.getVrpInput();

        // then
        assertThat(optionalVrpInput).isEmpty();
        verify(vrpFileDataProvider).resolveVrpInputDataFromFile(any(File.class));
    }

    @Test
    public void shouldReturnEmptyVrpInputWhenFileDidNotProvideAnyData() throws IOException {
        // given
        prepareTestSubjectInitializedWithFileName();
        when(vrpFileDataProvider.resolveFileFromPath(any(String.class))).thenReturn(Optional.of(file));
        when(file.getAbsolutePath()).thenReturn(FILE_ABSOLUTE_PATH);
        when(vrpFileDataProvider.resolveVrpInputDataFromFile(any(File.class))).thenReturn(Optional.empty());

        // when
        Optional<VrpInput> optionalVrpInput = testSubject.getVrpInput();

        // then
        assertThat(optionalVrpInput).isEmpty();
    }

    @Test
    public void shouldReturnVrpInputWhenFileProvidedData() throws IOException {
        // given
        prepareTestSubjectInitializedWithFileName();
        when(vrpFileDataProvider.resolveFileFromPath(any(String.class))).thenReturn(Optional.of(file));
        when(vrpFileDataProvider.resolveVrpInputDataFromFile(any(File.class))).thenReturn(Optional.of(vrpInput));

        // when
        Optional<VrpInput> optionalVrpInput = testSubject.getVrpInput();

        // then
        assertThat(optionalVrpInput).isNotEmpty();
        assertThat(optionalVrpInput.get()).isNotNull();
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