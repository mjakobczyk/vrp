package com.mjakobczyk.application;

/**
 * Application is a singleton which enables running calculations.
 */
public class Application {

    /**
     * Single instance of the class in a static context.
     */
    private static Application instance = new Application();

    /**
     * Run methods starts the application.
     */
    public void run() {
        System.out.println("Mock: Application run()");
    }

    /**
     * Public getter for static class instance.
     * @return
     */
    public static Application getInstance() {
        return instance;
    }

    /**
     * Private empty constructor not to let create more than one
     * instance of the class.
     */
    private Application() {}
}
