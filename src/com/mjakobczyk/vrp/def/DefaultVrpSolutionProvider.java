package com.mjakobczyk.vrp.def;

import com.mjakobczyk.vrp.service.VrpInputValidator;
import com.mjakobczyk.vrp.service.VrpSolutionProvider;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.DefaultVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;
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

    /**
     * Validator for input data.
     */
    private VrpInputValidator vrpInputValidator;

    @Override
    public Optional<VrpOutput> solve(final VrpInput vrpInput) {
        return getVrpSolutionProviderStrategy().findOptimalRouteFor(vrpInput);
    }

    /**
     * Constructor of DefaultVrpSolutionProvider.
     */
    public DefaultVrpSolutionProvider() {
        this.vrpSolutionProviderStrategy = new DefaultVrpSolutionProviderStrategy();
        this.vrpInputValidator = null;
    }

    /*
     * Constructor of DefaultVrpSolutionProvider.
     *
     * @param vrpSolutionProviderStrategy to inject
     */
    public DefaultVrpSolutionProvider(final VrpSolutionProviderStrategy vrpSolutionProviderStrategy) {
        this.vrpSolutionProviderStrategy = vrpSolutionProviderStrategy;
        this.vrpInputValidator = null;
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

    /**
     * Getter for {@link com.mjakobczyk.vrp.service.VrpInputValidator}.
     *
     * @return validator instance
     */
    public VrpInputValidator getVrpInputValidator() {
        return vrpInputValidator;
    }

    /**
     * Setter for {@link com.mjakobczyk.vrp.service.VrpInputValidator}.
     *
     * @param vrpInputValidator to set
     */
    public void setVrpInputValidator(final VrpInputValidator vrpInputValidator) {
        this.vrpInputValidator = vrpInputValidator;
    }
}
