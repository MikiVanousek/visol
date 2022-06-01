package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Vessel;

import javax.ws.rs.core.Request;
import javax.xml.bind.JAXBElement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VesselDao extends GenericDao{
    private final static String table = "vessel"; //needed??

    public static Vessel getVessel(int vesselId) {
        ResultSet rs = executeQuery("SELECT * FROM vessel WHERE id = ?", stmt -> stmt.setInt(1, vesselId));

        try {
            //TODO: destinationTerminal promise
            return new Vessel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("containerAmount"),
                    rs.getDouble("cost"),
                    rs.getInt("terminal"),
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
        String query = "UPDATE vessel SET name = ?, containerAmount = ?, cost = ?, terminal = ?, eta = ?, deadline = ?, width = ?, length = ?, depth = ? WHERE id = ?";

        executeUpdate(query, stmt -> {
            stmt.setString(1, vessel.getName());
            stmt.setInt(2, vessel.getContainerAmount());
            stmt.setDouble(3, vessel.getCost());
            stmt.setInt(4, vessel.getDestinationTerminalId());
            stmt.setTimestamp(5, vessel.getEta());
            stmt.setTimestamp(6, vessel.getDeadline());
            stmt.setInt(7, vessel.getWidth());
            stmt.setInt(8, vessel.getLength());
            stmt.setInt(9, vessel.getDepth());
            stmt.setInt(10, vesselId);
        });
    }


    public static Vessel createVessel(JAXBElement<Vessel> vesselXML){
        String query = "INSERT INTO berth (name, containerAmount, cost, terminal, eta, deadline, width, length, depth) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Vessel vessel = vesselXML.getValue();
        executeUpdate(query, stmt -> {
            stmt.setString(1, vessel.getName());
            stmt.setInt(2, vessel.getContainerAmount());
            stmt.setDouble(3, vessel.getCost());
            stmt.setInt(4, vessel.getDestinationTerminalId());
            stmt.setTimestamp(5, vessel.getEta());
            stmt.setTimestamp(6, vessel.getDeadline());
            stmt.setInt(7, vessel.getWidth());
            stmt.setInt(8, vessel.getLength());
            stmt.setInt(9, vessel.getDepth());
        });
        return vessel;
    }
}
