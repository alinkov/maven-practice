package com.appInterface;

import java.util.Scanner;
import com.appInterface.mechanicsInterface.InertiaLawInterface;
import com.appInterface.mechanicsInterface.HydrastaticsInterface;
import com.appInterface.electrodynamicsInterface.OhmLawInterface;
import com.appInterface.electrodynamicsInterface.CapacitorInterface;

// корневой класс интерфейса приложения

public class MainInterface {

    // выбор раздела физики путем ввода соответствующего значения в консоль
    public void choosingPhysicsBranch() {
        System.out.println("|------------------------------------------|\n" +
                "Physics computations, school level \n" +
                "|------------------------------------------|\n" +
                "Choose the branch of physics with typing its number: \n" +
                "1. mechanics \n" + "2. electrodynamics \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch (press q to quit): ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.choosingLawMech();
                break;
            case "2":
                this.choosingLawElectro();
                break;
            case "q":
                System.exit(0);
            default: {
                System.out.println("\n Wrong value! Try again. \n");
                this.choosingPhysicsBranch();
            }
        }
        scanner.close();
    }

    // вызывается в случаае выбора раздела "механика"
    // и предоставляет на выбор одно из двух уравнений механики
    private void choosingLawMech() {
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Mechanics! \n" +
                "|------------------------------------------|\n" +
                "Choose the law of mechanics with typing its number: \n" +
                "1. inertia law \n" + "2. hydrastatic pressiure \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch (press q to quit): ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                InertiaLawInterface inertiaInstance = new InertiaLawInterface();
                inertiaInstance.inertiaLawInterfaceMain();
                break;
            case "2":
                HydrastaticsInterface hydrastaticPressureInstance = new HydrastaticsInterface();
                hydrastaticPressureInstance.hydrastaticPressureInterfaceMain();
                break;
            case "q":
                System.exit(0);
            default: {
                System.out.println("\n Wrong value! Try again. \n");
                this.choosingPhysicsBranch();
            }
        }
        scanner.close();
    }

    // вызывается в случаае выбора раздела "электродинамика"
    // и предоставляет на выбор одно из двух уравнений электродинамики
    private void choosingLawElectro() {
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Electrodynamics! \n" +
                "|------------------------------------------|\n" +
                "Choose the law of mechanics with typing its number: \n" +
                "1. Ohm's law \n" + "2. capacitor energy \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch (press q to quit): ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                OhmLawInterface ohmInstance = new OhmLawInterface();
                ohmInstance.ohmLawInterfaceMain();
                break;
            case "2":
                CapacitorInterface capacitorInstance = new CapacitorInterface();
                capacitorInstance.capacitorEnergyInterfaceMain();
                break;
            case "q":
                System.exit(0);
            default: {
                System.out.println("\n Wrong value! Try again. \n");
                this.choosingPhysicsBranch();
            }
        }
        scanner.close();
    }
}
