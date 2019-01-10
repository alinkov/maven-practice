package com.appInterface;

import java.util.Scanner;

// класс, в котором в виде статических методов хранятся утилиты
// для использования в других частях приложения

public class MyUtils {

    // обработчик значений, вводимых пользователем через консоль:
    // разрешает только числа, не равные нулю
    // (исключение: символ "q" для выхода из приложения)
    public static double enterDouble(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(title + " (press q to quit) : ");
        String input = scanner.nextLine();
        if (input.equals("q")) {
            System.exit(0);
        }
        double value = 0;
        try {
             value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.print("It's not a number! ");
            double newValue = MyUtils.enterDouble(title);
        }
        if (value == 0){
            System.out.print("What's the reason to use zero values?.. try again! ");
            double newValue = MyUtils.enterDouble(title);
        }
        return value;
    }

    // оформление вывода результата вычисления физической величины
    public static void outputComputedValue(String title, String units, double value) {
        System.out.print(title + " value is  : " + Double.toString(value) + " " + units + '\n');
        MainInterface appInterface = new MainInterface();
        appInterface.choosingPhysicsBranch();
    }
}
