package com.electrodynamics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCapacitor {

    double voltage;
    double energy;
    double capacity;

    Capacitor capacitor = new Capacitor();

    @Test
    public void testCapacityCalc(){
        voltage = 23;
        energy = 235;
        assertEquals(capacitor.capacityCalc(energy, voltage),
               2 * energy/Math.pow(voltage, 2), 1e-6);
    }

    @Test
    public void testEnergyCalc(){
        capacity = 0;
        voltage = 12;
        assertEquals(capacitor.capacitorEnergyCalc(capacity, voltage),
                capacity * Math.pow(voltage, 2)/2, 1e-6);
    }

    @Test
    public void testVoltage(){
        energy = 2;
        capacity = 12;
        assertEquals(capacitor.voltageCalc(energy, capacity),
                Math.pow((2 * energy / capacity), 0.5), 1e-6);
    }
}
