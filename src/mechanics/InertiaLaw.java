package mechanics;

public class InertiaLaw {

    double force;
    double mass;
    double acceleration;

    public InertiaLaw(double f, double m, double a){
        force = f;
        mass = m;
        acceleration = a;
    }

    public double force_computation () {return mass * acceleration;};

    public double mass_computation () {return force / acceleration;};

    public double acceleration_computation () {return force / mass;};
}
