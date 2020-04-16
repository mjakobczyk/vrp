package com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.utils;

import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.Ant;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * AntFactory generates list of {@link com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.Ant}.
 */
public class AntUtils {

    /**
     * Random for introducing randomness in algorithm.
     */
    private Random random;

    /**
     * Default constructor of AntUtils.
     */
    public AntUtils() {
        this.random = new Random();
    }

    /**
     * Generates ants colony of given count and on basis of given input locations.
     *
     * @param antsCount that should be generated
     * @param locations that should be visited by each ant
     * @return list of ants
     */
    public List<Ant> generate(final int antsCount, final List<Location> locations) {
        final List<Ant> ants = new ArrayList<>();
        IntStream.range(0, antsCount).forEach(i -> ants.add(new Ant(locations)));

        return ants;
    }

    /**
     * Handle dynamic addition of location for every ant.
     *
     * @param ants     that should handle the addition
     * @param location that should be included
     */
    public void includeAdditionalLocationFor(final List<Ant> ants, final Location location) {
        // TODO
    }

    /**
     * Set up all ants to be ready for algorithm startup.
     *
     * @param ants that should be prepared
     */
    public void setUp(final List<Ant> ants) {
        // TODO
    }

    /**
     * Move each ant with an inclusion of input algorithm paramters.
     *
     * @param ants       that should be updated
     * @param parameters that should be included in calculations
     */
    public void move(final List<Ant> ants, final AntColonyParameters parameters) {
        ants.forEach(ant -> {
            final Optional<Location> optionalLocation = selectLocationFor(ant, parameters);
            optionalLocation.ifPresent(ant::moveTo);
        });
    }

    protected Optional<Location> selectLocationFor(final Ant ant, final AntColonyParameters parameters) {
        if (randomFactorAppears(parameters.getRandomFactor())) {
            return findFirstLocationUnvisitedBy(ant);
        }

        // TODO: implement second case when random factor is not used

        return Optional.empty();
    }

    protected boolean randomFactorAppears(final double randomFactor) {
        return random.nextDouble() < randomFactor;
    }

    protected Optional<Location> findFirstLocationUnvisitedBy(final Ant ant) {
        final List<Location> inputLocations = ant.getInputLocations();

        for (final Location location : inputLocations) {
            if (!ant.getTrail().contains(location)) {
                return Optional.of(location);
            }
        }

        return Optional.empty();
    }

    public void updateTrailsUsedBy(final List<Ant> ants) {
        // TODO
    }

    public List<Location> findBestTrailFrom(final List<Ant> ants) {
        return Collections.emptyList();
    }

}
