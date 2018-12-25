package electrodynamics;

import java.lang.Math;

public class CapacitorEnergy {

    double capacitor_energy;
    double voltage;
    double capacity;

    public CapacitorEnergy (double e, double c, double u) {
        capacitor_energy = e;
        capacity = c;
        voltage = u;
    };

    public double capacitor_energy_computation () {return capacity * Math.pow(voltage, 2) / 2; }

    public double capacity_computation () {return 2 * capacitor_energy / Math.pow(voltage, 2);}

    public double voltage_computation () {return Math.pow((2 * capacitor_energy / capacity), 0.5);}

}
