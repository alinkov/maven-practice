package app_interface.electrodynamics_interface;

import java.util.Scanner;
import electrodynamics.OhmLaw;
import app_interface.MyUtils;

public class OhmLawInterface {

    public void ohm_law_interface_main(){
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Ohm's Law! \n" +
                "|------------------------------------------|\n" +
                "Choose what you'd like to calculate: \n" +
                "1. voltage \n" + "2. current_strength \n" + "3. resistance \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch : ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.ohm_law_voltage_compute();
            case "2":
                this.ohm_law_current_strength_compute();
            case "3":
                this.ohm_law_resistance_compute();
            default: {
                System.out.println("Wrong code! Try again. \n");
                this.ohm_law_interface_main();
            }
        }
        scanner.close();
    }


    public void ohm_law_voltage_compute() {

        double current_strength_value = MyUtils.enter_double("Enter current strength value in ampere");
        double resistance_value = MyUtils.enter_double("Enter resistance value in ohm");

        OhmLaw ohm_law = new OhmLaw (0.0, current_strength_value, resistance_value);

        MyUtils.return_computed_value("Voltage", "volt", ohm_law.voltage_computation());
    }


    public void ohm_law_current_strength_compute() {

        double voltage_value = MyUtils.enter_double("Enter voltage value in volt");
        double resistance_value = MyUtils.enter_double("Enter resistance value in ohm");

        OhmLaw ohm_law = new OhmLaw (voltage_value, 0.0, resistance_value);

        MyUtils.return_computed_value("Current strengh", "ampere", ohm_law.current_strength_computation());

    }

    public void ohm_law_resistance_compute() {

        double voltage_value = MyUtils.enter_double("Enter voltage value in volt");
        double current_strength_value = MyUtils.enter_double("Enter current strength value in ampere");

        OhmLaw ohm_law = new OhmLaw (voltage_value, current_strength_value, 0.0);

        MyUtils.return_computed_value("Resistance", "ohm", ohm_law.resistance_computation());
    }

}

