package com.mjakobczyk.vrp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Vehicle visits {@link DeliveryLocation}s. Can hold limited capacity.
 */
public class Vehicle {

    private List<Location> allLocations;
    private List<Location> notVisited;
    private List<Location> visited;
    private int currentCapacity;
    private boolean busy;

    public Vehicle(final int initialCapacity) {
        this.allLocations = new ArrayList<>();
        this.visited = new ArrayList<>();
        this.notVisited = new ArrayList<>();
        this.currentCapacity = initialCapacity;
        this.busy = false;
    }

    public void addToPlan(final DeliveryLocation location) {
        if (canHandle(location)) {
            this.allLocations.add(location);
            this.notVisited.add(location);
            this.currentCapacity -= location.getDemand();
        }
    }

    public void visitNextPlanned() {
        if (!this.notVisited.isEmpty()) {
            this.visited.add(notVisited.get(0));
            this.notVisited.remove(0);
        }
    }

    public boolean visited(final DeliveryLocation location) {
        return visited.contains(location);
    }

    public List<Location> getPlannedTour() {
        final List<Location> result = new ArrayList<>(this.allLocations);
        // Planned tour contains at start and at the end
        result.add(result.get(0));
        return result;
    }

    public void establishNewPlan(final List<Location> locations) {
        this.clear();

        this.allLocations = new ArrayList<>(locations);
        this.notVisited = new ArrayList<>(locations);
        this.visited = new ArrayList<>();
    }

    public void clear() {
        this.allLocations.clear();
        this.visited.clear();
        this.notVisited.clear();
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
        return this.notVisited;
    }

}
