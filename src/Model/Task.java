package Model;

import DatabaseInteraction.TaskDatabaseInteraction;

/**
 * Created by Malia on 11/15/17.
 */
//TODO: Create int IDs for Primary Key

//====================================================================================
// Model.Task Class
//====================================================================================

public class Task {

    //================================================================================
    // Variables
    //================================================================================
    final private int id;
    private String title;
    private String description = "";
    private int projectId;
    public boolean isFinished = false;

    //================================================================================
    // Constructors
    //================================================================================

    public Task(final String title, final String description, final int projectId) {
        this.title = title;
        this.description = description;
        this.projectId = projectId;
        this.id = TaskDatabaseInteraction.incrementAndGet();
    }

    // DO NOT USE
    public Task(String title, String description, boolean isFinished, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.isFinished = isFinished;
    }

    // DO NOT USE
    public Task(String title, String description, int projectId, int id) {
        this.title = title;
        this.description = description;
        this.projectId = projectId;
        this.id = id;
    }

    //================================================================================
    // Getters and Setters
    //================================================================================

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    //================================================================================
    // Methods
    //================================================================================



}
