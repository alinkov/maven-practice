package com.appInterface.mechanicsInterface;

import java.util.Scanner;

import com.mechanics.Hydrastatics;
import com.appInterface.MyUtils;

// интерфейс уравнения для гидростатического давления
public class HydrastaticsInterface {

    // запрашивает, какую величину пользователь хотел бы рассчитать
    public void hydrastaticPressureInterfaceMain(){
        System.out.println("|------------------------------------------|\n" +
                "Welcome to Hydrastatic Pressure! \n" +
                "|------------------------------------------|\n" +
                "Choose what you'd like to calculate: \n" +
                "1. pressure \n" + "2. density \n" + "3. fluid_height \n");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of branch (press q to quit): ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                this.hydrastaticPressurePressureCompute();
                break;
            case "2":
                this.hydrastaticPressureDensityCompute();
                break;
            case "3":
                this.hydrastaticPressureFluidHeightCompute();
                break;
            case "q":
                System.exit(0);
            default: {
                System.out.println("Wrong code! Try again. \n");
                this.hydrastaticPressureInterfaceMain();
            }
        }
        scanner.close();
    }

    // запрашивает данные для расчета гидростатического давления
    // и отправляет программу на вычисление этой величины
    private void hydrastaticPressurePressureCompute() {

        double densityValue = MyUtils.enterDouble("Enter density value in kilogram per cubic meter");
        double fluidHeightValue = MyUtils.enterDouble("Enter fluid height value in meter");

        Hydrastatics hydrastatics = new Hydrastatics ();

        MyUtils.outputComputedValue("Hydrastatic pressure", "pascal",
                hydrastatics.hydrastaticPressureCalc(densityValue, fluidHeightValue));

    }

    // запрашивает данные для расчета плотности жидкости
    // и отправляет программу на вычисление этой величины
    private void hydrastaticPressureDensityCompute() {

        double pressureValue = MyUtils.enterDouble("Enter hydrstatic pressure value in pascal");
        double fluidHeightValue = MyUtils.enterDouble("Enter fluid height value in meter");

        Hydrastatics hydrastatics = new Hydrastatics();

        MyUtils.outputComputedValue("Density", "kilogram per cubic meter",
                hydrastatics.densityCalc(pressureValue, fluidHeightValue));

    }

    // запрашивает данные для расчета высоты столба жидкости
    // и отправляет программу на вычисление этой величины
    private void hydrastaticPressureFluidHeightCompute() {

        double pressure = MyUtils.enterDouble("Enter hydrastatic pressure value in pascal");
        double density = MyUtils.enterDouble("Enter density value in kilogram per cubic second");

        Hydrastatics hydrastatics = new Hydrastatics();

        MyUtils.outputComputedValue("Fluid height", "meter",
                hydrastatics.fluidHeightCalc(pressure, density));
    }
}
