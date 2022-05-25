package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Port;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PortDao extends GenericDao {
    private final static String table = "port";

    public Port getPort(int portId) {
        ResultSet rs = executeQuery("SELECT * FROM port WHERE id = ?", stmt -> stmt.setInt(1, portId));

        try {
            return new Port(
                    rs.getInt("id"),
                    rs.getString("name")
            );
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
