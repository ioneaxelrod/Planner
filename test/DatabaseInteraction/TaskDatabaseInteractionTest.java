package DatabaseInteraction;

import Model.Task;
import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Malia on 11/19/17.
 */
public class TaskDatabaseInteractionTest extends TestCase  {

    public void testCreateTaskInDatabase() throws SQLException {
        final Task task = DatabaseInteractionTest.createDummyTask("foo");

        DatabaseInteraction.printTaskTableToConsole();
        TaskDatabaseInteraction.createTaskInDatabase(task);
        DatabaseInteraction.printTaskTableToConsole();
        final Task retrievedTask = TaskDatabaseInteraction.retrieveTaskFromDatabase(task.getId());
        TaskDatabaseInteraction.deleteTaskFromDatabase(task);
        DatabaseInteraction.printTaskTableToConsole();
        assertEquals(task.getTitle(), retrievedTask.getTitle());

    }

    public void testDeleteTaskInDatabase() throws SQLException {
        final Task task = DatabaseInteractionTest.createDummyTask("foo");

        TaskDatabaseInteraction.createTaskInDatabase(task);
        DatabaseInteraction.printTaskTableToConsole();

        TaskDatabaseInteraction.deleteTaskFromDatabase(task);

        ArrayList<Task> tasks = DatabaseInteraction.retrieveTasksFromDatabase();
        DatabaseInteraction.printTaskTableToConsole();

        for (Task step : tasks) {
            if (step.getId() == task.getId()) {
                assertTrue(false);
            }
        }
    }

    public void testUpdateTaskInDatabase() throws SQLException {

        final Task task = DatabaseInteractionTest.createDummyTask("foo");

        TaskDatabaseInteraction.createTaskInDatabase(task);
        DatabaseInteraction.printTaskTableToConsole();

        final String newTitle = "bar";
        task.setTitle(newTitle);


        TaskDatabaseInteraction.updateTaskInDatabase(task);
        final Task retrievedTask = DatabaseInteraction.retrieveTaskFromDatabase(task.getId());


        DatabaseInteraction.printTaskTableToConsole();


        TaskDatabaseInteraction.deleteTaskFromDatabase(task);
        assertEquals(retrievedTask.getTitle(), newTitle);

    }

    public void testRetrieveTaskFromDatabase() throws SQLException {

        final Task task = DatabaseInteractionTest.createDummyTask("foo");
        TaskDatabaseInteraction.createTaskInDatabase(task);
        DatabaseInteraction.printTaskTableToConsole();

        final Task retrievedTask = TaskDatabaseInteraction.retrieveTaskFromDatabase(task.getId());
        TaskDatabaseInteraction.deleteTaskFromDatabase(task);
        DatabaseInteraction.printTaskTableToConsole();

        assertEquals(task.getTitle(), retrievedTask.getTitle());

    }

}
