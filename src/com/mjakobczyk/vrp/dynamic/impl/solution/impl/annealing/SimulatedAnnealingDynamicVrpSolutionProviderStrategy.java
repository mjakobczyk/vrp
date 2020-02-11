package com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing;

import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing.model.SimulatedAnnealingTemperature;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing.model.SimulatedAnnealingTravelOrder;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SimulatedAnnealingDynamicVrpSolutionProviderStrategy provides implementation of
 * simulated annealing algorithm, choosing optimal route for VRP. This implementation
 * includes dynamic location requests.
 */
public class SimulatedAnnealingDynamicVrpSolutionProviderStrategy implements VrpSolutionProviderStrategy {

    /**
     * SimulatedAnnealingDynamicVrpSolutionProviderStrategy logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(SimulatedAnnealingDynamicVrpSolutionProviderStrategy.class));

    /**
     * Maximum random number that can be generated for getting additional city.
     */
    private static final int MAX_RANDOM_NUMBER = 100;

    /**
     * Threshold value above which additional pick can be performed.
     */
    private static final int ADDITIONAL_PICK_THRESHOLD = 70;

    /**
     * Random to generate values for calculating if additional locations should appear.
     */
    private final Random random = new Random();


    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        if (vrpInput != null) {
            return vrpInput.getLocations().isEmpty() ? Optional.empty() : runSimulatedAnnealingAnalysisFor(vrpInput);
        }

        return Optional.empty();
    }

    protected Optional<VrpOutput> runSimulatedAnnealingAnalysisFor(final VrpInput vrpInput) {
        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;
        final List<Location> locations = dynamicVrpInput.getLocations();
        final List<Location> additionalLocations = dynamicVrpInput.getAdditionalLocations();

        final SimulatedAnnealingTemperature temperature = new SimulatedAnnealingTemperature();
        final SimulatedAnnealingTravelOrder travelsOrder = new SimulatedAnnealingTravelOrder(locations);

        List<Location> bestSolution = new ArrayList<>(locations);
        int additionalLocationsCounter = 0;
        double bestDistance = countDistanceFor(locations);

        LOG.log(Level.INFO, "Initial distance for " + locations.size() + " locations = " + bestDistance);

        while (temperature.isValid()) {
            if (additionalLocationsCounter < additionalLocations.size() && shouldAdditionalLocationAppear()) {
                locations.add(additionalLocations.get(additionalLocationsCounter++));
                bestSolution.clear();
                bestSolution = new ArrayList<>(locations);
                travelsOrder.updateTravelsOrder(locations);
            }

            for (int i = 0; i < travelsOrder.getTravels().size(); ++i) {
                Collections.swap(locations, travelsOrder.getTravels().get(i).getFirstLocation(), travelsOrder.getTravels().get(i).getSecondLocation());
                double distanceAfterSwap = countDistanceFor(locations);

                if (distanceAfterSwap < bestDistance) {
                    bestSolution.clear();
                    bestSolution = new ArrayList<>(locations);
                    bestDistance = distanceAfterSwap;
                } else if (Math.exp((bestDistance - distanceAfterSwap) / temperature.getCurrent()) < Math.random()) {
                    Collections.swap(locations, travelsOrder.getTravels().get(i).getFirstLocation(), travelsOrder.getTravels().get(i).getSecondLocation());
                }
            }

            temperature.decrease();
        }

        LOG.log(Level.INFO, "Found best distance for " + bestSolution.size() + " locations = " + bestDistance);

        return Optional.of(new DynamicVrpOutput(bestSolution));
    }

    protected double countDistanceFor(final List<Location> locations) {
        double totalDistance = 0.0f;

        for (int i = 0; i < locations.size() - 1; ++i) {
            totalDistance += locations.get(i).distanceTo(locations.get(i + 1));
        }

        return totalDistance;
    }

    protected boolean shouldAdditionalLocationAppear() {
        return random.nextInt(MAX_RANDOM_NUMBER) > ADDITIONAL_PICK_THRESHOLD;
    }

}
