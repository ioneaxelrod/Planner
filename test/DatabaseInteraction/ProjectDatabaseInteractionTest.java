package DatabaseInteraction;

import Model.Project;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Malia on 11/19/17.
 */
public class ProjectDatabaseInteractionTest extends TestCase {


    public void testCreateProjectInDatabase() {
        final Project project = DatabaseInteractionTest.createDummyProject("foo");

        ProjectDatabaseInteraction.createProjectInDatabase(project);
        final Project retrievedProject = DatabaseInteraction.retrieveProjectFromDatabase(project.getId());
        ProjectDatabaseInteraction.deleteProjectFromDatabase(project);
        assertEquals(project.getTitle(), retrievedProject.getTitle());

    }

    public void testDeleteProjectInDatabase() {
        final Project project = DatabaseInteractionTest.createDummyProject("foo");
        ProjectDatabaseInteraction.deleteProjectFromDatabase(project);
        ArrayList<Project> projects = DatabaseInteraction.retrieveProjectsFromDatabase();
        for (Project product : projects) {
            if (product.getId() == project.getId()) {
                assertTrue(false);
            }
        }
    }

    public void testUpdateProjectInDatabase() {

        final Project project = DatabaseInteractionTest.createDummyProject("foo");
        ProjectDatabaseInteraction.createProjectInDatabase(project);
        final String newTitle = "bar";
        project.setTitle(newTitle);
        ProjectDatabaseInteraction.updateProjectInDatabase(project);
        Project retrievedProject = DatabaseInteraction.retrieveProjectFromDatabase(project.getId());
        ProjectDatabaseInteraction.deleteProjectFromDatabase(project);
        assertEquals(retrievedProject.getTitle(), newTitle);

    }

    public void testGetProjectFromDatabase() {

        final Project project = DatabaseInteractionTest.createDummyProject("foo");
        ProjectDatabaseInteraction.createProjectInDatabase(project);
        Project retrievedProject = ProjectDatabaseInteraction.retrieveProjectFromDatabase(project.getId());
        ProjectDatabaseInteraction.deleteProjectFromDatabase(project);
        assertEquals(project.getTitle(), retrievedProject.getTitle());

    }
}
