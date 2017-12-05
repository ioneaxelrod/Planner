package GUI;

import DatabaseInteraction.ProjectDatabaseInteraction;
import DatabaseInteraction.TaskDatabaseInteraction;
import Model.Project;
import Model.Task;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;

public class ProjectView extends JFrame
    {

        final private JPanel projectTitlePanel;
        final private JPanel projectPanel;
        final private JPanel bottomPanel;
        private static Project currentProject;

        ProjectView(Project project)
        {
            super(project.getTitle());
            this.currentProject = project;

            setSize(500,500);
            setResizable(false);
            dispose();
            setLayout(new BorderLayout());
            setVisible(true);
            System.out.println("Project frame created");


            projectTitlePanel = new JPanel();
            add(projectTitlePanel, BorderLayout.NORTH);

            final JLabel deadlineLabel = new JLabel(currentProject.getTitle());
            projectTitlePanel.add(deadlineLabel);

            projectPanel = new JPanel();
            add(projectPanel, BorderLayout.CENTER);

            final ArrayList<String> parsedProjectInfo = currentProject.parseProjectForJList();
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String data: parsedProjectInfo) {
                listModel.addElement(data);
            }

            final JList projectList = new JList(listModel);

            projectPanel.add(projectList);

            bottomPanel = new JPanel();
            add(bottomPanel, BorderLayout.SOUTH);

            final JButton deleteButton = new JButton("Delete");
            //TODO: make this functional
//            deleteButton.addActionListener(e -> deleteProject(currentProject));
            bottomPanel.add(deleteButton);

        }

        //TODO: move this somehwere else

        public static String formatLocalDate(LocalDate date) {
            return date.getMonthValue() + "-" + date.getDayOfMonth() + "-" + date.getYear();
        }

        private void deleteProject(Project project) {
            for (Task task: project.getSteps()) {
                TaskDatabaseInteraction.deleteAllProjectTasksFromDatabase(project.getId());
            }
            ProjectDatabaseInteraction.deleteProjectFromDatabase(project);
        }

    }


