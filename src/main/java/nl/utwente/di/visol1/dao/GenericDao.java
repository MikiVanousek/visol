package nl.utwente.di.visol1.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class GenericDao {
    private String host;
    private String dbName;
    private String url;
    private String username;
    private String password;

    private Connection connection;
    private Statement statement;
    private String table;

    public static ResultSet executeQuery(String query){
        return null;
    }

}
