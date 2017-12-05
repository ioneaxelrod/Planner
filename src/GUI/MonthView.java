package GUI;

/**
 * Created by Malia on 12/4/17.
 */

import DatabaseInteraction.ProjectDatabaseInteraction;
import Model.Project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MonthView extends JFrame {

    final private JPanel calendarDayOfWeek = new JPanel();
    final private JPanel monthPanels = new JPanel();
    final private JPanel weekNamePanels = new JPanel();

    final private JLabel sun = new JLabel("Sunday");
    final private JLabel mon = new JLabel("Monday");
    final private JLabel tue = new JLabel("Tuesday");
    final private JLabel wed = new JLabel("Wednesday");
    final private JLabel thur = new JLabel("Thursday");
    final private JLabel fri = new JLabel("Friday");
    final private JLabel sat = new JLabel("Saturday");

    final private int FRAME_WIDTH = 1400;
    final private int FRAME_HEIGHT = 1000;

    //TODO: not final
    //dayPusher aligns GridLayout to be like a month in a calendar
    private int dayPusher = 0;
    private int dayOfMonth = 0;

    private JLabel emptyLabel;

    final private GridLayout daysOfWeekLayout = new GridLayout(1, 7);
    final private GridLayout daysOfMonthLayout = new GridLayout(6, 7);
    final private ArrayList<Project> projects = projectsToFillDays();

    public MonthView() {
        super("December 2017");

        //Frame Setup
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Top Setup
        weekNamePanels.setLayout(daysOfWeekLayout);
        weekNamePanels.add(sun);
        weekNamePanels.add(mon);
        weekNamePanels.add(tue);
        weekNamePanels.add(wed);
        weekNamePanels.add(thur);
        weekNamePanels.add(fri);
        weekNamePanels.add(sat);

        //Center Setup
        monthPanels.setLayout(new GridLayout(6, 7));
        fillDaysInMonth();
        add(monthPanels, BorderLayout.CENTER);
        add(weekNamePanels, BorderLayout.NORTH);

        JButton addButton = new JButton("Add");

        add(addButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private ArrayList<Project> projectsToFillDays() {
        return ProjectDatabaseInteraction.retrieveAllProjectsInDatabase();
    }




    private ArrayList<Project> findProjectWhoseDeadlineIsThisDay(int dayOfMonth) {
        ArrayList<Project> projectsWithDeadline = new ArrayList<>();
        for (Project project: projects) {
            if (project.getDeadline().getDayOfMonth() == dayOfMonth) {
                projectsWithDeadline.add(project);
            }
        }
        return projectsWithDeadline;
    }

    private void fillDaysInMonth() {
        for (int row = 0; row < daysOfMonthLayout.getRows(); row++) {
            for (int col = 0; col < daysOfMonthLayout.getColumns(); col++) {
                if (dayPusher < 5 || dayPusher > 35) {
                    dayPusher +=1;
                    emptyLabel = new JLabel("");
                    monthPanels.add(emptyLabel);
                }
                else {
                    dayPusher +=1;
                    dayOfMonth +=1;

                    String dateHeader = dayOfMonth + "\n";

                    JButton DateButton = new JButton(dateHeader);

                    DateButton.addActionListener(e -> {
                        final ArrayList<Project> testAttempt = projectsToFillDays();
                        final DayView b = new DayView(testAttempt);
                    });

                    monthPanels.add(DateButton);
                }
            }
        }
    }

}

