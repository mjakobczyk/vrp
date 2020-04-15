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

    /**
     * Getter for inputLocations.
     *
     * @return list of input locations
     */
    public List<Location> getInputLocations() {
        return inputLocations;
    }

}
