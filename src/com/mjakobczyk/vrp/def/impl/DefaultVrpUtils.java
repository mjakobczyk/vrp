package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.service.VrpUtils;

import java.util.List;

/**
 * Default implementation of {@link com.mjakobczyk.vrp.service.VrpUtils}.
 */
public class DefaultVrpUtils implements VrpUtils {

    @Override
    public double countDistanceFor(List<Location> locations) {
        double totalDistance = 0.0f;

        for (int i = 0; i < locations.size() - 1; ++i) {
            totalDistance += locations.get(i).distanceTo(locations.get(i + 1));
        }

        return totalDistance;
    }

    @Override
    public double countDistanceAndIncludeFirstLocationTwiceFor(List<Location> locations) {
        double totalDistance = countDistanceFor(locations);
        totalDistance += locations.get(locations.size()-1).distanceTo(locations.get(0));

        return totalDistance;
    }
}
