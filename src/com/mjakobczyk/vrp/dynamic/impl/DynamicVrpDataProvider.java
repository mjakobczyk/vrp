package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.VrpDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**\
 * DynamicVrpDataProvider is an implementation of data provider
 * for solving Dynamic VRP.
 */
public class DynamicVrpDataProvider implements VrpDataProvider {

    /**
     * DynamicVrpDataProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DynamicVrpDataProvider.class));

    @Override
    public Optional<VrpInput> getVrpInput() {
        LOG.log(Level.INFO, "DynamicVrpDataProvider#getVrpInput does not contain valuable implementation yet.");

        return Optional.empty();
    }
}
