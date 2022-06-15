package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Vessel;

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
                    rs.getTimestamp("arrival"),
                    rs.getTimestamp("deadline"),
                    rs.getInt("containers"),
                    rs.getDouble("cost_per_hour"),
                    rs.getInt("destination"),
                    rs.getInt("length"),
                    rs.getInt("width"),
                    rs.getInt("depth"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public static void deleteVessel(int vesselId) {
        executeUpdate("DELETE FROM vessel WHERE id = ?", stmt -> stmt.setInt(1, vesselId));
    }

    public static void replaceVessel(int vesselId, Vessel vessel) {
        String query = "UPDATE vessel SET name = ?, arrival = ?, deadline = ?, containers = ?, cost_per_hour = ?, destination = ?, length = ?, width = ?, depth = ? WHERE id = ?";

        executeUpdate(query, stmt -> {
            stmt.setString(1, vessel.getName());
	        stmt.setTimestamp(2, vessel.getArrival());
	        stmt.setTimestamp(3, vessel.getDeadline());
            stmt.setInt(4, vessel.getContainers());
			stmt.setDouble(5, vessel.getCostPerHour());
            stmt.setInt(6, vessel.getDestination());
	        stmt.setInt(7, vessel.getLength());
            stmt.setInt(8, vessel.getWidth());
            stmt.setInt(9, vessel.getDepth());
            stmt.setInt(10, vesselId);
        });
    }


    public static Vessel createVessel(Vessel vessel){
        String query = "INSERT INTO vessel (name, arrival, deadline, containers, cost_per_hour, destination, length, width, depth) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
	      executeUpdate(query, stmt -> {
			    stmt.setString(1, vessel.getName());
			    stmt.setTimestamp(2, vessel.getArrival());
			    stmt.setTimestamp(3, vessel.getDeadline());
			    stmt.setInt(4, vessel.getContainers());
			    stmt.setDouble(5, vessel.getCostPerHour());
			    stmt.setInt(6, vessel.getDestination());
			    stmt.setInt(7, vessel.getLength());
			    stmt.setInt(8, vessel.getWidth());
			    stmt.setInt(9, vessel.getDepth());
        });
        return vessel;
    }

    public static List<Vessel> getVesselsByTerminal(int terminalId){
        List<Vessel> res = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM vessel WHERE destination = ?", stmt -> stmt.setInt(1, terminalId));
        try {
            while(rs.next()) {
                res.add(new Vessel(
		            rs.getInt("id"),
		            rs.getString("name"),
		            rs.getTimestamp("arrival"),
		            rs.getTimestamp("deadline"),
		            rs.getInt("containers"),
		            rs.getDouble("cost_per_hour"),
		            rs.getInt("destination"),
		            rs.getInt("length"),
		            rs.getInt("width"),
		            rs.getInt("depth")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
