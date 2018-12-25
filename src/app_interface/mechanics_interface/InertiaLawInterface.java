package app_interface.mechanics_interface;

import java.util.Scanner;
import mechanics.InertiaLaw;
import app_interface.MyUtils;

public class InertiaLawInterface {

    public void inertia_law_interface_main(){
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Inertia Law! \n" +
                "|------------------------------------------|\n" +
                "Choose what you'd like to calculate: \n" +
                "1. mass \n" + "2. force \n" + "3. acceleration \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch : ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.inertia_law_mass_compute();
            case "2":
                this.inertia_law_force_compute();
            case "3":
                this.inertia_law_acceleration_compute();
            default: {
                System.out.println("Wrong code! Try again. \n");
                this.inertia_law_interface_main();
            }
        }
        scanner.close();
    }


    public void inertia_law_mass_compute() {

        double force_value = MyUtils.enter_double("Enter force value in meters per square second");
        double acceleration_value = MyUtils.enter_double("Enter acceleration value in meters per square second");

        InertiaLaw inertia_law = new InertiaLaw(force_value, 0.0, acceleration_value);

        MyUtils.return_computed_value("Mass", "kilogram", inertia_law.mass_computation());
    }


    public void inertia_law_acceleration_compute() {

        double force_value = MyUtils.enter_double("Enter force value in meters per square second");
        double mass_value = MyUtils.enter_double("Enter mass value in meters per square second");

        InertiaLaw inertia_law = new InertiaLaw(force_value, mass_value, 0.0);

        MyUtils.return_computed_value("Acceleration", "meter per square second", inertia_law.acceleration_computation());

    }

    public void inertia_law_force_compute() {

        double mass_value = MyUtils.enter_double("Enter mass value in meters per square second");
        double acceleration_value = MyUtils.enter_double("Enter acceleration value in meters per square second");

        InertiaLaw inertia_law = new InertiaLaw(0.0, mass_value, acceleration_value);

        MyUtils.return_computed_value("Force", "newton", inertia_law.force_computation());

    }

}
