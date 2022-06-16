package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Port;

import javax.xml.bind.JAXBElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortDao extends GenericDao {
    private final static String table = "port";

    public static Port getPort(int portId) {
        ResultSet rs = executeQuery("SELECT * FROM port WHERE id = ?", stmt -> stmt.setInt(1, portId));

        try {
	          if(!rs.next()) return null;
            return new Port(
                    rs.getInt("id"),
                    rs.getString("name")
            );
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Map<Integer,Port> getPorts(){
				Map<Integer, Port> res = new HashMap<>();
        ResultSet rs = executeQuery("SELECT * FROM port");

        try {
	        if(!rs.next()) return null;
            do {
                res.put(rs.getInt("id"),new Port(
                        rs.getInt("id"),
                        rs.getString("name")));
            } while(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
						return null;
        }
        return res;
    }
    public static int replacePort(int portId, Port port) {
        String query = "UPDATE port SET name = ? WHERE id = ?;";
        return executeUpdate(query, stmt -> {
            stmt.setString(1, port.getName());
            stmt.setInt(2, portId);
        });
    }


    public static Port createPort(Port port){
        String query = "INSERT INTO port (name) VALUES(?) RETURNING *;";
        ResultSet rs = executeQuery(query, stmt -> stmt.setString(1, port.getName()));
	    try {
		    if(!rs.next()) return null;
		    return new Port(
			    rs.getInt("id"),
			    rs.getString("name")
		    );
	    } catch (SQLException | NullPointerException exception) {
		    exception.printStackTrace();
		    return null;
	    }
    }

    public static int deletePort(int portId) {
        return executeUpdate("DELETE FROM port WHERE id = ?;", stmt -> stmt.setInt(1, portId));
    }


}
