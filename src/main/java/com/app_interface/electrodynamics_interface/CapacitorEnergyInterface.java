package main.java.com.app_interface.electrodynamics_interface;

import java.util.Scanner;
import main.java.com.electrodynamics.CapacitorEnergy;
import main.java.com.app_interface.MyUtils;

public class CapacitorEnergyInterface {

    public void capacitor_energy_interface_main(){
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Capacitor Energy! \n" +
                "|------------------------------------------|\n" +
                "Choose what you'd like to calculate: \n" +
                "1. capacitor energy \n" + "2. voltage \n" + "3. capacity \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch : ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.capacitor_energy_compute();
            case "2":
                this.voltage_compute();
            case "3":
                this.capacity_compute();
            default: {
                System.out.println("Wrong code! Try again. \n");
                this.capacitor_energy_interface_main();
            }
        }
        scanner.close();
    }


    public void capacitor_energy_compute() {

        double voltage_value = MyUtils.enter_double("Enter voltage value in volt");
        double capacity_value = MyUtils.enter_double("Enter capacity value in farad");

        CapacitorEnergy capacitor_energy = new CapacitorEnergy (0.0, capacity_value, voltage_value);

        MyUtils.return_computed_value("Capacitor energy", "joule", capacitor_energy.capacitor_energy_computation());
    }


    public void voltage_compute() {

        double capacity_value = MyUtils.enter_double("Enter capacity value in farad");
        double capacitor_energy_value = MyUtils.enter_double("Enter capacitor energy value in joule");

         CapacitorEnergy capacitor_energy = new CapacitorEnergy (capacitor_energy_value, capacity_value, 0.0);

        MyUtils.return_computed_value("Voltage", "volt", capacitor_energy.voltage_computation());

    }

    public void capacity_compute() {

        double voltage_value = MyUtils.enter_double("Enter voltage value in volt");
        double capacitor_energy_value = MyUtils.enter_double("Enter capacitor energy value in joule");

        CapacitorEnergy capacitor_energy = new CapacitorEnergy (capacitor_energy_value, 0.0 , voltage_value);

        MyUtils.return_computed_value("Capacity", "farad", capacitor_energy.capacity_computation());
    }
}
