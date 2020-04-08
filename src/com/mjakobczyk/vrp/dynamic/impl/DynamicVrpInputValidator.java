package com.mjakobczyk.vrp.dynamic.impl;

import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.service.VrpInputValidator;

/**
 * Implementation of {@link com.mjakobczyk.vrp.service.VrpInputValidator}
 * that covers {@link com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput}.
 */
public class DynamicVrpInputValidator implements VrpInputValidator {

    @Override
    public boolean isValid(final VrpInput vrpInput) {
        if (vrpInput instanceof DynamicVrpInput) {
            final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;

            if (dynamicVrpInput.getLocations() != null &&
                    !dynamicVrpInput.getLocations().isEmpty() &&
                    dynamicVrpInput.getAdditionalLocations() != null &&
                    !dynamicVrpInput.getAdditionalLocations().isEmpty()) {
                return true;
            }
        }

        return false;
    }

}
