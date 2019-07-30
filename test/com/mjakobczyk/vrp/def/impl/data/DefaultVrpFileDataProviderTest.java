package com.mjakobczyk.vrp.def.impl.data;

import com.mjakobczyk.vrp.model.VrpInput;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class DefaultVrpFileDataProviderTest {

    private static final String FILE_NAME = "fileName";

    private final DefaultVrpFileDataProvider testSubject = new DefaultVrpFileDataProvider();

    @Test
    private void shouldResolveFileFromResourcesForEmptyFileName() {
        // given

        // when

        // then
    }

}