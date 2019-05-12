package com.mjakobczyk.application;

import com.mjakobczyk.vrp.VrpDataProvider;
import com.mjakobczyk.vrp.VrpOutput;
import com.mjakobczyk.vrp.impl.def.DefaultVrpManager;
import com.mjakobczyk.vrp.VrpSolver;
import com.mjakobczyk.vrp.impl.def.DefaultVrpDataProvider;
import com.mjakobczyk.vrp.impl.def.DefaultVrpSolver;

/**
 * Application is a singleton which enables running calculations.
 */
public class Application {

    /**
     * Single instance of the class in a static context.
     */
    private static Application instance = new Application();

    /**
     * DefaultVrpManager that controls algorithm flow.
     */
    private DefaultVrpManager defaultVrpManager;

    /**
     * Run methods starts the application.
     */
    public void run() {
        System.out.println("Mock: Application run()");
        final VrpOutput vrpOutput = this.defaultVrpManager.solve();
        if (vrpOutput != null) {
            System.out.println(vrpOutput.toString());
        }
    }

    /**
     * Public getter for static class instance.
     *
     * @return
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
        final VrpDataProvider vrpDataProvider = new DefaultVrpDataProvider();
        final VrpSolver vrpSolver = new DefaultVrpSolver();
        this.defaultVrpManager = new DefaultVrpManager(vrpDataProvider, vrpSolver);
    }
}
