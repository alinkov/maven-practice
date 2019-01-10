package com.appInterface.electrodynamicsInterface;

import java.util.Scanner;
import com.electrodynamics.OhmLaw;
import com.appInterface.MyUtils;

// интерфейс закона Ома
public class OhmLawInterface {

    public void ohmLawInterfaceMain(){
        // запрашивает, какую величины пользователь хотел бы посчитать
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Ohm's Law! \n" +
                "|------------------------------------------|\n" +
                "Choose what you'd like to calculate: \n" +
                "1. voltage \n" + "2. current_strength \n" + "3. resistance \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch  (press q to quit): ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.ohmLawVoltageCompute();
                break;
            case "2":
                this.ohmLawCurrentStrengthCompute();
                break;
            case "3":
                this.ohmLawResistanceCompute();
                break;
            case "q":
                System.exit(0);
            default: {
                System.out.println("Wrong code! Try again. \n");
                this.ohmLawInterfaceMain();
            }
        }
        scanner.close();
    }

    // запрашивает данные для расчета напряжения и отправляет программу на вычисление этой величины
    private void ohmLawVoltageCompute() {

        double currentStrengthValue = MyUtils.enterDouble("Enter current strength value in ampere");
        double resistanceValue = MyUtils.enterDouble("Enter resistance value in ohm");

        OhmLaw ohmLaw = new OhmLaw ();

        MyUtils.outputComputedValue("Voltage", "volt",
                ohmLaw.voltageCalc(currentStrengthValue, resistanceValue));
    }

    // запрашивает данные для расчета силы тока и отправляет программу на вычисление этой величины
    private void ohmLawCurrentStrengthCompute() {

        double voltageValue = MyUtils.enterDouble("Enter voltage value in volt");
        double resistanceValue = MyUtils.enterDouble("Enter resistance value in ohm");

        OhmLaw ohmLaw = new OhmLaw ();

        MyUtils.outputComputedValue("Current strengh", "ampere",
                ohmLaw.currentStrengthCalc(voltageValue, resistanceValue));

    }

    // запрашивает данные для расчета электрического сопротивления
    // и отправляет программу на вычисление этой величины
    private void ohmLawResistanceCompute() {

        double voltageValue = MyUtils.enterDouble("Enter voltage value in volt");
        double currentStrengthValue = MyUtils.enterDouble("Enter current strength value in ampere");

        OhmLaw ohmLaw = new OhmLaw ();

        MyUtils.outputComputedValue("Resistance", "ohm",
                ohmLaw.resistanceCalc(voltageValue, currentStrengthValue));
    }

}

