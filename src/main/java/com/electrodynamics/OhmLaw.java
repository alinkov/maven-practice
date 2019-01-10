package com.electrodynamics;

// вычисления по закону Ома для участка цепи

public class OhmLaw {

    public double currentStrengthCalc(final double voltage, final double resistance) {
        return voltage / resistance;
    }

    public double voltageCalc(final double currentStrength, final double resistance) {
        return currentStrength * resistance;
    }

    public double resistanceCalc(final double voltage, final double currentStrength) {
        return voltage / currentStrength;
    }

}
