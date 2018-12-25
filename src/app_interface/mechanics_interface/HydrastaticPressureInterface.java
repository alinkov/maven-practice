package app_interface.mechanics_interface;

import java.util.Scanner;

import mechanics.HydrastaticPressure;
import app_interface.MyUtils;

public class HydrastaticPressureInterface {

    public void hydrastatic_pressure_interface_main(){
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Hydrastatic Pressure! \n" +
                "|------------------------------------------|\n" +
                "Choose what you'd like to calculate: \n" +
                "1. pressure \n" + "2. density \n" + "3. fluid_height \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch : ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.hydrastatic_pressure_pressure_compute();
            case "2":
                this.hydrastatic_pressure_density_compute();
            case "3":
                this.hydrastatic_pressure_fluid_height_compute();
            default: {
                System.out.println("Wrong code! Try again. \n");
                this.hydrastatic_pressure_interface_main();
            }
        }
        scanner.close();
    }


    public void hydrastatic_pressure_pressure_compute() {

        double density_value = MyUtils.enter_double("Enter density value in kilogram per cubic meter");
        double fluid_height_value = MyUtils.enter_double("Enter fluid height value in meter");

        HydrastaticPressure hydrastatic_pressure = new HydrastaticPressure(0.0, density_value, fluid_height_value);

        MyUtils.return_computed_value("Hydrastatic pressure", "pascal", hydrastatic_pressure.hydrastatic_pressure_computation());

    }


    public void hydrastatic_pressure_density_compute() {

        double pressure_value = MyUtils.enter_double("Enter hydrstatic pressure value in pascal");
        double fluid_height_value = MyUtils.enter_double("Enter fluid height value in meter");

        HydrastaticPressure hydrastatic_pressure = new HydrastaticPressure(pressure_value, 0.0, fluid_height_value);

        MyUtils.return_computed_value("Density", "kilogram per cubic meter", hydrastatic_pressure.density_computation());

    }

    public void hydrastatic_pressure_fluid_height_compute() {

        double pressure = MyUtils.enter_double("Enter hydrastatic pressure value in pascal");
        double density = MyUtils.enter_double("Enter density value in kilogram per cubic second");

        HydrastaticPressure hydrastatic_pressure = new HydrastaticPressure(pressure, density, 0.0);

        MyUtils.return_computed_value("Fluid height", "meter", hydrastatic_pressure.fluid_height_computation());

    }

}