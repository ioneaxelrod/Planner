package Engine;

import Model.Project;
import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Malia on 11/27/17.
 */
public class ProjectBuilderTest extends TestCase{

    public void testBuildProject(){

        final String title = "Project";
        final String description = "Test";
        final String date = "1999-12-1";
        final String priority = "4";
        final ArrayList<String> specs = new ArrayList<>(Arrays.asList(title, description, date, priority));

        final Project testProject = ProjectBuilder.buildProject(specs);

        final String expectedTitle = "Project";
        final String expectedDescription = "Test";
        final LocalDate expectedDeadline = LocalDate.of(1999, 12, 1);
        final int expectedPriority = 4;

        if (testProject.getTitle() != expectedTitle ||
                testProject.getDescription() != expectedDescription ||
                testProject.getDeadline() != expectedDeadline ||
                testProject.getPriority() != expectedPriority ) {
            assertFalse(false);
        }


    }

}
