package com.mjakobczyk.vrp.def.solution.impl.annealing.model;

/**
 * SimulatedAnnealingTemperature describes behaviour of temperature in Simulated Annealing
 * Algorithm. Default values can be overridden by using Fluent Builder constructor.
 */
public class SimulatedAnnealingTemperature {

    private static final int DEFAULT_INITIAL_VALUE = 1000;
    private static final int DEFAULT_MINIMUM_VALUE = 0;
    private static final int DEFAULT_STEP_VALUE = 1;

    /**
     * Current value of temperature.
     */
    private int current;

    /**
     * Minimum value of temperature until when algorithm should proceed.
     */
    private int minimum;

    /**
     * Step value using which temperature should be decreased after every
     * iteration of the algorithm.
     */
    private int step;

    /**
     * Default constructor of SimulatedAnnealingTemperature.
     */
    public SimulatedAnnealingTemperature() {
        this.current = DEFAULT_INITIAL_VALUE;
        this.minimum = DEFAULT_MINIMUM_VALUE;
        this.step = DEFAULT_STEP_VALUE;
    }

    public void setStep(final int step) {
        this.step = step;
    }

    /**
     * Constructor of SimulatedAnnealingTemperature using Fluent Builder pattern.
     *
     * @param builder containing values
     */
    public SimulatedAnnealingTemperature(final Builder builder) {
        this.current = builder.current;
        this.minimum = builder.minimum;
        this.step = builder.step;
    }

    /**
     * Checks if current temperature is still valid.
     *
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return current >= minimum;
    }

    /**
     * Decrease temperature by a single step.
     */
    public void decrease() {
        current -= step;
    }

    /**
     * Getter of current temperature.
     *
     * @return current temperature
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Getter of minimum temperature.
     *
     * @return minimum temperature
     */
    public int getMinimum() {
        return minimum;
    }

    /**
     * Getter of step value.
     *
     * @return step value
     */
    public int getStep() {
        return step;
    }

    /**
     * Fluent Builder pattern for initializing object values.
     */
    public static class Builder {

        private int current;
        private int minimum;
        private int step;

        /**
         * Setter of Builder current temperature.
         *
         * @param current temperature
         * @return
         */
        public Builder current(final int current) {
            this.current = current;
            return this;
        }

        /**
         * Setter of Builder minimum temperature.
         *
         * @param minimum value
         * @return Builder
         */
        public Builder minimum(final int minimum) {
            this.minimum = minimum;
            return this;
        }

        /**
         * Setter of Builder step value.
         *
         * @param step value
         * @return Builder
         */
        public Builder step(final int step) {
            this.step = step;
            return this;
        }

        /**
         * Final build method.
         *
         * @return SimulatedAnnealingTemperature class instance
         */
        public SimulatedAnnealingTemperature build() {
            return new SimulatedAnnealingTemperature(this);
        }
    }
}
