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
public class SimulatedAnnealingDynamicVrpSolutionProviderStrategy extends VrpSolutionProviderStrategy {

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
        if (vrpInput == null) {
            return Optional.empty();
        }

        if (!(vrpInput instanceof DynamicVrpInput)) {
            LOG.log(Level.SEVERE, "SimulatedAnnealingDynamicVrpSolutionProviderStrategy expects DynamicVrpInput as an input!");
            return Optional.empty();
        }

        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;

        if (vrpInput.getLocations() != null && !vrpInput.getLocations().isEmpty()) {
            return runSimulatedAnnealingAnalysisFor(dynamicVrpInput);
        }

        return Optional.empty();
    }

    protected Optional<VrpOutput> runSimulatedAnnealingAnalysisFor(final VrpInput vrpInput) {
        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;
        final List<Location> allLocations = dynamicVrpInput.getLocations();
        final List<Location> additionalLocations = dynamicVrpInput.getAdditionalLocations();

//        final Location depot = allLocations.get(0);
//        final List<Location> locationsToVisit = allLocations.subList(1, allLocations.size());

        final SimulatedAnnealingTemperature temperature = new SimulatedAnnealingTemperature();
        final SimulatedAnnealingTravelOrder travelsOrder = new SimulatedAnnealingTravelOrder(allLocations);

        List<Location> bestSolution = new ArrayList<>(allLocations);
        int additionalLocationsCounter = 0;
        double bestDistance = countDistanceFor(allLocations);

        LOG.log(Level.INFO, "Initial distance for " + allLocations.size() + " locations = " + bestDistance);

        while (temperature.isValid()) {
            if (additionalLocationsCounter < additionalLocations.size() && shouldAdditionalLocationAppear()) {
                allLocations.add(additionalLocations.get(additionalLocationsCounter++));
                bestSolution.clear();
                bestSolution = new ArrayList<>(allLocations);
                travelsOrder.updateTravelsOrder(allLocations);
            }

            for (int i = 0; i < travelsOrder.getTravels().size(); ++i) {
                Collections.swap(allLocations, travelsOrder.getTravels().get(i).getFirstLocation(), travelsOrder.getTravels().get(i).getSecondLocation());
                double distanceAfterSwap = countDistanceFor(allLocations);

                if (distanceAfterSwap < bestDistance) {
                    bestSolution.clear();
                    bestSolution = new ArrayList<>(allLocations);
                    bestDistance = distanceAfterSwap;
                } else if (Math.exp((bestDistance - distanceAfterSwap) / temperature.getCurrent()) < Math.random()) {
                    Collections.swap(allLocations, travelsOrder.getTravels().get(i).getFirstLocation(), travelsOrder.getTravels().get(i).getSecondLocation());
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
