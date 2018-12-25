package app_interface;

import java.util.Scanner;

public class MyUtils {

    public static double enter_double (String title){
        Scanner scanner = new Scanner(System.in);
        System.out.print(title + " : ");
        String input = scanner.nextLine();
        double value = Double.parseDouble(input);
        return value;
    };

    public static void return_computed_value (String title, String units, double value){
        System.out.print(title + " value is  : " + Double.toString(value) + " " + units + '\n');
        MainInterface app_interface = new MainInterface ();
        app_interface.choosing_physics_branch();
    };
}
