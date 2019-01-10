package com.mechanics;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestInertiaLaw {

    double force;
    double mass;
    double acceleration;

    InertiaLaw inertiaLaw = new InertiaLaw();

    @Test
    public void testAccelerationCalc(){
        force = 23;
        mass = 235;
        assertEquals(inertiaLaw.accelerationCalc(force, mass), force/mass, 1e-6);
    }

    @Test
    public void testMassCalc(){
        force = 0;
        acceleration = 12;
        assertEquals(inertiaLaw.massCalc(force, acceleration), force/acceleration, 1e-6);
    }

    @Test
    public void testForceCalc(){
        mass = 2;
        acceleration = 0;
        assertEquals(inertiaLaw.forceCalc(mass,acceleration), mass * acceleration, 1e-6);
    }
}
