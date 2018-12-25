package mechanics;

public class HydrastaticPressure {

    double density;
    double fluid_height;
    double hydrostatic_pressure;
    double free_fall_acceleration = 9.81;

    public HydrastaticPressure(double p, double rho, double h) {
        density = rho;
        hydrostatic_pressure = p;
        fluid_height = h;
    }

    public double hydrastatic_pressure_computation (){
        return density * fluid_height * free_fall_acceleration;
    }

    public double density_computation (){
        return hydrostatic_pressure / (fluid_height * free_fall_acceleration);
    }

    public double fluid_height_computation (){
        return hydrostatic_pressure / density * free_fall_acceleration;
    }

}
