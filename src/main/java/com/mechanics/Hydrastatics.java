package com.mechanics;

// вычисления по уравнению для гидростатического далвения

public class Hydrastatics {

    public double freeFallAcceleration = 9.81;

    public double hydrastaticPressureCalc(final double density, final double fluidHeight){
        return density * fluidHeight * freeFallAcceleration;
    }

    public double densityCalc(final double hydrostaticPressure, final double fluidHeight){
        return hydrostaticPressure / (fluidHeight * freeFallAcceleration);
    }

    public double fluidHeightCalc(final double hydrostaticPressure, final double density){
        return hydrostaticPressure / (density * freeFallAcceleration);
    }

}
