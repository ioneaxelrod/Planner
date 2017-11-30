package Engine;

import Model.Project;
import com.mysql.fabric.xmlrpc.base.Array;
import oracle.jrockit.jfr.StringConstantPool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Malia on 11/27/17.
 */
public class ProjectBuilder {

    private static final int MAX_AMT_OF_ATTRIBUTES = 4;
    private static final int INDEX_OF_TITLE = 0;
    private static final int INDEX_OF_DESCRIPTION = 1;
    private static final int INDEX_OF_DEADLINE = 2;
    private static final int INDEX_OF_PRIORITY = 3;


    public static Project buildProject(ArrayList<String> projectSpecs){

        final String title = projectSpecs.get(INDEX_OF_TITLE);
        final String description = projectSpecs.get(INDEX_OF_DESCRIPTION);
        final String stringDeadline = projectSpecs.get(INDEX_OF_DEADLINE);
        final LocalDate deadline;
        final int priority = Integer.parseInt(projectSpecs.get(INDEX_OF_PRIORITY));


        final ArrayList<String> stringDateInfo = separateValuesByDash(stringDeadline);
        final int year = Integer.parseInt(stringDateInfo.get(0));
        final int month = Integer.parseInt(stringDateInfo.get(1));
        final int day = Integer.parseInt(stringDateInfo.get(2));



        deadline = LocalDate.of(year, month, day);

        return new Project(title, description, deadline, priority);

    }

    public static ArrayList<String> separateValuesByDash(final String inputString) {
        return new ArrayList<>(Arrays.asList(inputString.split("-")));
    }

}
