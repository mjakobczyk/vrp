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

public class AntOptimization {

    private AntColonyParameters parameters;

    // Merge togeter to parameters
    private double c = 1.0;
    private double alpha = 1;
    private double beta = 5;
    private double evaporation = 0.5;
    private double Q = 500;
    private double antFactor = 0.8;
    private double randomFactor = 0.1;

    private int maxIterations = 1000;

    private List<Location> allLocations;
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
    private int bestTourIteration;

    public AntOptimization(final List<Location> allLocations) {
//        graph = generateRandomMatrix(noOfCities);
        // TODO: initialize graph with distances on basis of algorithm input
        this.allLocations = new ArrayList<>(allLocations);
        locationsCount = allLocations.size();
        mapLocationsToIndexes(allLocations);
        graph = generateDistancesMatrixOnBasisOfLocations();

        numberOfAnts = (int) (locationsCount * antFactor);

        trails = new double[locationsCount][locationsCount];
        // trails
        probabilities = new double[locationsCount];
        IntStream.range(0, numberOfAnts)
                .forEach(i -> ants.add(new AntOpt(locationsCount)));
    }

    public void mapLocationsToIndexes(final List<Location> locations) {
        this.indexToLocationMap = new HashMap<>();
        int currentLocationIndex = 0;
        for (final Location location : locations) {
            this.indexToLocationMap.put(currentLocationIndex++, location);
        }
    }

    public List<Location> mapIndexesToLocations(int[] locationsIndexes) {
        final List<Location> locations = new ArrayList<>();
        for (int i = 0; i < locationsCount; ++i) {
            locations.add(this.indexToLocationMap.get(locationsIndexes[i]));
        }

        return locations;
    }

    public double[][] generateDistancesMatrixOnBasisOfLocations() {
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
        IntStream.rangeClosed(1, 1) // TODO: adjust attempts
                .forEach(i -> {
//                    System.out.println("Attempt #" + i);
                    solve();
                });
        // TODO: add mapping back from index to Location

        return mapIndexesToLocations(bestTourOrder);
    }

    /**
     * Use this method to run the main logic
     */
    public int[] solve() {
        setupAnts();
        clearTrails();
        IntStream.range(0, maxIterations)
                .forEach(i -> {
                    setupAnts();
                    moveAnts();
                    updateTrails();
                    updateBest(i);
                });
//        System.out.println("Best tour length: " + (bestTourLength));
//        System.out.println("Best tour order: " + Arrays.toString(bestTourOrder));
//        System.out.println("Best tour iteration: " + bestTourIteration);
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
        if (random.nextDouble() < randomFactor) {
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

//        throw new RuntimeException("There are no other cities");

        // Had to adapt it this way
        OptionalInt cityIndex = IntStream.range(0, locationsCount)
                .filter(i -> !ant.visited(i))
                .findFirst();
        if (cityIndex.isPresent()) {
            return cityIndex.getAsInt();
        }
        else {
            throw new RuntimeException("There are no other cities");
        }
    }

    /**
     * Calculate the next city picks probabilites
     */
    public void calculateProbabilities(AntOpt ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < locationsCount; l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);
            }
        }
        for (int j = 0; j < locationsCount; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    /**
     * Update trails that ants used
     */
    private void updateTrails() {
        for (int i = 0; i < locationsCount; i++) {
            for (int j = 0; j < locationsCount; j++) {
                trails[i][j] *= evaporation;
            }
        }
        for (AntOpt a : ants) {
            double contribution = Q / a.trailLength(graph);
            for (int i = 0; i < locationsCount - 1; i++) {
                trails[a.trail[i]][a.trail[i + 1]] += contribution;

                // Each ant visited all cities but picked different trails,
                // not all trails were used
                // A    B    C
                //
                // D    E    F
                // |  /
                // G -  H    I
                trails [ a.trail[i] ] [ a.trail[i + 1] ] += contribution;
            }
            trails[a.trail[locationsCount - 1]][a.trail[0]] += contribution;
        }
    }

    /**
     * Update the best solution
     */
    private void updateBest(int iteration) {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0)
                    .trailLength(graph);
//            System.out.println("First best tour: " + bestTourLength);
        }
        for (AntOpt a : ants) {
            if (a.trailLength(graph) < bestTourLength) {
//                System.out.println("Improving from " + bestTourLength + " to " + a.trailLength(graph) );
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
                bestTourIteration = iteration;
            }
        }
    }

    /**
     * Clear trails after simulation
     */
    private void clearTrails() {
        IntStream.range(0, locationsCount)
                .forEach(i -> {
                    IntStream.range(0, locationsCount)
                            .forEach(j -> trails[i][j] = c);
                });
    }

}
