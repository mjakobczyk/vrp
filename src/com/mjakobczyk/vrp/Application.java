package com.mjakobczyk.vrp;

import com.mjakobczyk.vrp.def.impl.DefaultVrpDataProvider;
import com.mjakobczyk.vrp.def.impl.DefaultVrpSolutionProvider;
import com.mjakobczyk.vrp.def.impl.DefaultVrpSolver;
import com.mjakobczyk.vrp.def.impl.data.VrpFileDataProvider;
import com.mjakobczyk.vrp.def.impl.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpDataProvider;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpSolutionProvider;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpSolver;
import com.mjakobczyk.vrp.dynamic.impl.data.impl.DynamicVrpFileDataProvider;
import com.mjakobczyk.vrp.def.impl.solution.impl.annealing.SimulatedAnnealingDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.dynamic.impl.solution.impl.antcolony.AntColonyOptimizedDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.model.VrpOutput;
import com.mjakobczyk.vrp.service.VrpDataProvider;
import com.mjakobczyk.vrp.service.VrpSolutionProvider;
import com.mjakobczyk.vrp.service.VrpSolver;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application is a singleton which enables running calculations.
 */
public class Application {

    /**
     * Application logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(Application.class));

    /**
     * Single instance of the class in a static context.
     */
    private static Application instance = new Application();

    /**
     * DefaultVrpSolver that controls algorithm flow.
     */
    private VrpSolver vrpSolver;

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

        instantiateAntColonyDvrpSolution(inputFilePath);

        final Optional<VrpOutput> optionalVrpOutput = this.vrpSolver.solve();

        if (optionalVrpOutput.isEmpty()) {
            LOG.log(Level.SEVERE, "VrpOutput has not been collected.");
        } else {
            LOG.log(Level.INFO, "VrpOutput has been obtained successfully.");
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
    private Application() {

    }

    private void instantiateDefaultVrpSolution() {
        final VrpDataProvider vrpDataProvider = new DefaultVrpDataProvider();
        final VrpSolutionProvider vrpSolutionProvider = new DefaultVrpSolutionProvider();
        this.vrpSolver = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);
    }

    private void instantiateSimulatedAnnealingDvrpSolution(final String inputFilePath) {
        final VrpFileDataProvider fileDataProvider = new DynamicVrpFileDataProvider();
        final VrpDataProvider vrpDataProvider = new DynamicVrpDataProvider(inputFilePath, fileDataProvider);
        final VrpSolutionProviderStrategy strategy = new SimulatedAnnealingDynamicVrpSolutionProviderStrategy();
        final VrpSolutionProvider vrpSolutionProvider = new DynamicVrpSolutionProvider(strategy);
        this.vrpSolver = new DynamicVrpSolver(vrpDataProvider, vrpSolutionProvider);
    }

    private void instantiateAntColonyDvrpSolution(final String inputFilePath) {
        final VrpFileDataProvider fileDataProvider = new DynamicVrpFileDataProvider();
        final VrpDataProvider vrpDataProvider = new DynamicVrpDataProvider(inputFilePath, fileDataProvider);
        final VrpSolutionProviderStrategy strategy = new AntColonyOptimizedDynamicVrpSolutionProviderStrategy();
        final VrpSolutionProvider vrpSolutionProvider = new DynamicVrpSolutionProvider(strategy);
        this.vrpSolver = new DynamicVrpSolver(vrpDataProvider, vrpSolutionProvider);
    }
}
