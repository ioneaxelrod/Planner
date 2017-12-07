package GUI;

import Model.Project;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Malia on 12/4/17.
 */
public class DayView extends JFrame {

    final private ArrayList<Project> projectList;
    final private LocalDate date;

    final private JPanel datePanel = new JPanel();
    final private JPanel projectPanel = new JPanel();

    final private JLabel dateLabel;
    final private GridLayout projectLayout;

    final private JFrame frame;


    final private int FRAME_WIDTH = 400;
    final private int FRAME_HEIGHT = 400;


    DayView(ArrayList<Project> projects, JFrame frame) {
        super();

        this.date = projects.get(0).getDeadline();
        this.projectList = projects;
        this.frame = frame;
        String dateString = "Projects Due " + date.getMonthValue() + "-" + date.getDayOfMonth() + "-" + date.getYear();

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        dispose();
        setLayout(new BorderLayout());

        add(datePanel, BorderLayout.NORTH);
        add(projectPanel, BorderLayout.CENTER);

        projectLayout = new GridLayout(projectList.size(), 1);
        projectPanel.setLayout(projectLayout);

        dateLabel = new JLabel(dateString);
        datePanel.add(dateLabel);

        addProjectButtons();

        setVisible(true);

    }

    private void addProjectButtons() {
        for (Project project : projectList) {
            final JButton projectButton = new JButton(project.getTitle());
            projectPanel.add(projectButton);

            projectButton.addActionListener(e -> {
                final ProjectView b = new ProjectView(project, frame, this);
            });
        }
    }


}
