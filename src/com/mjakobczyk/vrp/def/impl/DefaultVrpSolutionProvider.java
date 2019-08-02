package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.VrpSolutionProvider;
import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.impl.solution.impl.DefaultVrpSolutionProviderStrategy;
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

    /**
     * VrpSolutionProviderStrategy for finding optimal route.
     */
    private VrpSolutionProviderStrategy vrpSolutionProviderStrategy;

    @Override
    public Optional<VrpOutput> solve(final VrpInput vrpInput) {
        return getVrpSolutionProviderStrategy().findOptimalRouteFor(vrpInput);
    }

    /**
     * Constructor of DefaultVrpSolutionProvider.
     */
    public DefaultVrpSolutionProvider() {
        this.vrpSolutionProviderStrategy = new DefaultVrpSolutionProviderStrategy();
    }

    /*
     * Constructor of DefaultVrpSolutionProvider.
     *
     * @param vrpSolutionProviderStrategy to inject
     */
    public DefaultVrpSolutionProvider(final VrpSolutionProviderStrategy vrpSolutionProviderStrategy) {
        this.vrpSolutionProviderStrategy = vrpSolutionProviderStrategy;
    }

    /**
     * Getter of VrpSolutionProviderStrategy.
     *
     * @return VrpSolutionProviderStrategy instance
     */
    protected VrpSolutionProviderStrategy getVrpSolutionProviderStrategy() {
        return vrpSolutionProviderStrategy;
    }

    /**
     * Setter of VrpSolutionProviderStrategy.
     *
     * @param vrpSolutionProviderStrategy to inject
     */
    public void setVrpSolutionProviderStrategy(final VrpSolutionProviderStrategy vrpSolutionProviderStrategy) {
        this.vrpSolutionProviderStrategy = vrpSolutionProviderStrategy;
    }
}
