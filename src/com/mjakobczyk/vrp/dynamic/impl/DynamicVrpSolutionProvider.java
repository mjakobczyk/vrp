package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.def.DefaultVrpSolutionProvider;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.DefaultDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * DynamicVrpSolutionProvider is an implementation of VrpSolutionProvider considering
 * incoming income of data.
 */
public class DynamicVrpSolutionProvider extends DefaultVrpSolutionProvider {

    /**
     * DefaultVrpSolutionProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DynamicVrpSolutionProvider.class));

    /**
     * Constructor of DynamicVrpSolutionProvider.
     */
    public DynamicVrpSolutionProvider() {
        this.setVrpSolutionProviderStrategy(new DefaultDynamicVrpSolutionProviderStrategy());
    }

    /*
     * Constructor of DynamicVrpSolutionProvider.
     *
     * @param vrpSolutionProviderStrategy to inject
     */
    public DynamicVrpSolutionProvider(final VrpSolutionProviderStrategy vrpSolutionProviderStrategy) {
        super(vrpSolutionProviderStrategy);
    }

    @Override
    public Optional<VrpOutput> solve(VrpInput vrpInput) {

        return getVrpSolutionProviderStrategy().findOptimalRouteFor(vrpInput);
    }
}
