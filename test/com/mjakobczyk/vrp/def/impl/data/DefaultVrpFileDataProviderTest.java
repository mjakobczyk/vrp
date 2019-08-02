package com.mjakobczyk.vrp.def.impl.data;

import com.mjakobczyk.vrp.def.impl.data.impl.DefaultVrpFileDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultVrpFileDataProviderTest {

    private static final String CORRECT_FILE_NAME = "testDefaultVrpDataFile.txt";
    private static final String INCORRECT_FILE_NAME = "incorrectFileName";
    private static final int LOCATIONS_COUNT = 5;

    private DefaultVrpFileDataProvider testSubject;

    @BeforeEach
    public void prepareTestSubject() {
        testSubject = new DefaultVrpFileDataProvider();
    }

    @Test
    public void shouldNotResolveFileFromResourcesForIncorrectFileName() {
        // when
        final Optional<File> optionalFile = testSubject.resolveFileFromResources(INCORRECT_FILE_NAME);

        // then
        assertThat(optionalFile).isEmpty();
    }

    @Test
    public void shouldResolveFileFromResourcesForCorrectFileName() {
        // when
        final Optional<File> optionalFile = testSubject.resolveFileFromResources(CORRECT_FILE_NAME);

        // then
        assertThat(optionalFile).isNotEmpty();
    }

    @Test
    public void shouldNotResolveVrpInputDataFromFileForNotExistingFile() throws IOException {
        // when
        final Optional<VrpInput> optionalVrpInput = testSubject.resolveVrpInputDataFromFile(null);

        // then
        assertThat(optionalVrpInput).isEmpty();
    }

    @Test
    public void shouldResolveVrpInputDataFromFileForExistingFile() throws IOException {
        // given
        final Optional<File> optionalFile = testSubject.resolveFileFromResources(CORRECT_FILE_NAME);
        final File file = optionalFile.orElse(null);

        // when
        final Optional<VrpInput> optionalVrpInput = testSubject.resolveVrpInputDataFromFile(file);

        // then
        assertThat(optionalVrpInput).isNotEmpty();
        assertThat(optionalVrpInput.get().getLocations().size()).isEqualTo(LOCATIONS_COUNT);
    }

}