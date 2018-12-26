package main.java.com.app_interface;

import java.util.Scanner;

public class MyUtils {

    public static double enter_double (String title){
        Scanner scanner = new Scanner(System.in);
        System.out.print(title + " : ");
        String input = scanner.nextLine();
        if (MyUtils.isDigit(input)) {
            double value = Double.parseDouble(input);
            return value;
        }
        else {
            System.out.print("It's not a number! ");
            double new_value = MyUtils.enter_double(title);
            return new_value;
        }
    };

    public static void return_computed_value (String title, String units, double value){
        System.out.print(title + " value is  : " + Double.toString(value) + " " + units + '\n');
        MainInterface app_interface = new MainInterface ();
        app_interface.choosing_physics_branch();
    };

    public static boolean isDigit (String input) throws NumberFormatException {
    try {
        double value = Double.parseDouble(input);
    } catch (NumberFormatException e) {
        return false;
    }
    return true;
    }
}
