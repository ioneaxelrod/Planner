package DatabaseInteraction;

import Model.Project;
import Model.Task;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Created by Malia on 11/17/17.
 */
public final class DatabaseInteractionTest extends TestCase {

    final static Project ski = new Project(
            "Ski",
            "slide down hill on wood sticks",
            LocalDate.of(2016, 1, 16),
            3,
            9999);

    final static Project snowboard = new Project("Snowboard",
            "slide sideways down a mountain",
            LocalDate.of(2016, 1, 15),
            5,
            10000);

    final static Task buyGear = new Task("Buy Gear",
            "go to store and pick out gear",
            420);

    final static Task rentCabin = new Task("Rent Cabin",
            "go on Airbnb", 421);

    final static Task rideLift = new Task("Ride Life",
            "get on the ski lift", 422);

    final static String projectsTableName = "projects";
    final static String tasksTableName = "tasks";

    //================================================================================
    // Create / Delete Tests
    //================================================================================

    public static Project createDummyProject(String foo) {
        return new Project(
                foo,
                "slide down hill on wood sticks",
                LocalDate.of(2016, 1, 16),
                3, 563); //<use the incrementer here
    }

    public static Task createDummyTask(String foo) {
        return new Task(foo,
                "bar",
                563
        );
    }

    //================================================================================
    // Project Tests
    //================================================================================

    @Test
    public void testAddTableRowToDatabase() throws SQLException {
        // before
        DatabaseInteraction.printProjectTableToConsole();

        // add project to db
        final Project foo = createDummyProject("foo");
        DatabaseInteraction.addTableRowIntoDatabase(foo.getId(), projectsTableName);
        DatabaseInteraction.printProjectTableToConsole();

        // check if project is added
        assertTrue(DatabaseInteraction.verifyProjectExists(foo.getId()));

        // delete project
        DatabaseInteraction.deleteTableRowFromDatabase(foo.getId(), projectsTableName);

    }

    @Test
    public void testDeleteTableRowFromDatabase() throws SQLException {

        //add dummy project to database
        final Project foo = createDummyProject("foo");
        DatabaseInteraction.addTableRowIntoDatabase(foo.getId(), projectsTableName);
        DatabaseInteraction.printProjectTableToConsole();

        //delete dummy project
        DatabaseInteraction.deleteTableRowFromDatabase(foo.getId(), projectsTableName);
        DatabaseInteraction.printProjectTableToConsole();

        //confirm dummy project does not exist in database
        final ArrayList<Project> projects = DatabaseInteraction.retrieveProjectsFromDatabase();
        for (final Project project : projects) {
            if (project.getTitle() == foo.getTitle()) {
                //assert that this line of code is never reachable
                assertTrue(false);
            }
        }
    }

    @Test
    public void testUpdateProjectInDatabase() throws SQLException {
        //

        Project foo = createDummyProject("foo");
        DatabaseInteraction.addTableRowIntoDatabase(foo.getId(), projectsTableName);
        DatabaseInteraction.printProjectTableToConsole();
        final String newTitle = "Sled";

        foo.setTitle(newTitle);

        DatabaseInteraction.updateProjectInDatabase(foo.getTitle(),
                foo.getDescription(),
                foo.getDeadline(),
                foo.isFinished,
                foo.getPriority(),
                foo.getId());

        DatabaseInteraction.printProjectTableToConsole();

        final Project project = DatabaseInteraction.retrieveProjectFromDatabase(foo.getId());
        final String projectTitle = project.getTitle();
        DatabaseInteraction.deleteTableRowFromDatabase(foo.getId(), projectsTableName);
        assertEquals(foo.getTitle(), projectTitle);

    }

    @Test
    public void testRetrieveProjectFromDatabase() throws SQLException {

        DatabaseInteraction.printProjectTableToConsole();

        Project retrieveProject = DatabaseInteraction.retrieveProjectFromDatabase(DatabaseInteractionTest.snowboard.getId());

        System.out.println(retrieveProject.getDeadline());
        System.out.println(DatabaseInteractionTest.snowboard.getDeadline());

        assertEquals(retrieveProject.getDeadline(), DatabaseInteractionTest.snowboard.getDeadline());

    }


    //================================================================================
    // Task Tests
    //================================================================================

    @Test
    public void testUpdateTaskFromDatabase() throws SQLException {

        DatabaseInteraction.printTaskTableToConsole();
        final String newDescription = "have as much fun as possible";
        DatabaseInteractionTest.rentCabin.setDescription(newDescription);

        DatabaseInteraction.updateTaskInDatabase(DatabaseInteractionTest.rentCabin.getTitle(),
                DatabaseInteractionTest.rentCabin.getDescription(),
                DatabaseInteractionTest.rentCabin.isFinished,
                DatabaseInteractionTest.snowboard.getId(),
                DatabaseInteractionTest.rentCabin.getId());

        Task retrieveTask = DatabaseInteraction.retrieveTaskFromDatabase(DatabaseInteractionTest.rentCabin.getId());

        DatabaseInteraction.printTaskTableToConsole();

        assertEquals(DatabaseInteractionTest.rentCabin.getDescription(), retrieveTask.getDescription());
    }

