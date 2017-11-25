package Model;



import DatabaseInteraction.ProjectDatabaseInteraction;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Malia on 11/15/17.
 */

//================================================================================
// Model.Project Class
//================================================================================
public class Project {

    //================================================================================
    // Variables
    //================================================================================

    private int id;
    private LocalDate deadline;
    private String title;
    private String description;
    private ArrayList<Task> tasks = new ArrayList<>();
    public boolean isFinished = false;
    private int priority;
    private ArrayList<Task> completedTasks = new ArrayList<>();

    //================================================================================
    // Constructors
    //================================================================================

    //DO NOT USE
    public Project(String title, String description, LocalDate deadline, int priority, int id) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.id = id;
    }


    //USE THIS CONSTRUCTOR
    public Project(String title, String description, LocalDate deadline, int priority) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.id = ProjectDatabaseInteraction.incrementAndGet();
    }

    public Project(Project another) {
        this.title = another.title;
        this.description = another.description;
        this.deadline = another.deadline;
        this.priority = another.priority;
        this.tasks = another.tasks;
        this.completedTasks = another.completedTasks;
        this.id = another.id;
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

//    public String getDeadline() {
//        return deadline.getDayOfWeek() +  ", " + String.valueOf(deadline);
//    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public int getPriority() {
        return priority;
    }

    public ArrayList<Task> getSteps() {
        return tasks;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Task> getCompletedTasks() {
        return completedTasks;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        this.deadline = deadline;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setSteps(ArrayList<Task> steps) {
        this.tasks = steps;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }


    //================================================================================
    // Methods
    //================================================================================

    public void addStep(Task step) {
        tasks.add(step);
    }

    public void addCompletedStep(Task step) {
        completedTasks.add(step);
    }

    public String printSteps() {
        String answer = "";
        for (Task task : tasks) {
            answer = answer + task.getTitle() + "\n";
        }
        return answer;
    }

    public void printProject() {
        String answer = "Title: " + title + "\n";
        answer = answer + "Description: " + description + "\n";
        answer = answer + "Deadline: " + deadline + "\n";
        if (tasks.isEmpty()) {
            return;
        }
        answer = answer + printSteps();
    }

    public boolean checkIfProjectIsComplete() {
        if (isFinished) {
            return true;
        }
        for (Task task: tasks) {
            if (!task.isFinished) {
                return false;
            }
        }
        setFinished(true);
        return true;
    }


    public void completeStep(Task task) {
        task.isFinished = true;
        if (checkIfProjectIsComplete()) {
            System.out.println("Congratulations you completed the project!");
        }
    }





}