package app_interface;

import java.util.Scanner;
import app_interface.mechanics_interface.InertiaLawInterface;
import app_interface.mechanics_interface.HydrastaticPressureInterface;
import app_interface.electrodynamics_interface.OhmLawInterface;
import app_interface.electrodynamics_interface.CapacitorEnergyInterface;

public class MainInterface {

    public void choosing_physics_branch() {
        System.out.println("|------------------------------------------|\n" +
                "Physics computations, school level \n" +
                "|------------------------------------------|\n" +
                "Choose the branch of physics with typing its number: \n" +
                "1. mechanics \n" + "2. electrodynamics \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch : ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.choose_law_mech();
            case "2":
                this.choose_law_electro();
            default: {
                System.out.println("Wrong code! Try again. \n");
                this.choosing_physics_branch();
            }
        }
        scanner.close();
    }

    public void choose_law_mech () {
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Mechanics! \n" +
                "|------------------------------------------|\n" +
                "Choose the law of mechanics with typing its number: \n" +
                "1. inertia law \n" + "2. hydrastatic pressiure \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch : ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                InertiaLawInterface inertia_instance = new InertiaLawInterface();
                inertia_instance.inertia_law_interface_main();
            case "2":
                HydrastaticPressureInterface hydrastatic_pressure_instance = new HydrastaticPressureInterface();
                hydrastatic_pressure_instance.hydrastatic_pressure_interface_main();
        }
        scanner.close();
    }


    public void choose_law_electro () {
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Electrodynamics! \n" +
                "|------------------------------------------|\n" +
                "Choose the law of mechanics with typing its number: \n" +
                "1. Ohm's law \n" + "2. capacitor energy \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch : ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                OhmLawInterface ohm_instance = new OhmLawInterface();
                ohm_instance.ohm_law_interface_main();
            case "2":
                CapacitorEnergyInterface capacitor_instance = new CapacitorEnergyInterface();
                capacitor_instance.capacitor_energy_interface_main();
        }
        scanner.close();
    }
}