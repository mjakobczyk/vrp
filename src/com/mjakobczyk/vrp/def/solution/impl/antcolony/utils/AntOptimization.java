package com.mjakobczyk.vrp.def.solution.impl.antcolony.utils;

import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.AntOpt;
import com.mjakobczyk.vrp.model.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * AntOptimization performs exact algorithm of ants to search for the optimal route.
 */
public class AntOptimization {

    private AntColonyParameters parameters;
    private Map<Integer, Location> indexToLocationMap;

    private int locationsCount;
    private int numberOfAnts;
    private double graph[][];
    private double trails[][];
    private List<AntOpt> ants = new ArrayList<>();
    private Random random = new Random();
    private double probabilities[];

    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;

    /**
     * Default constructor.
     *
     * @param allLocations used in sumulation
     * @param parameters   containing values
     */
    public AntOptimization(final List<Location> allLocations, final AntColonyParameters parameters) {
        this.parameters = parameters;
        locationsCount = allLocations.size();
        mapLocationsToIndexes(allLocations);
        graph = generateDistancesMatrixOnBasisOfLocations();
        numberOfAnts = (int) (locationsCount * this.parameters.getAntFactor());
        trails = new double[locationsCount][locationsCount];
        probabilities = new double[locationsCount];
        IntStream.range(0, numberOfAnts)
                .forEach(i -> ants.add(new AntOpt(locationsCount)));
    }

    protected void mapLocationsToIndexes(final List<Location> locations) {
        this.indexToLocationMap = new HashMap<>();
        int currentLocationIndex = 0;
        for (final Location location : locations) {
            this.indexToLocationMap.put(currentLocationIndex++, location);
        }
    }

    protected List<Location> mapIndexesToLocations(int[] locationsIndexes) {
        final List<Location> locations = new ArrayList<>();
        for (int i = 0; i < locationsCount; ++i) {
            locations.add(this.indexToLocationMap.get(locationsIndexes[i]));
        }

        return locations;
    }

    protected double[][] generateDistancesMatrixOnBasisOfLocations() {
        double[][] matrix = new double[locationsCount][locationsCount];
        for (int i = 0; i < locationsCount; ++i) {
            for (int j = 0; j < locationsCount; ++j) {
                matrix[i][j] = indexToLocationMap.get(i).distanceTo(indexToLocationMap.get(j));
            }
        }

        return matrix;
    }

    /**
     * Perform ant optimization
     */
    public List<Location> startAntOptimization() {
        solve();
        return mapIndexesToLocations(bestTourOrder);
    }

    protected int[] solve() {
        setupAnts();
        clearTrails();
        IntStream.range(0, this.parameters.getIterations())
                .forEach(i -> {
                    setupAnts();
                    moveAnts();
                    updateTrails();
                    updateBest();
                });
        return bestTourOrder.clone();
    }

    /**
     * Prepare ants for the simulation
     */
    private void setupAnts() {
        IntStream.range(0, numberOfAnts)
                .forEach(i -> {
                    ants.forEach(ant -> {
                        ant.clear();
                        ant.visitCity(-1, random.nextInt(locationsCount));
                    });
                });
        currentIndex = 0;
    }

    /**
     * At each iteration, move ants
     */
    private void moveAnts() {
        IntStream.range(currentIndex, locationsCount - 1)
                .forEach(i -> {
                    ants.forEach(ant -> ant.visitCity(currentIndex, selectNextCity(ant)));
                    currentIndex++;
                });
    }

    /**
     * Select next city for each ant
     */
    private int selectNextCity(AntOpt ant) {
        int t = random.nextInt(locationsCount - currentIndex);
        if (random.nextDouble() < this.parameters.getRandomFactor()) {
            OptionalInt cityIndex = IntStream.range(0, locationsCount)
                    .filter(i -> i == t && !ant.visited(i))
                    .findFirst();
            if (cityIndex.isPresent()) {
                return cityIndex.getAsInt();
            }
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < locationsCount; i++) {
            total += probabilities[i];
            if (total >= r) {
                return i;
            }
        }

        OptionalInt cityIndex = IntStream.range(0, locationsCount)
                .filter(i -> !ant.visited(i))
                .findFirst();
        if (cityIndex.isPresent()) {
            return cityIndex.getAsInt();
        } else {
            throw new RuntimeException("There are no other cities");
        }
    }

    public void calculateProbabilities(AntOpt ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < locationsCount; l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(trails[i][l], this.parameters.getAlpha()) * Math.pow(1.0 / graph[i][l], this.parameters.getBeta());
            }
        }
        for (int j = 0; j < locationsCount; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], this.parameters.getAlpha()) * Math.pow(1.0 / graph[i][j], this.parameters.getBeta());
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    private void updateTrails() {
        for (int i = 0; i < locationsCount; i++) {
            for (int j = 0; j < locationsCount; j++) {
                trails[i][j] *= this.parameters.getEvaporation();
            }
        }
        for (AntOpt a : ants) {
            double contribution = this.parameters.getQ() / a.trailLength(graph);
            for (int i = 0; i < locationsCount - 1; i++) {
                final int firstLocation = a.trail[i];
                final int secondLocation = a.trail[i + 1];
                trails[firstLocation][secondLocation] += contribution;
            }

            final int firstCity = a.trail[0];
            final int lastCity = a.trail[locationsCount - 1];

            trails[lastCity][firstCity] += contribution;
        }
    }

    private void updateBest() {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0)
                    .trailLength(graph);
        }
        for (AntOpt a : ants) {
            if (a.trailLength(graph) < bestTourLength) {
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
            }
        }
    }

    private void clearTrails() {
        for (int i = 0; i < locationsCount; ++i) {
            for (int j = 0; j < locationsCount; ++j) {
                trails[i][j] = this.parameters.getC();
            }
        }
    }

}
