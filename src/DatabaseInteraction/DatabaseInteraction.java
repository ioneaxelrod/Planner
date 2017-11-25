package DatabaseInteraction;

import FileReader.FileParser;
import Model.Project;
import Model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

//TODO: Double checking all variables are final except for VERY GOOD REASON

/**
 * Crafts SQLStatements and directly interacts with the database.
 */
public class DatabaseInteraction {

    private static final DatabaseInteraction singleton = new DatabaseInteraction();
    private static Connection myConn;
    private static final String DB_URL = "jdbc:mysql://localhost:330/foo";
    private static final String USER_NAME = "root";


    private DatabaseInteraction() {
        try {
            myConn = DriverManager.getConnection(DB_URL, USER_NAME, FileParser.getPassword());
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public static DatabaseInteraction get() {
        return DatabaseInteraction.singleton;
    }

    public static void main(String[] args) {
        init();
    }

    //================================================================================
    // DB Initialization
    //================================================================================

    /**
     * Initializer that Uploads Information from Database into Engine upon startup
     * <p>
     * UNDER CONSTRUCTION
     */

    public static void init() {

        try {
            // Connection
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/foo", "root", "datab4se");
            printProjectTableToConsole();

            retrieveAndSetMaxTaskId();
            retrieveAndSetMaxProjectIdNumber();

        } catch (SQLException e) {
            System.out.println("Initiating connection failed");
            e.printStackTrace();
        } finally {

            try {
                myConn.close();
            } catch (SQLException e) {
                System.out.println("Connection failed to close");
                e.printStackTrace();
            }

        }
    }


    //================================================================================
    // Create / Delete TableRows into / from Database
    //================================================================================

    // TODO: Update other methods to this style

    /**
     * Inserts a task object into the tasks database
     *
     * @param projectId for parent Project
     * @return status of insert call
     */
    public static boolean addTableRowIntoDatabase(final int projectId, String tableName) {

        //TODO: vulnerable to SQL injection
        final String insertQuery = "insert into $tableName(id)"
                + " values (?);";
        final String finishedQuery = insertQuery.replace("$tableName", tableName);

        try {
            final PreparedStatement statement = DatabaseInteraction.myConn.prepareStatement(finishedQuery);
            statement.setInt(1, projectId);
            executeStatement(statement);
            return true; // TODO: Returns true no matter what; needs to be updated to return the proper status
        } catch (final SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Statement failed to concatenate");
            return false;
        }
    }

    public static boolean deleteTableRowFromDatabase(final int taskId, String tableName) {
        try {
            final String query = "delete from $tableName where id = ?;";
            final String finishedQuery = query.replace("$tableName", tableName);
            final PreparedStatement preparedStmt = myConn.prepareStatement(finishedQuery);

            preparedStmt.setInt(1, taskId);
            preparedStmt.execute();
            return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAllTasksForProjectFromDatabase(final int projectId) {
        try {
            final String query = "delete from tasks where project_id = ?;";
            final PreparedStatement preparedStmt = myConn.prepareStatement(query);

            preparedStmt.setInt(1, projectId);
            preparedStmt.execute();
            return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAllProjectsFromDatabase() {
        //TODO: implement

        return false;
    }

    //================================================================================
    // Update Functions
    //================================================================================

    /**
     * Updates a task object's information into the database.
     *
     * @param newTitle
     * @param newDescription
     * @param newIsFinished
     * @param id
     * @return
     */

    public static boolean updateTaskInDatabase(final String newTitle,
                                               final String newDescription,
                                               final boolean newIsFinished,
                                               final int newProjectId,
                                               final int id) {
        try {
            final String insertQuery = "update tasks set title = ?, description = ?, is_finished = ?, project_id = ? where id = ?;";
            final PreparedStatement preparedStmt = myConn.prepareStatement(insertQuery);

            preparedStmt.setString(1, newTitle);
            preparedStmt.setString(2, newDescription);
            preparedStmt.setBoolean(3, newIsFinished);
            preparedStmt.setInt(4, newProjectId);
            preparedStmt.setInt(5, id);
            preparedStmt.execute();

            return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    /*********************************************************************************
     *
     * @param newTitle
     * @param newDescription
     * @param newDeadline
     * @param newIsFinished
     * @param newPriority
     * @param id
     * @return
     */
    public static boolean updateProjectInDatabase(String newTitle,
                                                  String newDescription,
                                                  LocalDate newDeadline,
                                                  boolean newIsFinished,
                                                  int newPriority,
                                                  int id) {
        try {
            final String query = "update projects set title = ?, description = ?, deadline = ?, is_finished = ?, priority = ? where id = ?;";

            final PreparedStatement preparedStmt = myConn.prepareStatement(query);

            preparedStmt.setString(1, newTitle);
            preparedStmt.setString(2, newDescription);
            preparedStmt.setObject(3, newDeadline);
            preparedStmt.setBoolean(4, newIsFinished);
            preparedStmt.setInt(5, newPriority);
            preparedStmt.setInt(6, id);

            preparedStmt.execute();
            return true;

        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }
    }


    //================================================================================
    // Retrieval Functions
    //================================================================================

    public static Project retrieveProjectFromDatabase(final int id) {
        Project result = null;

        try {
            final String query = "select * from projects where id = ? ;";

            final PreparedStatement preparedStmt = myConn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            final ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {

                final String title = resultSet.getString("title");
                final String description = resultSet.getString("description");
                final LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                final int priority = resultSet.getInt("priority");
                result = new Project(title, description, deadline, priority, id);

            }
            return result;

        } catch (final SQLException exc) {
            exc.printStackTrace();
            System.out.println("Failed to create product, result is null");
        }
        return result;
    }

    public static Task retrieveTaskFromDatabase(final int id) {
        Task result = null;

        try {
            final String query = "select * from tasks where id = ? ;";

            final PreparedStatement preparedStmt = myConn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            final ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {

                final String title = resultSet.getString("title");
                final String description = resultSet.getString("description");
                final boolean isFinished = resultSet.getBoolean("is_finished");
                result = new Task(title, description, isFinished, id);
            }
            return result;

        } catch (final SQLException exc) {
            exc.printStackTrace();
            System.out.println("Failed to create product, result is null");

        }
        return result;
    }

    public static ArrayList<Task> retrieveProjectTasksFromDatabase(final int projectId) {
        ArrayList<Task> tasks = new ArrayList<>();
        Task newTask;
        final String query = "SELECT * FROM tasks WHERE project_id = ?;";

        try {

            final PreparedStatement statement = myConn.prepareStatement(query);
            statement.setInt(1, projectId);

            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                int id = resultSet.getInt("id");
                newTask = new Task(title, description, projectId, id);
                tasks.add(newTask);
            }
            return tasks;

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return tasks;
    }

    public static ArrayList<Task> retrieveAllTasksFromDatabase() {
        ArrayList<Task> tasks = new ArrayList<>();
        Task newTask;

        final String query = "SELECT * FROM tasks;";
        try {

            final PreparedStatement statement = myConn.prepareStatement(query);

            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                int projectId = resultSet.getInt("project_id");
                int id = resultSet.getInt("id");
                newTask = new Task(title, description, projectId, id);
                tasks.add(newTask);
            }
            return tasks;

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return tasks;
    }


    public static ArrayList<Project> retrieveProjectsFromDatabase() {

        final ArrayList<Project> projects = new ArrayList<>();
        Project newProject = null;

        final String query = "SELECT * FROM projects";

        try {
            final Statement statement = myConn.createStatement();

            final ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                int priority = resultSet.getInt("priority");
                int id = resultSet.getInt("id");
                newProject = new Project(title, description, deadline, priority, id);
                projects.add(newProject);
            }
            return projects;

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return projects;
    }


    //================================================================================
    // Execute Function
    //================================================================================

    public static boolean executeStatement(final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
        //TODO: have Execute return accurate status information
        return true;
    }


    //================================================================================
    // Print Functions
    //================================================================================

    /**
     *
     */
    public static void printTaskTableToConsole() throws SQLException {

        final String query = "SELECT * FROM tasks";
        // 2. Create a statement
        final Statement statement = myConn.createStatement();
        // 3. Execute SQL query
        final ResultSet resultSet = statement.executeQuery(query);
        // 4. Process the result set
        while (resultSet.next()) {
            System.out.println(
                    resultSet.getString("id") + ", " +
                            resultSet.getString("project_id") + ", " +
                            resultSet.getString("title") + ", " +
                            resultSet.getString("description") + ", " +
                            resultSet.getString("is_finished")
            );
        }
        System.out.println();
    }

    /**
     *
     */
    public static void printProjectTableToConsole() throws SQLException {
        final String query = "SELECT * FROM projects";
        final Statement statement = myConn.createStatement();
        final ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            System.out.println(
                    resultSet.getString("id") + ", " +
                            resultSet.getString("title") + ", " +
                            resultSet.getString("description") + ", " +
                            resultSet.getString("deadline") + ", " +
                            resultSet.getString("is_finished") + ", " +
                            resultSet.getString("priority") + ", "
            );
        }
        System.out.println();
    }

    //================================================================================
    // Functions to improve Testing
    //================================================================================

    public static boolean verifyProjectExists(final int projectId) throws SQLException {
        final String query = "SELECT * FROM projects where id = ?;";
        final PreparedStatement preparedStmt = myConn.prepareStatement(query);
        preparedStmt.setInt(1, projectId);
        final ResultSet resultSet = preparedStmt.executeQuery();
        return resultSet.next();
    }

    //================================================================================
    // Initializer Functions
    //================================================================================

    private static void retrieveAndSetMaxTaskId() throws SQLException {
        final String query = "SELECT MAX(id) as max FROM tasks";
        final PreparedStatement preparedStmt = myConn.prepareStatement(query);
        final ResultSet resultSet = preparedStmt.executeQuery();
        final int maxTaskId = resultSet.next() ? resultSet.getInt("max") : 0;

        TaskDatabaseInteraction.setTaskIdOnInit(maxTaskId);
    }

    private static void retrieveAndSetMaxProjectIdNumber() throws SQLException {
        final String query = "SELECT MAX(id) as max FROM projects";

        final PreparedStatement preparedStmt = myConn.prepareStatement(query);
        final ResultSet resultSet = preparedStmt.executeQuery();

        final int answer;
        if (resultSet.next()) {
            answer = resultSet.getInt("max");
        } else {
            answer = 0;
        }

        ProjectDatabaseInteraction.setProjectIdOnInit(answer);
    }


}









