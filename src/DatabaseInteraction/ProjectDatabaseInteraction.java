package DatabaseInteraction;

import Model.Project;
import Model.Task;

import java.util.ArrayList;

/**
 * Create, edit, retrieve, or delete Projects in the database.
 * The engine should directly call this instead of directly interacting /w DatabaseInteraction.
 */
public class ProjectDatabaseInteraction {

    final private static String tableName = "projects";
    private static int projectIdIncrementer = 0;

    public static boolean createProjectInDatabase(final Project project) {

        final boolean createRow = DatabaseInteraction.addTableRowIntoDatabase(project.getId(), tableName);
        final boolean updateRow = DatabaseInteraction.updateProjectInDatabase(project.getTitle(),
                project.getDescription(),
                project.getDeadline(),
                project.isFinished,
                project.getPriority(),
                project.getId());

        return (createRow && updateRow);
    }

    public static boolean deleteProjectFromDatabase(final Project project) {
        return DatabaseInteraction.deleteTableRowFromDatabase(project.getId(), tableName);
    }

    public static Project retrieveProjectFromDatabase(final int id) {
        return DatabaseInteraction.retrieveProjectFromDatabase(id);
    }

    public static boolean updateProjectInDatabase(final Project updatedProject) {
        return DatabaseInteraction.updateProjectInDatabase(updatedProject.getTitle(),
                updatedProject.getDescription(),
                updatedProject.getDeadline(),
                updatedProject.isFinished,
                updatedProject.getPriority(),
                updatedProject.getId());
    }

    public static ArrayList<Project> retrieveAllProjectsInDatabase() {
        ArrayList<Project> projects = DatabaseInteraction.retrieveProjectsFromDatabase();
        for (Project project: projects) {
            addTasksToProject(project);
        }
        return projects;
    }

    private static void addTasksToProject(Project project) {
        ArrayList<Task> projectTasks = DatabaseInteraction.retrieveProjectTasksFromDatabase(project.getId());
        project.addSteps(projectTasks);
    }


    public synchronized static int incrementAndGet() {
        return ++projectIdIncrementer;
    }

    public synchronized static void setProjectIdOnInit(final int idCount) {
        projectIdIncrementer = idCount;
    }


}


