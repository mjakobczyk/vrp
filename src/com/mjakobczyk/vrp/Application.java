package com.mjakobczyk.vrp;

import com.mjakobczyk.vrp.def.DefaultVrpDataProvider;
import com.mjakobczyk.vrp.def.DefaultVrpDrawer;
import com.mjakobczyk.vrp.def.DefaultVrpSolutionProvider;
import com.mjakobczyk.vrp.def.DefaultVrpSolver;
import com.mjakobczyk.vrp.def.data.VrpFileDataProvider;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.def.solution.impl.tabu.TabuSearchDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpDataProvider;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpSolutionProvider;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpSolver;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.AntColonyOptimizedDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.data.impl.MendeleyDynamicVrpFileDataProvider;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.VrpOutput;
import com.mjakobczyk.vrp.service.VrpDataProvider;
import com.mjakobczyk.vrp.service.VrpDrawer;
import com.mjakobczyk.vrp.service.VrpSolutionProvider;
import com.mjakobczyk.vrp.service.VrpSolver;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application is a singleton which enables running calculations.
 */
public final class Application {

    /**
     * Application logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(Application.class));

    /**
     * Single instance of the class in a static context.
     */
    private static final Application instance = new Application();

    /**
     * Chooses which algorithm to run.
     */
    private static final int SOLVER_CHOICE = 0;

    /**
     * Run methods starts the application.
     */
    public void run(final String[] args) {
        LOG.log(Level.INFO, "Running Application...");

        final String inputFilePath;
        if (args.length != 1) {
            LOG.log(Level.INFO, "Application can take only one argument. Using default input file from resources.");
            inputFilePath = StringUtils.EMPTY;
        } else {
            LOG.log(Level.INFO, "Input file path passed: {0}", args[0]);
            inputFilePath = args[0];
        }

        final VrpSolver vrpSolver = instantiateVrpSolver(SOLVER_CHOICE, inputFilePath);
        final Optional<VrpOutput> optionalVrpOutput = vrpSolver.solve();

        if (optionalVrpOutput.isEmpty()) {
            LOG.log(Level.SEVERE, "VrpOutput has not been collected.");
        } else {
            LOG.log(Level.INFO, "VrpOutput has been obtained successfully.");
            final VrpDrawer vrpDrawer = new DefaultVrpDrawer();
            final DynamicVrpOutput dynamicVrpOutput = (DynamicVrpOutput) optionalVrpOutput.get();
            vrpDrawer.draw(dynamicVrpOutput);
        }
    }

    /**
     * Public getter for static class instance.
     *
     * @return Application instance
     */
    public static Application getInstance() {
        return instance;
    }

    /**
     * Private empty constructor not to let create more than one
     * instance of the class.
     */
    private Application() {}

    private VrpSolver instantiateVrpSolver(final int choice, final String inputFilePath) {
        return switch (choice) {
            case 1 -> createSimulatedAnnealingDvrpSolution(inputFilePath);
            case 2 -> createAntColonyDvrpSolution(inputFilePath);
            default -> createDefaultVrpSolution();
        };
    }

    private VrpSolver createDefaultVrpSolution() {
        final VrpDataProvider vrpDataProvider = new DefaultVrpDataProvider();
        final VrpSolutionProvider vrpSolutionProvider = new DefaultVrpSolutionProvider();
        return new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);
    }

    private VrpSolver createSimulatedAnnealingDvrpSolution(final String inputFilePath) {
        final VrpFileDataProvider fileDataProvider = new MendeleyDynamicVrpFileDataProvider();
        final VrpDataProvider vrpDataProvider = new DynamicVrpDataProvider(inputFilePath, fileDataProvider);
        final VrpSolutionProviderStrategy strategy = new TabuSearchDynamicVrpSolutionProviderStrategy();
        final VrpSolutionProvider vrpSolutionProvider = new DynamicVrpSolutionProvider(strategy);
        return new DynamicVrpSolver(vrpDataProvider, vrpSolutionProvider);
    }

    private VrpSolver createAntColonyDvrpSolution(final String inputFilePath) {
        final VrpFileDataProvider fileDataProvider = new MendeleyDynamicVrpFileDataProvider();
        final VrpDataProvider vrpDataProvider = new DynamicVrpDataProvider(inputFilePath, fileDataProvider);
        final VrpSolutionProviderStrategy strategy = new AntColonyOptimizedDynamicVrpSolutionProviderStrategy(new AntColonyParameters());
        final VrpSolutionProvider vrpSolutionProvider = new DynamicVrpSolutionProvider(strategy);
        return new DynamicVrpSolver(vrpDataProvider, vrpSolutionProvider);
    }
}
