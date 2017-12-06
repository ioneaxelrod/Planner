package GUI;

/**
 * Created by Malia on 12/4/17.
 */

import DatabaseInteraction.ProjectDatabaseInteraction;
import Model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


public class MonthView extends JFrame {

    final private JPanel monthPanels = new JPanel();
    final private JPanel weekNamePanels = new JPanel();

    final private JLabel sun = new JLabel("Sunday", SwingConstants.CENTER);
    final private JLabel mon = new JLabel("Monday", SwingConstants.CENTER);
    final private JLabel tue = new JLabel("Tuesday", SwingConstants.CENTER);
    final private JLabel wed = new JLabel("Wednesday", SwingConstants.CENTER);
    final private JLabel thur = new JLabel("Thursday", SwingConstants.CENTER);
    final private JLabel fri = new JLabel("Friday", SwingConstants.CENTER);
    final private JLabel sat = new JLabel("Saturday", SwingConstants.CENTER);

    final private int FRAME_WIDTH = 1400;
    final private int FRAME_HEIGHT = 1000;
    final private GridLayout daysOfWeekLayout = new GridLayout(1, 7);
    final private GridLayout daysOfMonthLayout = new GridLayout(6, 7);
    final private ArrayList<Project> projects = projectsToFillDays();
    //dayPusher aligns GridLayout to be like a month in a calendar
    private int dayPusher = 0;
    private int dayOfMonth = 0;

    public MonthView() {
        super("December 2017");

        //Frame Setup
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //Top Setup
        createTopPanel();
        add(weekNamePanels, BorderLayout.NORTH);

        //Center Setup
        monthPanels.setLayout(new GridLayout(6, 7));
        fillDaysInMonth();
        add(monthPanels, BorderLayout.CENTER);

        //Bottom Setup
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> new ProjectEditorView(this));
        add(addButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void updateFrame(JFrame frame) {

        WindowEvent closingEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
        MonthView view = new MonthView();

    }

    private void createTopPanel() {
        weekNamePanels.setLayout(daysOfWeekLayout);
        weekNamePanels.add(sun);
        weekNamePanels.add(mon);
        weekNamePanels.add(tue);
        weekNamePanels.add(wed);
        weekNamePanels.add(thur);
        weekNamePanels.add(fri);
        weekNamePanels.add(sat);
    }

    private ArrayList<Project> projectsToFillDays() {
        return ProjectDatabaseInteraction.retrieveAllProjectsInDatabase();
    }

    private ArrayList<Project> findProjectWhoseDeadlineIsThisDay(int dayOfMonth) {
        ArrayList<Project> allProjects = projectsToFillDays();
        ArrayList<Project> projectsWithDeadline = new ArrayList<Project>();
        for (Project project : projects) {
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
                    dayPusher += 1;
                    final JLabel emptyLabel = new JLabel("");
                    monthPanels.add(emptyLabel);
                } else {
                    dayPusher += 1;
                    dayOfMonth += 1;

                    String dateHeader = "" + dayOfMonth;

                    if (findProjectWhoseDeadlineIsThisDay(dayOfMonth).isEmpty()) {
                        JLabel dayLabel = new JLabel(dateHeader, SwingConstants.CENTER);
                        monthPanels.add(dayLabel);
                    } else {
                        JButton dayButton = new JButton(dateHeader);
                        dayButton.addActionListener(e -> {
                            final int buttonDay = Integer.parseInt(dayButton.getText());
                            final ArrayList<Project> testAttempt = findProjectWhoseDeadlineIsThisDay(buttonDay);
                            final DayView b = new DayView(testAttempt, this);
                        });
                        monthPanels.add(dayButton);
                    }
                }
            }
        }
    }

}

