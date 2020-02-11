package com.mjakobczyk.vrp.dynamic.impl.data.impl;

import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.model.VrpInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicVrpFileDataProviderTest {

    private static final String FILE_NAME = "testDynamicVrpDataFile.txt";
    private static final int LOCATIONS_COUNT = 5;
    private static final int ADDITIONAL_LOCATIONS_COUNT = 4;

    private DynamicVrpFileDataProvider testSubject;

    @BeforeEach
    public void prepareTestSubject() {
        testSubject = new DynamicVrpFileDataProvider();
    }

    @Test
    public void shouldResolveVrpInputDataFromFileForExistingFile() throws IOException {
        // given
        final Optional<File> optionalFile = testSubject.resolveFileFromResources(FILE_NAME);
        final File file = optionalFile.orElse(null);

        // when
        final Optional<VrpInput> optionalVrpInput = testSubject.resolveVrpInputDataFromFile(file);

        // then
        assertThat(optionalVrpInput).isNotEmpty();
        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) optionalVrpInput.get();
        assertThat(dynamicVrpInput.getLocations().size()).isEqualTo(LOCATIONS_COUNT);
        assertThat(dynamicVrpInput.getAdditionalLocations().size()).isEqualTo(ADDITIONAL_LOCATIONS_COUNT);
    }

}