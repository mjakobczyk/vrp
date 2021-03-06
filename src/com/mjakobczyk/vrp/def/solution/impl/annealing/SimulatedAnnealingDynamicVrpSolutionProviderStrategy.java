package com.mjakobczyk.vrp.def.solution.impl.annealing;

import com.mjakobczyk.vrp.def.model.DefaultVrpOutput;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpInputValidator;
import com.mjakobczyk.vrp.model.Location;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.annealing.model.SimulatedAnnealingTemperature;
import com.mjakobczyk.vrp.def.solution.impl.annealing.model.SimulatedAnnealingTravelOrder;
import com.mjakobczyk.vrp.model.VrpInput;
import com.mjakobczyk.vrp.model.VrpOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
     * Temperature step to be used in a simulation process.
     */
    private int step = 1;

    /**
     * Default constructor of SimulatedAnnealingDynamicVrpSolutionProviderStrategy.
     */
    public SimulatedAnnealingDynamicVrpSolutionProviderStrategy() {
        super(new DynamicVrpInputValidator());
    }

    @Override
    public Optional<VrpOutput> findOptimalRouteFor(final VrpInput vrpInput) {
        return runSimulatedAnnealingAnalysisFor(vrpInput);
    }

    protected Optional<VrpOutput> runSimulatedAnnealingAnalysisFor(final VrpInput vrpInput) {
        final List<Location> allLocations = vrpInput.getLocations();
        final Location depot = allLocations.get(0);
        final List<Location> locationsToVisit = allLocations.subList(1, allLocations.size());

        final SimulatedAnnealingTemperature temperature = new SimulatedAnnealingTemperature();
        temperature.setStep(this.step);
        final SimulatedAnnealingTravelOrder travelsOrder = new SimulatedAnnealingTravelOrder(locationsToVisit);

        List<Location> resultLocationsList = new ArrayList<>();
        resultLocationsList.add(depot);
        resultLocationsList.addAll(locationsToVisit);
        resultLocationsList.add(depot);

        List<Location> bestSolution = new ArrayList<>(resultLocationsList);
        double bestDistance = getVrpUtils().countDistanceFor(resultLocationsList);

        while (temperature.isValid()) {
            for (int i = 0; i < travelsOrder.getTravels().size(); ++i) {
                Collections.swap(allLocations, travelsOrder.getTravels().get(i).getFirstLocation(), travelsOrder.getTravels().get(i).getSecondLocation());
                double distanceAfterSwap = getVrpUtils().countDistanceFor(allLocations);

                if (distanceAfterSwap < bestDistance) {
                    bestSolution.clear();
                    bestSolution = new ArrayList<>(allLocations);
                    bestDistance = distanceAfterSwap;
                } else if (shouldAcceptWorseSolution(bestDistance, distanceAfterSwap, temperature)) {
                    Collections.swap(allLocations, travelsOrder.getTravels().get(i).getFirstLocation(), travelsOrder.getTravels().get(i).getSecondLocation());
                }
            }

            temperature.decrease();
        }


        return Optional.of(new DefaultVrpOutput(bestSolution));
    }

    protected boolean shouldAcceptWorseSolution(final double bestDistance, final double distanceAfterSwap, final SimulatedAnnealingTemperature temperature) {
        return Math.exp((bestDistance - distanceAfterSwap) / temperature.getCurrent()) < Math.random();
    }

    public void setStep(final int newStep) {
        this.step = newStep;
    }

}
