package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.def.VrpDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicVrpDataProvider implements VrpDataProvider {

    /**
     * DefaultVrpDataProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DynamicVrpDataProvider.class));

    @Override
    public Optional<VrpInput> getVrpInput() {
        LOG.log(Level.INFO, "DynamicVrpDataProvider#getVrpInput does not contain valuable implementation yet.");

        return Optional.empty();
    }
}
