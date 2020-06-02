package com.mjakobczyk.vrp.def.solution.impl.antcolony.model;

import com.mjakobczyk.vrp.def.solution.impl.antcolony.AntColonyVrpSolutionProviderStrategy;

/**
 * AntColonyParameters stores all configuration values for
 * {@link AntColonyVrpSolutionProviderStrategy}.
 */
public class AntColonyParameters {

    /**
     * c indicates the original number of trails.
     */
    private double c;

    /**
     * alpha controls the pheromone importance.
     */
    private double alpha;

    /**
     * beta controls the distance priority. In general it's value should be
     * greater than alpha for the best results.
     */
    private double beta;

    /**
     * evaporation show the percent how much the pheromone is evaporating
     * in every iteration.
     */
    private double evaporation;

    /**
     * Q provides information about the total amount of pheromone left
     * on the trial by each ant.
     */
    private double Q;

    /**
     * antFactor indicates on how many ants are going to be used per city.
     */
    private double antFactor;

    /**
     * randomFactor indicates randomness in algorithm in order to give
     * a chance to get out of local minimums.
     */
    private double randomFactor;

    /**
     * iterations of algorithm.
     */
    private int iterations;

    /**
     * Default constructor of AntColonyParameters.
     */
    public AntColonyParameters() {
        this.c = 1.0d;
        this.alpha = 1d;
        this.beta = 5d;
        this.evaporation = 0.5;
        this.Q = 500d;
        this.antFactor = 0.8d;
        this.randomFactor = 0.01d;
        this.iterations = 1000; // by default was 100
    }

    /**
     * Constructor for AntColonyParameters that takes all parameters values.
     *
     * @param c            value
     * @param alpha        value
     * @param beta         value
     * @param evaporation  value
     * @param q            value
     * @param antFactor    value
     * @param randomFactor value
     */
    public AntColonyParameters(final double c, final double alpha, final double beta, final double evaporation,
                               final double q, final double antFactor, final double randomFactor, final int iterations) {
        this.c = c;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporation = evaporation;
        this.Q = q;
        this.antFactor = antFactor;
        this.randomFactor = randomFactor;
        this.iterations = iterations;
    }

    /**
     * Getter for c parameter.
     *
     * @return c value
     */
    public double getC() {
        return c;
    }

    /**
     * Getter for alpha parameter.
     *
     * @return alpha value
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Getter for beta parameter.
     *
     * @return beta value
     */
    public double getBeta() {
        return beta;
    }

    /**
     * Getter for evaporation parameter.
     *
     * @return evaporation value
     */
    public double getEvaporation() {
        return evaporation;
    }

    /**
     * Getter for Q parameter.
     *
     * @return Q value
     */
    public double getQ() {
        return Q;
    }

    /**
     * Getter for antFactor parameter.
     *
     * @return antFactor value
     */
    public double getAntFactor() {
        return antFactor;
    }

    /**
     * Getter for random factor.
     *
     * @return randomFactor value
     */
    public double getRandomFactor() {
        return randomFactor;
    }

    /**
     * Getter for iterations.
     *
     * @return iterations value
     */
    public int getIterations() {
        return iterations;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setEvaporation(double evaporation) {
        this.evaporation = evaporation;
    }

    public void setQ(double q) {
        Q = q;
    }

    public void setAntFactor(double antFactor) {
        this.antFactor = antFactor;
    }

    public void setRandomFactor(double randomFactor) {
        this.randomFactor = randomFactor;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
