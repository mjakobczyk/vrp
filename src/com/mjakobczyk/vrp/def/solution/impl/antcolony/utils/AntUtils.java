package com.mjakobczyk.vrp.def.solution.impl.antcolony.utils;

import com.mjakobczyk.vrp.def.DefaultVrpUtils;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.Ant;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.AntLocationsHolder;
import com.mjakobczyk.vrp.model.L2LValueMapper;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.service.VrpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * AntFactory generates list of {@link com.mjakobczyk.vrp.def.solution.impl.antcolony.model.Ant}.
 */
public class AntUtils {

    private static final Double TRAILS_SIGNIFICANCE_INITIAL_VALUE = 1D;
    private static final Double DISTANCES_INITIAL_VALUE = Double.MAX_VALUE;

    /**
     * Random for introducing randomness in algorithm.
     */
    private Random random;

    /**
     * Reflect trails between locations significance.
     */
    private L2LValueMapper trailsSignificance;

    /**
     * Reflect distances between locations.
     */
    private L2LValueMapper distances;

    /**
     * Probabilities for picking by Ant a given Location.
     */
    private Map<Location, Double> probabilities;

    /**
     * VrpUtils for counting distances.
     */
    private VrpUtils vrpUtils;

    /**
     * Default constructor of AntUtils.
     */
    public AntUtils(final AntLocationsHolder locations) {
        this.random = new Random();
        this.vrpUtils = new DefaultVrpUtils();
        this.probabilities = new HashMap<>();

        this.trailsSignificance = new L2LValueMapper(locations.getAllLocations(), AntUtils.TRAILS_SIGNIFICANCE_INITIAL_VALUE);
        for (final Location location : locations.getAllLocations()) {
            this.probabilities.put(location, 0D);
        }

        this.distances = new L2LValueMapper(locations.getAllLocations(), AntUtils.DISTANCES_INITIAL_VALUE);
        for (final Location first : locations.getAllLocations()) {
            for (final Location second : locations.getAllLocations()) {
                final double distance = first.distanceTo(second);
                this.distances.put(first, second, distance);
            }
        }
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
     * Set up all ants to be ready for algorithm startup.
     *
     * @param ants      that should be prepared
     * @param locations to be visited
     */
    public void setUp(final List<Ant> ants, final AntLocationsHolder locations) {
        ants.forEach(ant -> ant.setUp(locations.getDepot()));
    }

    /**
     * Move each ant with an inclusion of input algorithm parameters.
     *
     * @param ants       that should be updated
     * @param parameters that should be included in calculations
     */
    public void move(final List<Ant> ants, final AntColonyParameters parameters, final AntLocationsHolder locations) {
        for (int i = 0; i < locations.getLocationsToVisit().size(); ++i) {
            for (final Ant ant : ants) {
                final Optional<Location> optionalLocation = selectLocationFor(ant, parameters, locations);
                optionalLocation.ifPresent(ant::moveTo);
            }
        }

        // Move back all ants to depot
        ants.forEach(ant -> {
            ant.moveTo(locations.getDepot());
        });
    }

    protected Optional<Location> selectLocationFor(final Ant ant, final AntColonyParameters parameters, final AntLocationsHolder locations) {
        final List<Location> locationsToVisit = locations.getLocationsToVisit();

        // First we should check if the Ant should visit a random city.
        // Decision is made upon random factor.
        if (randomFactorAppears(parameters.getRandomFactor())) {
            return findFirstLocationUnvisitedBy(ant, locationsToVisit);
        }

        // If random city was not visited then probabilities should be count for each city to visit.
        calculateProbabilitiesToVisitNextCityBy(ant, locationsToVisit, parameters);
        // After calculating probability of visiting each city the one with the highest
        // probability should be chosen.
        return chooseNextLocationWithTheHighestProbabilityFor(ant, locationsToVisit);
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
        final Location lastVisitedLocation = ant.getTrail().get(ant.getTrail().size() - 1);

        for (final Location locationToVisit : locationsToVisit) {
            if (ant.visited(locationToVisit)) {
                probabilities.put(locationToVisit, 0D);
            } else {
                final double value = countPheromoneLevelBetween(lastVisitedLocation, locationToVisit, parameters);
                // If there was no ant that used this particular path then there are no pheromones on the path.
                // So the resulting probability will be 0.
                final double probability = pheromones == 0 ? 0D : value / pheromones;
                probabilities.put(locationToVisit, probability);
            }
        }
    }

    protected double countTotalPheromonesLevelFor(final Ant ant, final List<Location> locationsToVisit, final AntColonyParameters parameters) {
        double phoromones = 0D;
        final Location lastVisitedLocation = ant.getTrail().get(ant.getTrail().size() - 1);

        for (final Location locationToVosit : locationsToVisit) {
            if (!ant.visited(locationToVosit)) {
                phoromones += countPheromoneLevelBetween(lastVisitedLocation, locationToVosit, parameters);
            }
        }

        return phoromones;
    }

    protected double countPheromoneLevelBetween(final Location first, final Location second, final AntColonyParameters parameters) {
        final double trailSignificance = trailsSignificance.get(first, second);
        final double trailDistance = distances.get(first, second);
        return Math.pow(trailSignificance, parameters.getAlpha()) * Math.pow(trailDistance, parameters.getBeta());
    }

    protected Optional<Location> chooseNextLocationWithTheHighestProbabilityFor(final Ant ant, final List<Location> locationsToVisit) {
        double randomDouble = random.nextDouble();
        double total = 0;

        for (final Location location : locationsToVisit) {
            total += probabilities.get(location);

            if (total >= randomDouble) {
                return Optional.of(location);
            }
        }

        return findFirstLocationUnvisitedBy(ant, locationsToVisit);
    }

    public void updateTrailsUsedBy(final List<Ant> ants, final AntLocationsHolder locations, final AntColonyParameters parameters) {
        evaporateTrails(locations, parameters);
        updateContributionOfAll(ants, parameters);
    }

    protected void evaporateTrails(final AntLocationsHolder locations, final AntColonyParameters parameters) {
        for (final Location first : locations.getAllLocations()) {
            for (final Location second : locations.getAllLocations()) {
                final double oldTrailSignificance = trailsSignificance.get(first, second);
                final double newTrailSignificance = oldTrailSignificance * parameters.getEvaporation();
                trailsSignificance.put(first, second, newTrailSignificance);
            }
        }
    }

    protected void updateContributionOfAll(final List<Ant> ants, final AntColonyParameters parameters) {
        ants.forEach(ant -> {
            final double trailLength = parameters.getQ() / getVrpUtils().countDistanceAndIncludeFirstLocationTwiceFor(ant.getTrail());
            final double antContribution = parameters.getQ() / trailLength;
            final List<Location> antTrail = ant.getTrail();

            for (int i = 0; i < antTrail.size() - 2; ++i) {
                for (int j = 1; j < antTrail.size() - 1; ++j) {
                    trailsSignificance.put(antTrail.get(i), antTrail.get(j), antContribution);
                }
            }
            // Contribution from the last location to the depot should be included too
            trailsSignificance.put(antTrail.get(antTrail.size() - 1), antTrail.get(0), antContribution);
        });
    }

    public List<Location> findBestTrailFrom(final List<Ant> ants) {
        Ant bestAnt = null;
        double bestDistance = Double.MAX_VALUE;

        for (Ant ant : ants) {
            final double distance = getVrpUtils().countDistanceFor(ant.getTrail());
            if (distance < bestDistance) {
                bestDistance = distance;
                bestAnt = ant;
            }
        }

        return bestAnt.getTrail();
    }

    public void clear(final List<Ant> ants) {
        ants.forEach(Ant::clear);
    }

    /**
     * Getter for {@link com.mjakobczyk.vrp.service.VrpUtils}.
     *
     * @return vrpUtils instance
     */
    public VrpUtils getVrpUtils() {
        return vrpUtils;
    }

    /**
     * Setter for {@link com.mjakobczyk.vrp.service.VrpUtils}.
     *
     * @param vrpUtils instance
     */
    public void setVrpUtils(VrpUtils vrpUtils) {
        this.vrpUtils = vrpUtils;
    }
}
