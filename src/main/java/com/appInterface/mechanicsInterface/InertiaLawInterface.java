package com.appInterface.mechanicsInterface;

import java.util.Scanner;
import com.mechanics.InertiaLaw;
import com.appInterface.MyUtils;

// интерфейс второго закона Ньютона
public class InertiaLawInterface {

    // запрашивает, какую величину пользователь хотел бы рассчитать
    public void inertiaLawInterfaceMain(){
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Inertia Law! \n" +
                "|------------------------------------------|\n" +
                "Choose what you'd like to calculate: \n" +
                "1. mass \n" + "2. force \n" + "3. acceleration \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch (press q to quit): ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.inertiaLawMassCompute();
                break;
            case "2":
                this.inertiaLawForceCompute();
                break;
            case "3":
                this.inertiaLawAccelerationCompute();
                break;
            case "q":
                System.exit(0);
            default: {
                System.out.println("Wrong code! Try again. \n");
                this.inertiaLawInterfaceMain();
            }
        }
        scanner.close();
    }

    // запрашивает данные для расчета массы и отправляет программу на вычисление этой величины
    private void inertiaLawMassCompute() {

        double forceValue = MyUtils.enterDouble("Enter force value in newton");
        double accelerationValue = MyUtils.enterDouble("Enter acceleration value in meters per square second");

        InertiaLaw inertiaLaw = new InertiaLaw();

        MyUtils.outputComputedValue("Mass", "kilogram",
                inertiaLaw.massCalc(forceValue, accelerationValue));
    }

    // запрашивает данные для расчета ускорения и отправляет программу на вычисление этой величины
    private void inertiaLawAccelerationCompute() {

        double forceValue = MyUtils.enterDouble("Enter force value in meters per square second");
        double massValue = MyUtils.enterDouble("Enter mass value in meters per square second");

        InertiaLaw inertiaLaw = new InertiaLaw();

        MyUtils.outputComputedValue("Acceleration", "meter per square second",
                inertiaLaw.accelerationCalc(forceValue, massValue));

    }

    // запрашивает данные для расчета силы и отправляет программу на вычисление этой величины
    private void inertiaLawForceCompute() {

        double massValue = MyUtils.enterDouble("Enter mass value in kilogram");
        double accelerationValue = MyUtils.enterDouble("Enter acceleration value in meters per square second");

        InertiaLaw inertiaLaw = new InertiaLaw();

        MyUtils.outputComputedValue("Force", "newton",
                inertiaLaw.forceCalc(massValue, accelerationValue));
    }
}
