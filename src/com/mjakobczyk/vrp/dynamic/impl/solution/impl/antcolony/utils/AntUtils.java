package com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.utils;

import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.Ant;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.model.AntLocationsHolder;
import com.mjakobczyk.vrp.model.L2LValueMapper;
import com.mjakobczyk.vrp.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
     * Reflect trails between locations significance.
     */
    private L2LValueMapper trailsSignificance;

    /**
     * Probabilities for picking by Ant a given Location.
     */
    private Map<Location, Double> probabilities;

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
     * @return list of ants
     */
    public List<Ant> generate(final int antsCount) {
        final List<Ant> ants = new ArrayList<>();
        IntStream.range(0, antsCount).forEach(i -> ants.add(new Ant()));

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
     * @param ants      that should be prepared
     * @param locations to be visited
     */
    public void setUp(final List<Ant> ants, final AntLocationsHolder locations) {
        ants.forEach(ant -> ant.setUp(locations.getDepot()));
        this.trailsSignificance = new L2LValueMapper(locations.getAllLocations());
        for (final Location location : locations.getAllLocations()) {
            this.probabilities.put(location, 0D);
        }
    }

    /**
     * Move each ant with an inclusion of input algorithm parameters.
     *
     * @param ants       that should be updated
     * @param parameters that should be included in calculations
     */
    public void move(final List<Ant> ants, final AntColonyParameters parameters, final AntLocationsHolder locations) {
        ants.forEach(ant -> {
            final Optional<Location> optionalLocation = selectLocationFor(ant, parameters, locations);
            optionalLocation.ifPresent(ant::moveTo);
        });
    }

    protected Optional<Location> selectLocationFor(final Ant ant, final AntColonyParameters parameters, final AntLocationsHolder locations) {
        final List<Location> locationsToVisit = locations.getLocationsToVisit();

        if (randomFactorAppears(parameters.getRandomFactor())) {
            return findFirstLocationUnvisitedBy(ant, locationsToVisit);
        }

        calculateProbabilitiesToVisitNextCityBy(ant, locationsToVisit, parameters);
        chooseNextLocationWithTheChangestProbabilityFor(ant, locationsToVisit);

        // TODO: implement second case when random factor is not used

        return Optional.empty();
    }

    protected boolean randomFactorAppears(final double randomFactor) {
        return random.nextDouble() < randomFactor;
    }

    protected Optional<Location> findFirstLocationUnvisitedBy(final Ant ant, final List<Location> locationsToVisit) {
        for (final Location location : locationsToVisit) {
            if (!ant.getTrail().contains(location)) {
                return Optional.of(location);
            }
        }

        return Optional.empty();
    }

    protected void calculateProbabilitiesToVisitNextCityBy(final Ant ant, final List<Location> locationsToVisit, final AntColonyParameters parameters) {
        final double pheromones = countTotalPheromonesLevelFor(ant, locationsToVisit, parameters);

        for (final Location location : locationsToVisit) {
            if (ant.visited(location)) {
                probabilities.put(location, 0D);
            } else {
                double value = 0D; // TODO: implement counting probability value using pheromones
                probabilities.put(location, value);
            }
        }
    }

    protected double countTotalPheromonesLevelFor(final Ant ant, final List<Location> locationsToVisit, final AntColonyParameters parameters) {
        double phoromones = 0D;

        for (final Location location : locationsToVisit) {
            if (!ant.visited(location)) {
                phoromones += 0D; // TODO: count pheromone
            }
        }

        return phoromones;
    }

    protected Optional<Location> chooseNextLocationWithTheChangestProbabilityFor(final Ant ant, final List<Location> locationsToVisit) {
        double randomDouble = random.nextDouble();
        double total = 0;

        for (final Location location : locationsToVisit) {
            total += probabilities.get(location);

            if (total >= randomDouble) {
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

    public void clear(final List<Ant> ants) {
        ants.forEach(Ant::clear);
        this.trailsSignificance = null;
    }

}
