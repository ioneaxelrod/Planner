package GUI;

import Model.Project;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;

public class ProjectView extends JFrame
    {

        final private JPanel projectTitlePanel;
        final private JPanel projectPanel;
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

        }

        //TODO: move this somehwere else

        public static String formatLocalDate(LocalDate date) {
            return date.getMonthValue() + "-" + date.getDayOfMonth() + "-" + date.getYear();
        }

    }


