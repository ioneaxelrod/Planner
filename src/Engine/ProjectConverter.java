package Engine;

import Model.Project;
import com.mysql.fabric.xmlrpc.base.Array;
import com.mysql.jdbc.StringUtils;
import oracle.jrockit.jfr.StringConstantPool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Malia on 11/27/17.
 */
public class ProjectBuilder {

    private static final int MAX_AMT_OF_ATTRIBUTES = 4;
    private static final int INDEX_OF_TITLE = 0;
    private static final int INDEX_OF_DESCRIPTION = 1;
    private static final int INDEX_OF_DEADLINE = 2;
    private static final int INDEX_OF_PRIORITY = 3;

    // Add exceptions and checks
    public static Project validateAndCreateProject(ArrayList<String> projectSpecs) {
//if specs has no entries, or if specs has only 3 entries

        final String title = projectSpecs.get(INDEX_OF_TITLE);
        //validateTitle(title)
        final String description = projectSpecs.get(INDEX_OF_DESCRIPTION);
        final String stringDeadline = projectSpecs.get(INDEX_OF_DEADLINE);
        final LocalDate deadline;
        final int priority = Integer.parseInt(projectSpecs.get(INDEX_OF_PRIORITY));

        deadline = LocalDate.parse(stringDeadline);

//        final ArrayList<String> stringDateInfo = separateValuesByDash(stringDeadline);
//        final int year = Integer.parseInt(stringDateInfo.get(0));
//        final int month = Integer.parseInt(stringDateInfo.get(1));
//        final int day = Integer.parseInt(stringDateInfo.get(2));
//        deadline = LocalDate.of(year, month, day);

        return new Project(title, description, deadline, priority);
    }

    public static ArrayList<String> separateValuesByDash(final String inputString) {
        return new ArrayList<>(Arrays.asList(inputString.split("-")));
    }

    public static ArrayList<String> separateValuesByComma(final String inputString) {
        return new ArrayList<>(Arrays.asList(inputString.split(",")));
    }

    private static boolean isValidProjectInformation(final ArrayList<String> projectSpecs) {
        if (projectSpecs.size() != MAX_AMT_OF_ATTRIBUTES) {
            System.out.println("projectSpecs ArrayList does not have correct amount of attributes");
            return false;
        }
        if (!isValidString(projectSpecs.get(INDEX_OF_TITLE))) {
            System.out.println("Title string does not exist or is blank");
            return false;
        }
        if (!isValidString(projectSpecs.get(INDEX_OF_DESCRIPTION))) {
            System.out.println("Description string does not exist or is blank");
            return false;
        }
        if (isValidInt(projectSpecs.get(INDEX_OF_PRIORITY))) {
            System.out.println("Integer string contains non integer characters");
            return false;
        }
        return true;
    }

    private static boolean isValidString(final String input) {
        if (input == null) {
            return false;
        }
        if (input == "") {
            return false;
        }
        return true;
    }

    private static boolean isValidInt(final String input) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(input);
            isValidInteger = true;
        } catch (NumberFormatException exc) {
            System.out.println("Int does not fit");
            exc.printStackTrace();
        }
        return isValidInteger;
    }

}