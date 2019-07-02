package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.def.VrpSolutionProvider;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DynamicVrpSolutionProvider is an implementation of VrpSolutionProvider considering
 * incoming income of data.
 */
public class DynamicVrpSolutionProvider implements VrpSolutionProvider {

    /**
     * DefaultVrpSolutionProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DynamicVrpSolutionProvider.class));

    @Override
    public Optional<VrpOutput> solve(VrpInput vrpInput) {
        LOG.log(Level.INFO, "DynamicVrpSolutionProvider#solve does not contain valuable implementation yet.");

        return Optional.empty();
    }
}
