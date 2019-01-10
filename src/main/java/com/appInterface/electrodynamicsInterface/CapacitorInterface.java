package com.appInterface.electrodynamicsInterface;

import java.util.Scanner;
import com.electrodynamics.Capacitor;
import com.appInterface.MyUtils;

// интерфейс уравнения для энергии электрического поля в коденсаторе
public class CapacitorInterface {

    public void capacitorEnergyInterfaceMain() {

        // запрашивает, какую величину пользователь хотел бы рассчитать
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Capacitor Energy! \n" +
                "|------------------------------------------|\n" +
                "Choose what you'd like to calculate: \n" +
                "1. capacitor energy \n" + "2. voltage \n" + "3. capacity \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch  (press q to quit): ");

        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.capacitorEnergyCompute();
                break;
            case "2":
                this.voltageCompute();
                break;
            case "3":
                this.capacityCompute();
                break;
            case "q":
                System.exit(0);
            default: {
                System.out.println("Wrong code! Try again. \n");
                this.capacitorEnergyInterfaceMain();
            }
        }
        scanner.close();
    }

    // запрашивает данные для расчета энергии и направляет программу к вычислению требуемой величины
    private void capacitorEnergyCompute() {

        Capacitor capacitor = new Capacitor();

        double voltageValue = MyUtils.enterDouble("Enter voltage value in volt");
        double capacityValue = MyUtils.enterDouble("Enter capacity value in farad");

        MyUtils.outputComputedValue("Capacitor energy", "joule",
                capacitor.capacitorEnergyCalc(capacityValue, voltageValue));
    }

    // запрашивает данные для расчета напряжения и направляет программу к вычислению требуемой величины
    private void voltageCompute() {

        double capacityValue = MyUtils.enterDouble("Enter capacity value in farad");
        double capacitorEnergyValue = MyUtils.enterDouble("Enter capacitor energy value in joule");

        Capacitor capacitor = new Capacitor();

        MyUtils.outputComputedValue("Voltage", "volt",
                capacitor.voltageCalc(capacitorEnergyValue, capacityValue));

    }

    // запрашивает данные для расчета емкости конденсатора и направляет программу
    // к вычислению требуемой величины
    private void capacityCompute() {

        double voltageValue = MyUtils.enterDouble("Enter voltage value in volt");
        double capacitorEnergyValue = MyUtils.enterDouble("Enter capacitor energy value in joule");

        Capacitor capacitor = new Capacitor();

        MyUtils.outputComputedValue("Capacity", "farad",
                capacitor.capacityCalc(capacitorEnergyValue, voltageValue));
    }
}
