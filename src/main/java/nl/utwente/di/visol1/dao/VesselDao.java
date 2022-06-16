package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Vessel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VesselDao extends GenericDao{
    private final static String table = "vessel"; //needed??

    public static Vessel getVessel(int vesselId) {
        ResultSet rs = executeQuery("SELECT * FROM vessel WHERE id = ?", stmt -> stmt.setInt(1, vesselId));
        try {
            if(!rs.next()) return null;
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
    public static int deleteVessel(int vesselId) {
        return executeUpdate("DELETE FROM vessel WHERE id = ?", stmt -> stmt.setInt(1, vesselId));
    }

    public static int replaceVessel(int vesselId, Vessel vessel) {
        String query = "UPDATE vessel SET name = ?, arrival = ?, deadline = ?, containers = ?, cost_per_hour = ?, destination = ?, length = ?, width = ?, depth = ? WHERE id = ?";

        return executeUpdate(query, stmt -> {
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
        String query = "INSERT INTO vessel (name, arrival, deadline, containers, cost_per_hour, destination, length, width, depth) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *;";
	      ResultSet rs = executeQuery(query, stmt -> {
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
	    try {
		    if(!rs.next()) return null;
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

    public static Map<Integer, Vessel> getVesselsByTerminal(int terminalId){
	    Map<Integer, Vessel>res = new HashMap<>();
        ResultSet rs = executeQuery("SELECT * FROM vessel WHERE destination = ?", stmt -> stmt.setInt(1, terminalId));
        try {
	          if(!rs.next()) return null;
            do{
                res.put(rs.getInt("id"), new Vessel(
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
            } while(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
