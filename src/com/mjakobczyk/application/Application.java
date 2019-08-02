package com.mjakobczyk.application;

import com.mjakobczyk.vrp.VrpDataProvider;
import com.mjakobczyk.vrp.VrpSolutionProvider;
import com.mjakobczyk.vrp.VrpSolver;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpDataProvider;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpSolutionProvider;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpSolver;
import com.mjakobczyk.vrp.model.VrpOutput;

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
    private static final Logger log = Logger.getLogger(String.valueOf(Application.class));

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
    public void run() {
        log.log(Level.INFO, "Running Application...");

        final Optional<VrpOutput> optionalVrpOutput = this.vrpSolver.solve();

        if (optionalVrpOutput.isEmpty()) {
            log.log(Level.SEVERE, "VrpOutput has not been collected.");
        } else {
            log.log(Level.INFO, "VrpOutput has been obtained successfully.");
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
        // TODO: implement mechanism for choosing type of provider and solver
//        final VrpDataProvider vrpDataProvider = new DefaultVrpDataProvider();
        final VrpDataProvider vrpDataProvider = new DynamicVrpDataProvider();
//        final VrpSolutionProvider vrpSolutionProvider = new DefaultVrpSolutionProvider();
        final VrpSolutionProvider vrpSolutionProvider = new DynamicVrpSolutionProvider();

//        this.vrpSolver = new DefaultVrpSolver(vrpDataProvider, vrpSolutionProvider);
        this.vrpSolver = new DynamicVrpSolver(vrpDataProvider, vrpSolutionProvider);
    }
}
