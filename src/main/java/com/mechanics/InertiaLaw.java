package com.mechanics;

// вычисления по второму закону Ньютона

public class InertiaLaw {

    public double forceCalc(final double mass, final double acceleration) {
        return mass * acceleration;
    }

    public double massCalc(final double force, final double acceleration) {
        return force / acceleration;
    }

    public double accelerationCalc(final double force, final double mass) {
        return force / mass;
    }
}
