package nl.utwente.di.visol1.dao;

import java.sql.*;

public abstract class GenericDao {
    @FunctionalInterface
    interface PrepareStatement {
        void inject(PreparedStatement preparedStatement) throws SQLException;
    }

    //public or private?
    private static final String HOST = "bronto.ewi.utwente.nl";
    private static final String DB_NAME = "dab_di21222b_138";
    private static final String URL = "jdbc:postgresql://" + HOST + ":5432/" + DB_NAME + "?currentSchema=visol_project";
    private static final String USERNAME = DB_NAME;
    private static final String PASSWORD = "";


    protected GenericDao() {

    }

    protected static ResultSet executeQuery(String query) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            connection.close();

            return rs;

        } catch (SQLException sqle) {
            System.err.println("Sql error: " + sqle);
            return null;
        }
    }

    protected static ResultSet executeQuery(String query, PrepareStatement prepare) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);
            prepare.inject(statement);
            ResultSet rs = statement.executeQuery();
            connection.close();

            return rs;

        } catch (SQLException sqle) {
            System.err.println("Sql error: " + sqle);
            return null;
        }

    }

    protected static int executeUpdate(String query, PrepareStatement prepare) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);
            prepare.inject(statement);
            int res = statement.executeUpdate();
            connection.close();

            return res;

        } catch (SQLException sqle) {
            System.err.println("Sql error: " + sqle);
            return -1;
        }
    }

}
