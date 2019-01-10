package com.electrodynamics;

import java.lang.Math;

// вычисления по уравнению для энергии электрического поля в конденсаторе

public class Capacitor {


    public double capacitorEnergyCalc(final double capacity, final double voltage) {
        return capacity * Math.pow(voltage, 2) / 2;
    }

    public double capacityCalc(final double capacitorEnergy, final double voltage) {
        return 2 * capacitorEnergy / Math.pow(voltage, 2);
    }

    public double voltageCalc(final double capacitorEnergy, final double capacity) {
        return Math.pow((2 * capacitorEnergy / capacity), 0.5);
    }

}
