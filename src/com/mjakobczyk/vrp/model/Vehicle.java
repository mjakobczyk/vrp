package com.mjakobczyk.vrp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Vehicle visits {@link DeliveryLocation}s. Can hold limited capacity.
 */
public class Vehicle {

    private List<Location> allLocations;
    private List<Location> visited;
    private int currentCapacity;
    private boolean busy;

    public Vehicle(final int initialCapacity) {
        this.allLocations = new ArrayList<>();
        this.visited = new ArrayList<>();
        this.currentCapacity = initialCapacity;
        this.busy = false;
    }

    public void visit(final DeliveryLocation location) {
        if (canHandle(location)) {
            this.allLocations.add(location);
            this.currentCapacity -= location.getDemand();
        }
    }

    public boolean visited(final DeliveryLocation location) {
        return visited.contains(location);
    }

    public void clear() {
        this.allLocations.clear();
        this.visited.clear();
    }

    public boolean canHandle(final DeliveryLocation location) {
        return currentCapacity - location.getDemand() >= 0;
    }

    public void changeBusyState() {
        this.busy = !this.busy;
    }

    public boolean isBusy() {
        return this.busy;
    }

    public List<Location> getAllLocations() {
        return this.allLocations;
    }

    public List<Location> getNotVisitedLocations() {
        final List<Location> notVisited = new ArrayList<>(this.allLocations);
        notVisited.removeAll(visited);
        return notVisited;
    }

}
