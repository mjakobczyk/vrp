package com.mjakobczyk.vrp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * L2LValueMapper reflects correlation between two locations in form of numerical value.
 * It can man anything like distances, probabilities or other purposes.
 */
public class L2LValueMapper {

    private static final Double DEFAULT_INITIAL_VALUE = 0D;

    private Map<Location, Integer> locationsIndexesMap;

    private List<List<Double>> data;

    /**
     * Constructor that takes locations list as a parameter.
     * Initial value of each mapping is 0 by default.
     *
     * @param locations to initialize mapper with
     */
    public L2LValueMapper(final List<Location> locations) {
        initializeLocationsIndexesMapWith(locations);
        initializesDataWith(locations, L2LValueMapper.DEFAULT_INITIAL_VALUE);
    }

    /**
     *
     * @param locations
     */
    public L2LValueMapper(final List<Location> locations, final Double initialValue) {
        initializeLocationsIndexesMapWith(locations);
        initializesDataWith(locations, initialValue);
    }

    protected void initializeLocationsIndexesMapWith(final List<Location> locations) {
        this.locationsIndexesMap = new HashMap<>();
        int index = 0;
        for (final Location location : locations) {
            this.locationsIndexesMap.put(location, index);
            index++;
        }
    }

    protected void initializesDataWith(final List<Location> locations, final Double initialValue) {
        final int size = locations.size();
        this.data = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            this.data.add(new ArrayList<>(locations.size()));
            for (int j = 0; j < size; j++) {
                this.data.get(i).add(initialValue);
            }
        }
    }

    /**
     * Puts value to the mapper.
     *
     * @param first  location
     * @param second location
     * @param value  to be put
     */
    public void put(final Location first, final Location second, double value) {
        final int firstIndex = getIndexOf(first);
        final int secondIndex = getIndexOf(second);
        data.get(firstIndex).add(secondIndex, value);
    }

    protected int getIndexOf(final Location location) {
        return this.locationsIndexesMap.get(location);
    }

    /**
     * Gets value from the mapper.
     *
     * @param first  location
     * @param second location
     * @return value
     */
    public double get(final Location first, final Location second) {
        final int firstIndex = getIndexOf(first);
        final int secondIndex = getIndexOf(second);
        return data.get(firstIndex).get(secondIndex);
    }

}
