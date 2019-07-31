package com.mjakobczyk.vrp.def.impl.data;

import com.mjakobczyk.vrp.def.impl.data.impl.DefaultVrpFileDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class DefaultVrpFileDataProviderTest {

    private static final String CORRECT_FILE_NAME = "testDefaultVrpDataFile.txt";
    private static final String INCORRECT_FILE_NAME = "incorrectFileName";

    private DefaultVrpFileDataProvider testSubject;

    @BeforeEach
    public void prepareTestSubject() {
        testSubject = new DefaultVrpFileDataProvider();
    }

    @Test
    public void shoulNotdResolveFileFromResourcesForIncorrectFileName() {
        // given

        // when
        final Optional<File> optionalFile = testSubject.resolveFileFromResources(INCORRECT_FILE_NAME);

        // then
        assertThat(optionalFile).isEmpty();
    }

    @Test
    public void shouldResolveFileFromResourcesForCorrectFileName() {
        // given

        // when
        final Optional<File> optionalFile = testSubject.resolveFileFromResources(CORRECT_FILE_NAME);

        // then
        assertThat(optionalFile).isNotEmpty();
    }

    @Test
    public void shouldNotResolveVrpInputDataFromFileForNotExistingFile() {
        // TODO
        // given

        // when

        // then
    }

}