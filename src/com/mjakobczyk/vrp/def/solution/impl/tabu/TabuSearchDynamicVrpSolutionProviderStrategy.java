package com.mjakobczyk.vrp.def.solution.impl.tabu;

import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.annealing.model.SimulatedAnnealingTravelOrder;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.*;

/**
 * Tabu Search implementation of {@link VrpSolutionProviderStrategy}.
 */
public class TabuSearchDynamicVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {

    private Map<Location, Integer> tabu;
    private final int neighbourhood = 3;
    private final int cadency = 5;

    public TabuSearchDynamicVrpSolutionProviderStrategy() {
        super();
        this.tabu = new HashMap<>();
    }

    @Override
    public Optional<VrpOutput> findOptimalRouteFor(VrpInput vrpInput) {
        return runTabuSearchAnalysisFor(vrpInput);
    }

    protected Optional<VrpOutput> runTabuSearchAnalysisFor(final VrpInput vrpInput) {
        final List<Location> allLocations = vrpInput.getLocations();
        final Location depot = allLocations.get(0);
        final List<Location> locationsToVisit = allLocations.subList(1, allLocations.size());
        initializeMapWith(allLocations);

        this.tabu.clear();

        List<Location> resultLocationsList = new ArrayList<>();
        resultLocationsList.add(depot);
        resultLocationsList.addAll(locationsToVisit);
        resultLocationsList.add(depot);

        List<Location> bestSolution = new ArrayList<>(resultLocationsList);
        final double initialDistannce =  getVrpUtils().countDistanceFor(resultLocationsList);
        double bestDistance = Double.MAX_VALUE;

        for(int it = 0; it < 1000; ++it) {
            for (int i = neighbourhood + 1; i < resultLocationsList.size() - neighbourhood - 1; ++i) {
                int firstBest = -1;
                int secondBest = -1;
                for (int j = i - neighbourhood; j < i + neighbourhood; ++j) {
                    for (int k = i - neighbourhood; k < i + neighbourhood; ++k) {
                        Collections.swap(resultLocationsList, j, k);
                        double distanceAfterSwap = getVrpUtils().countDistanceFor(resultLocationsList);

                        if (distanceAfterSwap < bestDistance) {
                            firstBest = j;
                            secondBest = k;
                            bestDistance = distanceAfterSwap;
                        }

                        Collections.swap(resultLocationsList, j, k);
                    }
                }

                if (firstBest != -1 && secondBest != -1) {
                    Collections.swap(resultLocationsList, firstBest, secondBest);
                    final Location firstLocation = allLocations.get(firstBest);
                    final Location secondLocation = allLocations.get(secondBest);
                    int firstOldValue = this.tabu.getOrDefault(firstLocation, 0);
                    int secondOldValue = this.tabu.getOrDefault(secondLocation, 0);

                    if (firstOldValue != 0 || secondOldValue != 0) {
                        final double currentDistance = getVrpUtils().countDistanceFor(resultLocationsList);
                        if (shouldAcceptSolution(currentDistance, bestDistance)) {
                            Collections.swap(resultLocationsList, firstBest, secondBest);
                        }
                    }

                    this.tabu.put(firstLocation, cadency);
                    this.tabu.put(secondLocation, cadency);
                }
            }

            decreaseTabu(allLocations);
        }

        this.tabu.clear();

        return Optional.of(new DefaultVrpOutput(bestSolution));
    }

    protected void initializeMapWith(final List<Location> locations) {
        for (final Location location : locations) {
            this.tabu.put(location, 0);
        }
    }

    protected void decreaseTabu(final List<Location> locations) {
        for (final Location location : locations) {
            int oldValue = this.tabu.getOrDefault(location, 0);

            if (oldValue > 0) {
                --oldValue;
                this.tabu.put(location, oldValue);
            }
        }
    }

    protected boolean shouldAcceptSolution(final double oldValue, final double newValue) {
        return newValue / oldValue >= 1.05;
    }
}
