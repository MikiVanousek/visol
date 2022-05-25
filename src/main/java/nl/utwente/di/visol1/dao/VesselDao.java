package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Vessel;

import javax.ws.rs.core.Request;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VesselDao extends GenericDao{
    private final static String table = "vessel"; //needed??

    public Vessel getVessel(int vesselId) {
        ResultSet rs = executeQuery("SELECT * FROM vessel WHERE id = ?", stmt -> stmt.setInt(1, vesselId));

        try {
            //public Vessel(int id, String name, int containerAmount, double cost, Terminal destinationTerminal, Timestamp eta, Timestamp deadline, int width, int length, int depth)
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
                    rs.getInt("depth")
            );
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
