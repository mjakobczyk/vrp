package com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing.model;

import com.mjakobczyk.vrp.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * SimulatedAnnealingTravelOrder describes in what order should locations
 * be checked during algorithm analysis.
 */
public class SimulatedAnnealingTravelOrder {

    /**
     * List of travel locations.
     */
    private List<LocationsPair> travels;

    /**
     * Constructor of SimulatedAnnealingTravelOrder.
     *
     * @param locations list
     */
    public SimulatedAnnealingTravelOrder(final List<Location> locations) {
        generateLocationsList(locations);
    }

    /**
     * Updates travels order on basis of locations list.
     *
     * @param locations list
     */
    public void updateTravelsOrder(final List<Location> locations) {
        generateLocationsList(locations);
    }

    private void generateLocationsList(final List<Location> locations) {
        if (travels == null) {
            travels = new ArrayList<>();
        } else {
            travels.clear();
        }

        int locationsCount = locations.size();

        for (int i = 0; i < locationsCount; ++i) {
            for (int j = i + 1; j < locationsCount;  ++j) {
                travels.add(new LocationsPair(i, j));
            }
        }
    }

    /**
     * Getter of travels.
     *
     * @return travels list
     */
    public List<LocationsPair> getTravels() {
        return travels;
    }
}
