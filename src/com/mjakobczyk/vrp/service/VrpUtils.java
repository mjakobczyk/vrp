package com.mjakobczyk.vrp.service;

import com.mjakobczyk.vrp.model.Location;

import java.util.List;

/**
 * VrpUtils offers handy utilities common to all VRP methods.
 */
public interface VrpUtils {

    /**
     * Counts total distance for given list of locations.
     *
     * @param locations list
     * @return total distance
     */
    double countDistanceFor(final List<Location> locations);

    /**
     * Counts total distance for given list of locations with an inclusion
     * of the VRP-specific case of returning to the depot after visiting
     * the last city so it should be included at the end of the list.
     * However, regarding performance factor instead of copying list
     * every time there is a need for counting distance it will be
     * dynamically applied here assuming that depot is the first location
     * on the list.
     *
     * @param locations list
     * @return total distance with first location included on the last place
     */
    double countDistanceAndIncludeFirstLocationTwiceFor(final List<Location> locations);

}
