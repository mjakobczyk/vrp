package com.mjakobczyk.vrp.service;

import com.mjakobczyk.vrp.model.VrpInput;

/**
 * Validator for {@link com.mjakobczyk.vrp.model.VrpInput}.
 */
public interface VrpInputValidator {

    /**
     * Checks if VrpInput instance is valid for processing.
     *
     * @param vrpInput to be checked
     * @return true if valid, false otherwise
     */
    boolean isValid(final VrpInput vrpInput);

}
