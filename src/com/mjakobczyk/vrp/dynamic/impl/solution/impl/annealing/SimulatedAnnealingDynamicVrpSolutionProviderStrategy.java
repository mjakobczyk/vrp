package com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing;

import com.mjakobczyk.location.Location;
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
    private static final int MAX_RANDOM_NUMBER = 100;
    private static final int ADDITIONAL_PICK_THRESHOLD = 70;


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
        int additionalLocationsCounter = 0;

        final SimulatedAnnealingTemperature temperature = new SimulatedAnnealingTemperature();
        final SimulatedAnnealingTravelOrder travelsOrder = new SimulatedAnnealingTravelOrder(locations);

        List<Location> bestSolution = new ArrayList<>();
        double bestDistance = countDistanceFor(locations);

        while (temperature.isValid()) {
            if (shouldAdditionalLocationAppear()) {
                locations.add(additionalLocations.get(additionalLocationsCounter++));
                travelsOrder.updateTravelsOrder(locations);
            }

            for (int i = 0; i < travelsOrder.getTravels().size(); ++i) {
                double initialDistance = countDistanceFor(locations);
                Collections.swap(locations, travelsOrder.getTravels().get(0).getFirstLocation(), travelsOrder.getTravels().get(0).getSecondLocation());
                double distanceAfterSwap = countDistanceFor(locations);

                if (distanceAfterSwap < initialDistance) {
                    bestSolution.clear();
                    bestSolution = new ArrayList<>(locations);
                }
                else
                {
                    Collections.swap(locations, travelsOrder.getTravels().get(0).getFirstLocation(), travelsOrder.getTravels().get(0).getSecondLocation());
                }
            }

            temperature.decrease();
        }

        // Include additional locations that did not occur as popup during calculations.
//        if (additionalLocationsCounter < additionalLocations.size()) {
//
//        }

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
        final Random random = new Random();
        return random.nextInt(MAX_RANDOM_NUMBER) > ADDITIONAL_PICK_THRESHOLD;
    }

}
