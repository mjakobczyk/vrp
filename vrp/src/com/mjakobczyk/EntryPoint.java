package com.mjakobczyk;

import com.mjakobczyk.application.Application;

/**
 * Entry point of the application.
 */
public class EntryPoint {

    /**
     * main method runs application.
     * @param args from command line
     */
    public static void main(String[] args) {
        Application application = Application.getInstance();
        application.run();
    }
}
