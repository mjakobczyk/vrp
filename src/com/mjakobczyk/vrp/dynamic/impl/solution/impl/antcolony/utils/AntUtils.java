package com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.utils;

import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.Ant;
import com.mjakobczyk.vrp.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * AntFactory generates list of {@link com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.Ant}.
 */
public class AntUtils {

    public List<Ant> generate(final int antsCount, final List<Location> locations) {
        final List<Ant> ants = new ArrayList<>();
        IntStream.range(0, antsCount).forEach(i -> ants.add(new Ant(locations)));

        return ants;
    }

    public void includeAdditionalLocationFor(final List<Ant> ants, final Location location) {
        // TODO
    }

    public void setUp(final List<Ant> ants) {
        // TODO
    }

    public void move(final List<Ant> ants) {
        // TODO
    }

    public void updatePathsUsedBy(final List<Ant> ants) {
        // TODO
    }

    public List<Location> findBestPathFrom(final List<Ant> ants) {
        return Collections.emptyList();
    }

}
