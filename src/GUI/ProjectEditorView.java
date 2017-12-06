package GUI;

import DatabaseInteraction.DatabaseInteraction;
import DatabaseInteraction.ProjectDatabaseInteraction;
import DatabaseInteraction.TaskDatabaseInteraction;
import Model.Project;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Created by Malia on 12/4/17.
 */
public class ProjectEditorView extends JFrame {

    final private int FRAME_WIDTH = 650;
    final private int FRAME_HEIGHT = 200;

    final private JPanel topPanel;
    final private JPanel centerPanel;
    final private JPanel bottomPanel;

    final private JTextField titleField;
    final private JTextField deadlineYearField;
    final private JTextField deadlineMonthField;
    final private JTextField deadlineDayField;
    final private JTextField priorityField;
    final private JTextField descriptionField;
    final private JTextField taskField1;
    final private JTextField taskField2;
    final private JTextField taskField3;
    final private JTextField taskField4;

    final private JFrame frame;

    ProjectEditorView(JFrame frame) {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        dispose();
        setLayout(new BorderLayout());
        setVisible(true);
        this.frame = frame;

        topPanel = new JPanel();
        centerPanel = new JPanel();
        bottomPanel = new JPanel();

        final JLabel topLabel = new JLabel("New Project");

        final JLabel titleLabel = new JLabel("Title: ");
        titleField = new JTextField("", 25);

        final JLabel deadlineLabel = new JLabel("Deadline: ");
        deadlineYearField = new JTextField("YYYY", 4);
        deadlineMonthField = new JTextField("MM", 2);
        deadlineDayField = new JTextField("DD", 2);

        final JLabel priorityLabel = new JLabel("Priority: ");
        priorityField = new JTextField("", 1);

        final JLabel descriptionLabel = new JLabel("Description: ");
        descriptionField = new JTextField("", 45);


        final JLabel taskLabel = new JLabel("Tasks: ");
        taskField1 = new JTextField("", 11);
        taskField2 = new JTextField("", 11);
        taskField3 = new JTextField("", 11);
        taskField4 = new JTextField("", 11);

        final JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> saveInformation());

        topPanel.add(topLabel);

        centerPanel.add(titleLabel);
        centerPanel.add(titleField);
        centerPanel.add(deadlineLabel);
        centerPanel.add(deadlineDayField);
        centerPanel.add(deadlineMonthField);
        centerPanel.add(deadlineYearField);
        centerPanel.add(priorityLabel);
        centerPanel.add(priorityField);
        centerPanel.add(descriptionLabel);
        centerPanel.add(descriptionField);
        centerPanel.add(taskLabel);
        centerPanel.add(taskField1);
        centerPanel.add(taskField2);
        centerPanel.add(taskField3);
        centerPanel.add(taskField4);

        bottomPanel.add(saveButton);


        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        System.out.println("Project Creator/Editor Window Opened");

    }

    private void saveInformation() {

        final Project newProject = consolidateUserInputForProject();
        final ArrayList<Task> newTasks = consolidateUserInputForTasks(newProject.getId());

        ProjectDatabaseInteraction.createProjectInDatabase(newProject);
        for (Task task : newTasks) {
            TaskDatabaseInteraction.createTaskInDatabase(task);
        }
        MonthView.updateFrame(frame);
        dispose();
        try {
            DatabaseInteraction.printProjectTableToConsole();
            DatabaseInteraction.printTaskTableToConsole();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Project consolidateUserInputForProject() {
        final String title = titleField.getText();
        final String description = descriptionField.getText();

        final String deadlineYearString = deadlineYearField.getText();
        final String deadlineMonthString = deadlineMonthField.getText();
        final String deadlineDayString = deadlineDayField.getText();
        final LocalDate deadline = convertDeadlineToLocalDate(deadlineYearString, deadlineMonthString, deadlineDayString);

        final String priorityString = priorityField.getText();
        final int priority = Integer.parseInt(priorityString);

        return new Project(title, description, deadline, priority);
    }

    private ArrayList<Task> consolidateUserInputForTasks(final int newProjectId) {

        final String task1String = taskField1.getText();
        final String task2String = taskField2.getText();
        final String task3String = taskField3.getText();
        final String task4String = taskField4.getText();

        ArrayList<Task> newTasks = new ArrayList<>();

        if (task1String != "" || task1String != null) {
            final Task task1 = new Task(task1String, newProjectId);
            newTasks.add(task1);
        }
        if (task2String != "" || task2String != null) {
            final Task task2 = new Task(task2String, newProjectId);
            newTasks.add(task2);
        }
        if (task3String != "" || task3String != null) {
            final Task task3 = new Task(task3String, newProjectId);
            newTasks.add(task3);
        }
        if (task4String != "" || task4String != null) {
            final Task task4 = new Task(task4String, newProjectId);
            newTasks.add(task4);
        }

        return newTasks;
    }

    private LocalDate convertDeadlineToLocalDate(final String yearString,
                                                 final String monthString,
                                                 final String dayString) {
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString);
        int day = Integer.parseInt(dayString);

        return LocalDate.of(year, month, day);
    }

}
