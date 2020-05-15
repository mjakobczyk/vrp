package com.mjakobczyk.vrp.dynamic.impl.data.impl;

import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.model.VrpInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MendeleyDynamicVrpFileDataProviderTest {

    private static final String FILE_NAME = "testTai100dD.vrp";

    private MendeleyDynamicVrpFileDataProvider testSubject;

    @BeforeEach
    public void prepareTestSubject() {
        testSubject = new MendeleyDynamicVrpFileDataProvider();
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
        assertThat(dynamicVrpInput).isNotNull();
    }

}