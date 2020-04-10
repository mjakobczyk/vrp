package com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony;

import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.Optional;

public class AntColonyDynamicVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {


    @Override
    public Optional<VrpOutput> findOptimalRouteFor(VrpInput vrpInput) {
        return Optional.empty();
    }
}