    //Test is not ready
    @Test
    public void testRetrieveProjectTasksFromDatabase() throws SQLException{
        DatabaseInteraction.printTaskTableToConsole();
        final ArrayList<Task> retrievedTasks = DatabaseInteraction.retrieveProjectTasksFromDatabase(
                DatabaseInteractionTest.ski.getId());

        for (final Task task : retrievedTasks) {
            System.out.println(task.getTitle());
        }
    }

    //
    @Test
    public void testDeleteAllTasksForProjectFromDatabase() throws SQLException {
        final Project foo = DatabaseInteractionTest.createDummyProject("foo");
        final Task bar = DatabaseInteractionTest.createDummyTask("bar");
        final Task blah = DatabaseInteractionTest.createDummyTask("blah");

        DatabaseInteraction.addTableRowIntoDatabase(foo.getId(), projectsTableName);
        DatabaseInteraction.updateProjectInDatabase(foo.getTitle(),
                foo.getDescription(),
                foo.getDeadline(),
                foo.isFinished,
                foo.getPriority(),
                foo.getId());
        DatabaseInteraction.addTableRowIntoDatabase(bar.getId(), tasksTableName);
        DatabaseInteraction.updateTaskInDatabase(bar.getTitle(),
                bar.getDescription(),
                bar.isFinished,
                bar.getProjectId(),
                bar.getId());
        DatabaseInteraction.addTableRowIntoDatabase(blah.getId(), tasksTableName);
        DatabaseInteraction.updateTaskInDatabase(blah.getTitle(),
                blah.getDescription(),
                blah.isFinished,
                blah.getProjectId(),
                blah.getId());

        DatabaseInteraction.printProjectTableToConsole();
        DatabaseInteraction.printTaskTableToConsole();

        DatabaseInteraction.deleteAllTasksForProjectFromDatabase(foo.getId());
        DatabaseInteraction.deleteTableRowFromDatabase(foo.getId(), projectsTableName);

        DatabaseInteraction.printProjectTableToConsole();
        DatabaseInteraction.printTaskTableToConsole();

        final ArrayList<Task> tasks = DatabaseInteraction.retrieveTasksFromDatabase();
        for (final Task task : tasks) {
            if (task.getTitle() == blah.getTitle()) {
                assertTrue(false);
            }
        }
    }

    public void testDeleteAllProjectsFromDatabase() throws SQLException {

        ArrayList<Project> allProjects = DatabaseInteraction.retrieveProjectsFromDatabase();

        DatabaseInteraction.printProjectTableToConsole();

        DatabaseInteraction.deleteAllProjectsFromDatabase();

        DatabaseInteraction.printProjectTableToConsole();
        System.out.println("Only one table should appear above, but two were generated");
        System.out.println();

        ArrayList<Project> shouldHoldNoProjects = DatabaseInteraction.retrieveProjectsFromDatabase();

        for (Project project: allProjects) {
            DatabaseInteraction.addTableRowIntoDatabase(project.getId(), projectsTableName);
            DatabaseInteraction.updateProjectInDatabase(project.getTitle(),
                    project.getDescription(),
                    project.getDeadline(),
                    project.isFinished,
                    project.getPriority(),
                    project.getId());
        }

        DatabaseInteraction.printProjectTableToConsole();

        assertTrue(shouldHoldNoProjects.isEmpty());
    }

    public void testDeleteAllTasksFromDatabase() throws SQLException{

        ArrayList<Task> allTasks = DatabaseInteraction.retrieveTasksFromDatabase();

        DatabaseInteraction.printTaskTableToConsole();

        DatabaseInteraction.deleteAllTasksFromDatabase();

        DatabaseInteraction.printTaskTableToConsole();
        System.out.println("Only one table should appear above, but two were generated");
        System.out.println();

        ArrayList<Task> shouldHoldNoTasks = DatabaseInteraction.retrieveTasksFromDatabase();

        for (Task task: allTasks) {
            DatabaseInteraction.addTableRowIntoDatabase(task.getId(), tasksTableName);
            DatabaseInteraction.updateTaskInDatabase(task.getTitle(),
                    task.getDescription(),
                    task.isFinished,
                    task.getProjectId(),
                    task.getId());
        }

        DatabaseInteraction.printTaskTableToConsole();

        assertTrue(shouldHoldNoTasks.isEmpty());


    }

    public void testPrintProjectsTable() throws SQLException{
        DatabaseInteraction.printProjectTableToConsole();
        LocalDate localDate = LocalDate.of(2017, 12, 1);


    }


}