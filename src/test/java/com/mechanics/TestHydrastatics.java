package com.mechanics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestHydrastatics {

    double pressure;
    double density;
    double fluidHeigth;


    Hydrastatics hydrastatics = new Hydrastatics();

    @Test
    public void testFluidHeightCalc(){
        pressure = 23;
        density = 235;
        assertEquals(hydrastatics.fluidHeightCalc(pressure, density),
                pressure / (density * hydrastatics.freeFallAcceleration), 1e-6);
    }

    @Test
    public void testDensityCalc(){
        pressure = 97;
        fluidHeigth = 0.1;
        assertEquals(hydrastatics.densityCalc(pressure, fluidHeigth),
                pressure / (fluidHeigth * hydrastatics.freeFallAcceleration), 1e-6);
    }

    @Test
    public void testPressureCalc(){
        density = 2;
        fluidHeigth = 0;
        assertEquals(hydrastatics.hydrastaticPressureCalc(density,fluidHeigth),
                density * fluidHeigth * hydrastatics.freeFallAcceleration, 1e-6);
    }
}
