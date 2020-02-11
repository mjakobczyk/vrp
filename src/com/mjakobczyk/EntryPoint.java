package com.mjakobczyk;

import com.mjakobczyk.vrp.Application;

/**
 * Entry point of the application.
 */
public class EntryPoint {

    /**
     * main method runs application.
     *
     * @param args from command line
     */
    public static void main(final String[] args) {
        final Application application = Application.getInstance();
        application.run(args);
    }

}
