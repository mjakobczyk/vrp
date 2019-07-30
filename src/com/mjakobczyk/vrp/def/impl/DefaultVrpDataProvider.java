package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.VrpDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * DefaultVrpDataProvider is a default implementation of data provider
 * for solving VRP.
 */
public class DefaultVrpDataProvider implements VrpDataProvider {

    /**
     * DefaultVrpDataProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpDataProvider.class));

    @Override
    public Optional<VrpInput> getVrpInput() {
        // TODO

        return Optional.empty();
    }

}
