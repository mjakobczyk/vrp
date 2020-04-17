package com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model;

import com.mjakobczyk.vrp.model.Location;

import java.util.Collections;
import java.util.List;

/**
 * Ant represents a single worker that look for the best path in
 * {@link com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.AntColonyDynamicVrpSolutionProviderStrategy}.
 */
public class Ant {

    /**
     * trail is a list of locations visited by Ant.
     */
    private List<Location> trail;

    /**
     * Constructor of Ant that takes list of locations to visit.
     */
    public Ant() {
        this.trail = Collections.emptyList();
    }

    /**
     * Moves ant, forces it to visit given location.
     */
    public void moveTo(final Location location) {
        this.trail.add(location);
    }

    public boolean visited(final Location location) {
        return trail.contains(location);
    }

    /**
     * Getter for trail.
     *
     * @return trail
     */
    public List<Location> getTrail() {
        return trail;
    }

}
