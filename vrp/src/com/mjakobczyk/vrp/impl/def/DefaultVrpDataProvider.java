package com.mjakobczyk.vrp.impl.def;

import com.mjakobczyk.vrp.VrpDataProvider;
import com.mjakobczyk.vrp.VrpInput;

/**
 * DefaultVrpDataProvider is a default implementation of data provider
 * for solving Vehicle Routing Problem.
 */
public class DefaultVrpDataProvider implements VrpDataProvider {

    @Override
    public VrpInput getVrpInput() {
        // TODO: implement generating VrpInput
        System.out.println("Mock: DefaultVrpDataProvider getVrpInput()");
        return null;
    }
}
