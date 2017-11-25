package DatabaseInteraction;

import Model.Task;

/**
 * Create, edit, retrieve, or delete Tasks in the database.
 * The engine should directly call this instead of directly interacting /w DatabaseInteraction.
 */
public class TaskDatabaseInteraction {

    final static private String TABLE_NAME = "tasks";
    private static int taskIdIncrementer = 0;

    public static boolean createTaskInDatabase(final Task task) {

        final boolean createRow = DatabaseInteraction.addTableRowIntoDatabase(task.getId(), TABLE_NAME);
        final boolean updateRow = DatabaseInteraction.updateTaskInDatabase(task.getTitle(),
                task.getDescription(),
                task.isFinished,
                task.getProjectId(),
                task.getId());

        return (createRow && updateRow);
    }

    public static boolean deleteTaskFromDatabase(final Task task) {
        final boolean deleteRow = DatabaseInteraction.deleteTableRowFromDatabase(task.getId(), TABLE_NAME);
        return deleteRow;
    }

    public static Task retrieveTaskFromDatabase(final int id) {
        return DatabaseInteraction.retrieveTaskFromDatabase(id);
    }

    public static boolean updateTaskInDatabase(final Task updatedTask) {
        return DatabaseInteraction.updateTaskInDatabase(updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.isFinished,
                updatedTask.getId(),
                updatedTask.getId());
    }

    public synchronized static int incrementAndGet() {
        return ++taskIdIncrementer;
    }

    protected synchronized static void setTaskIdOnInit(final int idCount) {
        taskIdIncrementer = idCount;
    }
}
