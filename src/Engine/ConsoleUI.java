package Engine;

import com.mysql.fabric.xmlrpc.base.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Malia on 11/26/17.
 */
public class ConsoleUI {

    public static String getUserInputForProject() {
        final String inputString;
        System.out.println("Enter project title, project description, project due date as 'YYYY/MM/DD' project priority number");
        System.out.print(">>");
        final Scanner INPUT = new Scanner(System.in);
        inputString = INPUT.nextLine();
        return inputString;
    }

    public static String getUserInputForTask() {
        final String inputString;
        System.out.println("Enter task title, task description, task project id");
        System.out.print(">>");
        final Scanner INPUT = new Scanner(System.in);
        inputString = INPUT.nextLine();
        return inputString;
    }

    public static ArrayList<String> separateValuesByComma(final String inputString) {
        return new ArrayList<>(Arrays.asList(inputString.split(", ")));
    }


}
