package GUI;
import Model.Project;

import java.awt.BorderLayout;
import java.time.LocalDate;
import javax.swing.*;

public class ProjectView extends JFrame
    {

        final private JPanel deadlinePanel;


        private static Project currentProject;

        ProjectView(Project a)
        {
            super(a.getTitle());
            currentProject = a;

            setSize(500,300);
            setResizable(false);
            dispose();
            setLayout(new BorderLayout());
            setVisible(true);
            System.out.println("Project frame created");


            deadlinePanel = new JPanel();
            JLabel deadlineLabel = new JLabel(formatLocalDate(currentProject.getDeadline()));
            add(deadlinePanel, BorderLayout.NORTH);
            deadlinePanel.add(deadlineLabel);

        }

        public static String formatLocalDate(LocalDate date) {
            String result = "";
            result = date.getMonthValue() + "-" + date.getDayOfMonth() + "-" + date.getYear();
            return result;
        }
    }


