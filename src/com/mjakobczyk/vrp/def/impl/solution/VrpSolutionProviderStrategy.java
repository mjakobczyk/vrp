package com.mjakobczyk.vrp.def.impl.solution;

import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;
import com.mjakobczyk.vrp.service.VrpInputValidator;
import com.mjakobczyk.vrp.service.VrpUtils;

import java.util.Optional;

/**
 * VrpSolutionProviderStrategy describes actual implementation of dealing with VRP.
 */
public abstract class VrpSolutionProviderStrategy {

    /**
     * Validator for input data.
     */
    private VrpInputValidator vrpInputValidator;

    /**
     * VrpUtils for counting distances.
     */
    private VrpUtils vrpUtils;

    /**
     * Returns found optimal router for given VrpInput.
     *
     * @param vrpInput to analyze
     * @return found optimal route
     */
    public abstract Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput);

    /**
     * Default constructor of VrpSolutionProviderStrategy.
     */
    public VrpSolutionProviderStrategy() {
    }

    /**
     * Constructor of VrpSolutionProviderStrategy.
     *
     * @param vrpInputValidator implementation that should be used for input validation
     */
    public VrpSolutionProviderStrategy(final VrpInputValidator vrpInputValidator) {
        this.vrpInputValidator = vrpInputValidator;
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

    /**
     * Getter for {@link com.mjakobczyk.vrp.service.VrpUtils}.
     *
     * @return vrpUtils instance
     */
    public VrpUtils getVrpUtils() {
        return vrpUtils;
    }

    /**
     * Setter for {@link com.mjakobczyk.vrp.service.VrpUtils}.
     *
     * @param vrpUtils instance
     */
    public void setVrpUtils(VrpUtils vrpUtils) {
        this.vrpUtils = vrpUtils;
    }
}
