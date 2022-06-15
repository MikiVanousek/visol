package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Vessel;

import javax.xml.bind.JAXBElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VesselDao extends GenericDao{
    private final static String table = "vessel"; //needed??

    public static Vessel getVessel(int vesselId) {
        ResultSet rs = executeQuery("SELECT * FROM vessel WHERE id = ?", stmt -> stmt.setInt(1, vesselId));

        try {
            //TODO: destinationTerminal promise
            rs.next();
            return new Vessel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("containers"),
                    rs.getInt("dest_terminal"),
                    rs.getTimestamp("eta"),
                    rs.getTimestamp("deadline"),
                    rs.getInt("width"),
                    rs.getInt("length"),
                    rs.getInt("depth"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public static void deleteVessel(int vesselId) {
        executeUpdate("DELETE FROM vessel WHERE id = ?", stmt -> stmt.setInt(1, vesselId));
    }

    public static void replaceVessel(int vesselId, JAXBElement<Vessel> vesselXML) {
        Vessel vessel = vesselXML.getValue();
        String query = "UPDATE vessel SET name = ?, containers = ?, dest_terminal = ?, eta = ?, deadline = ?, width = ?, length = ?, depth = ? WHERE id = ?";

        executeUpdate(query, stmt -> {
            stmt.setString(1, vessel.getName());
            stmt.setInt(2, vessel.getContainers());
            stmt.setInt(3, vessel.getDestinationTerminalId());
            stmt.setTimestamp(4, vessel.getEta());
            stmt.setTimestamp(5, vessel.getDeadline());
            stmt.setInt(6, vessel.getWidth());
            stmt.setInt(7, vessel.getLength());
            stmt.setInt(8, vessel.getDepth());
            stmt.setInt(9, vesselId);
        });
    }


    public static Vessel createVessel(JAXBElement<Vessel> vesselXML){
        String query = "INSERT INTO vessel (name, containers,dest_terminal, eta, deadline, width, length, depth) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        Vessel vessel = vesselXML.getValue();
        executeUpdate(query, stmt -> {
            stmt.setString(1, vessel.getName());
            stmt.setInt(2, vessel.getContainers());
            stmt.setInt(3, vessel.getDestinationTerminalId());
            stmt.setTimestamp(4, vessel.getEta());
            stmt.setTimestamp(5, vessel.getDeadline());
            stmt.setInt(6, vessel.getWidth());
            stmt.setInt(7, vessel.getLength());
            stmt.setInt(8, vessel.getDepth());
        });
        return vessel;
    }

    public static List<Vessel> getVesselsByTerminal(int terminalId){
        List<Vessel> res = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM vessel WHERE dest_terminal = ?", stmt -> stmt.setInt(1, terminalId));
        try {
            while(rs.next()) {
                res.add(new Vessel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("containers"),
                        rs.getInt("dest_terminal"),
                        rs.getTimestamp("eta"),
                        rs.getTimestamp("deadline"),
                        rs.getInt("width"),
                        rs.getInt("length"),
                        rs.getInt("depth")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
