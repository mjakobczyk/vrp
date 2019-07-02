package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.def.VrpSolutionProvider;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DefaultVrpSolutionProvider is a default implementation of VrpSolutionProvider.
 */
public class DefaultVrpSolutionProvider implements VrpSolutionProvider {

    /**
     * DefaultVrpSolutionProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpSolutionProvider.class));

    @Override
    public Optional<VrpOutput> solve(final VrpInput vrpInput) {
        LOG.log(Level.INFO, "DefaultVrpSolutionProvider#solve does not contain valuable implementation yet.");

        return Optional.empty();
    }
}
