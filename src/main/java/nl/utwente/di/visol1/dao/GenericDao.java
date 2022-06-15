package nl.utwente.di.visol1.dao;

import java.sql.*;
import java.util.function.Supplier;

public abstract class GenericDao {
    @FunctionalInterface
    interface PrepareStatement {
        void inject(PreparedStatement preparedStatement) throws SQLException;
    }

    //public or private?
    private static final String HOST = "bronto.ewi.utwente.nl";
    private static final String DB_NAME = "dab_di21222b_138";
    private static String SCHEMA;
    private static final Supplier<String> URL = () -> "jdbc:postgresql://" + HOST + ":5432/" + DB_NAME + "?currentSchema=" + SCHEMA;
    private static final String USERNAME = DB_NAME;
    private static final String PASSWORD = System.getenv("db_password");

    private static Connection connection;


    static {
        useTestSchema(false);
    }

    protected GenericDao() {

    }

    public static void truncateAllTables() {
        String[] tables = {"schedule", "vessel", "berth", "terminal", "port"};
        for (String table : tables) {
            executeUpdate("truncate table " + table + " restart identity cascade");

        }
    }

    public static void useTestSchema(boolean useTestSchema) {
        SCHEMA = useTestSchema ? "visol_project_test" : "visol_project";
    }

    protected static ResultSet executeQuery(String query) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        }

        try {
            connection = DriverManager.getConnection(URL.get(), USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;

        } catch (SQLException sqle) {
            System.err.println("Sql error: " + sqle);
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected static int executeUpdate(String query) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        }

        try {
            connection = DriverManager.getConnection(URL.get(), USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            int res = statement.executeUpdate(query);
            return res;

        } catch (SQLException sqle) {
            System.err.println("Sql error: " + sqle);
            return -1;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected static ResultSet executeQuery(String query, PrepareStatement prepare) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        }

        try {
            connection = DriverManager.getConnection(URL.get(), USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);
            prepare.inject(statement);
            ResultSet rs = statement.executeQuery();
            return rs;


        } catch (SQLException sqle) {
            System.err.println("Sql error: " + sqle);
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected static int executeUpdate(String query, PrepareStatement prepare) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        }

        try {
            connection = DriverManager.getConnection(URL.get(), USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);
            prepare.inject(statement);
            int res = statement.executeUpdate();
            return res;

        } catch (SQLException sqle) {
            System.err.println("Sql error: " + sqle);
            return -1;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
