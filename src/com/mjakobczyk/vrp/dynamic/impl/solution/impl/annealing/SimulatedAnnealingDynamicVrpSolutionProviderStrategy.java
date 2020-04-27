package com.mjakobczyk.vrp.dynamic.impl.solution.impl.annealing;

import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpInputValidator;
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

    /**
     * Default constructor of SimulatedAnnealingDynamicVrpSolutionProviderStrategy.
     */
    public SimulatedAnnealingDynamicVrpSolutionProviderStrategy() {
        super(new DynamicVrpInputValidator());
    }

    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        if (getVrpInputValidator().isValid(vrpInput)) {
            return runSimulatedAnnealingAnalysisFor(vrpInput);
        }

        return Optional.empty();
    }

    protected Optional<VrpOutput> runSimulatedAnnealingAnalysisFor(final VrpInput vrpInput) {
        final DynamicVrpInput dynamicVrpInput = (DynamicVrpInput) vrpInput;
        final List<Location> allLocations = dynamicVrpInput.getLocations();
        final List<Location> additionalLocations = dynamicVrpInput.getAdditionalLocations();
        final Location depot = allLocations.get(0);
        final List<Location> locationsToVisit = allLocations.subList(1, allLocations.size());

        final SimulatedAnnealingTemperature temperature = new SimulatedAnnealingTemperature();
        final SimulatedAnnealingTravelOrder travelsOrder = new SimulatedAnnealingTravelOrder(locationsToVisit);

        List<Location> resultLocationsList = new ArrayList<>();
        resultLocationsList.add(depot);
        resultLocationsList.addAll(locationsToVisit);
        resultLocationsList.add(depot);

        List<Location> bestSolution = new ArrayList<>(resultLocationsList);
        int additionalLocationsCounter = 0;
        double bestDistance = getVrpUtils().countDistanceFor(allLocations);

        LOG.log(Level.INFO, "Initial distance for " + allLocations.size() + " locations = " + bestDistance);

        while (temperature.isValid()) {
            if (additionalLocationsCounter < additionalLocations.size() && shouldAdditionalLocationAppear()) {
                final Location additionalLocation = additionalLocations.get(additionalLocationsCounter++);
                allLocations.add(additionalLocation);
                resultLocationsList.add(additionalLocation);
                final int resultLocationListSize = resultLocationsList.size();
                Collections.swap(resultLocationsList, resultLocationListSize - 2, resultLocationListSize - 1);

                bestSolution.clear();
                bestSolution = new ArrayList<>(resultLocationsList);
                travelsOrder.updateTravelsOrder(allLocations);
            }

            for (int i = 0; i < travelsOrder.getTravels().size(); ++i) {
                Collections.swap(allLocations, travelsOrder.getTravels().get(i).getFirstLocation(), travelsOrder.getTravels().get(i).getSecondLocation());
                double distanceAfterSwap = getVrpUtils().countDistanceFor(allLocations);

                if (distanceAfterSwap < bestDistance) {
                    bestSolution.clear();
                    bestSolution = new ArrayList<>(resultLocationsList);
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

    protected boolean shouldAdditionalLocationAppear() {
        return random.nextInt(MAX_RANDOM_NUMBER) > ADDITIONAL_PICK_THRESHOLD;
    }

}
