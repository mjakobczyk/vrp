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
     * inputLocations is a list of locations that are originally
     * contained in VrpInput.
     */
    private List<Location> inputLocations;

    /**
     * trail is a list of locations visited by Ant.
     */
    private List<Location> trail;

    /**
     * Constructor of Ant that takes list of locations to visit.
     */
    public Ant(final List<Location> locations) {
        this.inputLocations = locations;
        this.trail = Collections.emptyList();
    }

    /**
     * Getter for trail.
     *
     * @return trail
     */
    public List<Location> getTrail() {
        return trail;
    }

    /**
     * Moves ant, forces it to visit another location.
     */
    public void move() {
        int i = 0;
        while (true) {
            final Location location = inputLocations.get(i);
            if (!trail.contains(location)) {
                trail.add(location);
                break;
            }
        }
    }

    public boolean visited(final Location location) {
        return trail.contains(location);
    }
}
