package electrodynamics;

public class OhmLaw {

    double voltage;
    double current_strength;
    double resistance;

    public OhmLaw(double u, double i, double r){
        voltage = u;
        current_strength = i;
        resistance = r;
    }

    public double current_strength_computation() {return voltage / resistance;}

    public double voltage_computation() {return current_strength * resistance;}

    public double resistance_computation() {return voltage / current_strength;}

}
