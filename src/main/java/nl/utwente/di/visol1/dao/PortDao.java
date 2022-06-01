package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Port;

import javax.xml.bind.JAXBElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PortDao extends GenericDao {
    private final static String table = "port";

    public static Port getPort(int portId) {
        ResultSet rs = executeQuery("SELECT * FROM port WHERE id = ?", stmt -> stmt.setInt(1, portId));

        try {
            return new Port(
                    rs.getInt("id"),
                    rs.getString("name")
            );
        } catch (SQLException | NullPointerException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static List<Port> getPorts(){
        List<Port> res = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM port");

        try {
            while(rs.next()) {
                res.add(new Port(
                        rs.getInt("id"),
                        rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static void replacePort(int portId, JAXBElement<Port> portXML) {
        String query = "UPDATE port SET name = ? WHERE id = ?;";
        Port port = portXML.getValue();
        executeUpdate(query, stmt -> {
            stmt.setString(1, port.getName());
            stmt.setInt(2, portId);
        });
    }


    public static Port createPort(JAXBElement<Port> portXML){
        String query = "INSERT INTO port (name) VALUES(?);";
        Port port = portXML.getValue();
        executeUpdate(query, stmt -> stmt.setString(1, port.getName()));
        return port;
    }

    public static void deletePortById(int portId) {
        executeUpdate("DELETE FROM port WHERE id = ?;", stmt -> stmt.setInt(1, portId));
    }


}
