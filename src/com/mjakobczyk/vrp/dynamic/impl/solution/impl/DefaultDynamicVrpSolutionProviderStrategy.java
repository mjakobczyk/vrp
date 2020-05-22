package com.mjakobczyk.vrp.dynamic.impl.solution.impl;

import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpInputValidator;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.DefaultVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * DynamicVrpSolutionProviderStrategy provides implementation of strategy
 * allowing to find optimal route for Dynamic VRP.
 */
public class DefaultDynamicVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {

    /**
     * DefaultVrpSolutionProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpSolutionProviderStrategy.class));

    /**
     * Default constructor of {@link com.mjakobczyk.vrp.dynamic.impl.solution.impl.DefaultDynamicVrpSolutionProviderStrategy}.
     * Initially it makes use of {@link com.mjakobczyk.vrp.dynamic.impl.DynamicVrpInputValidator}.
     */
    public DefaultDynamicVrpSolutionProviderStrategy() {
        setVrpInputValidator(new DynamicVrpInputValidator());
    }

    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        if (getVrpInputValidator().isValid(vrpInput)) {
            return Optional.of(mapVrpInputToVrpOutput(vrpInput));
        }

        return Optional.empty();
    }

    protected VrpOutput mapVrpInputToVrpOutput(final VrpInput vrpInput) {
        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;
        final List<Location> locations = dynamicVrpInput.getLocations();
        locations.addAll(dynamicVrpInput.getAdditionalLocations());

        return new DefaultVrpOutput(locations);
    }
}
