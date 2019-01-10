package com.electrodynamics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestOhmLaw {

    double voltage;
    double current;
    double resistance;

    OhmLaw ohmLaw = new OhmLaw();

    @Test
    public void testVoltageCalc(){
        current = 23;
        resistance = 235;
        assertEquals(ohmLaw.voltageCalc(current, resistance),
               current * resistance, 1e-6);
    }

    @Test
    public void testResistanceCalc(){
        current = 0.1;
        voltage = 12;
        assertEquals(ohmLaw.resistanceCalc(voltage, current), voltage/current , 1e-6);
    }

    @Test
    public void testCurrent(){
        voltage = 2;
        current = 12;
        assertEquals(ohmLaw.currentStrengthCalc(voltage, current), voltage / current, 1e-6);
    }
}
