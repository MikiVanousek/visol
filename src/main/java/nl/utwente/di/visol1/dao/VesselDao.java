package nl.utwente.di.visol1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import nl.utwente.di.visol1.models.Vessel;

public class VesselDao extends GenericDao {
	public static Vessel getVessel(int vesselId) {
		try (Query query = Query.prepared("SELECT * FROM vessel WHERE id = ?", stmt -> stmt.setInt(1, vesselId))) {
			ResultSet rs = query.getResultSet();
			if (!rs.next()) return null;
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
				rs.getInt("depth")
			);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public static int deleteVessel(int vesselId) {
		try (Update update = Update.prepared("DELETE FROM vessel WHERE id = ?", stmt -> stmt.setInt(1, vesselId))) {
			return update.getRowsChanged();
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}

	public static int replaceVessel(int vesselId, Vessel vessel) {
		if (vessel == null) return -1;
		try (Update update = Update.prepared(
			"UPDATE vessel SET name = ?, arrival = ?, deadline = ?, containers = ?, cost_per_hour = ?, destination = ?, length = ?, width = ?, depth = ? WHERE id = ?",
			stmt -> {
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
			}
		)) {
			return update.getRowsChanged();
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}

	public static Vessel createVessel(Vessel vessel) {
		if (vessel == null) return null;
		try (Query query = Query.prepared(
			"INSERT INTO vessel (name, arrival, deadline, containers, cost_per_hour, destination, length, width, depth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *",
			stmt -> {
				stmt.setString(1, vessel.getName());
				stmt.setTimestamp(2, vessel.getArrival());
				stmt.setTimestamp(3, vessel.getDeadline());
				stmt.setInt(4, vessel.getContainers());
				stmt.setDouble(5, vessel.getCostPerHour());
				stmt.setInt(6, vessel.getDestination());
				stmt.setInt(7, vessel.getLength());
				stmt.setInt(8, vessel.getWidth());
				stmt.setInt(9, vessel.getDepth());
			}
		)) {
			ResultSet rs = query.getResultSet();
			if (!rs.next()) return null;
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
				rs.getInt("depth")
			);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public static Map<Integer, Vessel> getVesselsByTerminal(int terminalId) {
		Map<Integer, Vessel> result = new HashMap<>();
		try (Query query = Query.prepared("SELECT * FROM vessel WHERE destination = ?", stmt -> stmt.setInt(1, terminalId))) {
			ResultSet rs = query.getResultSet();
			while (rs.next()) {
				result.put(
					rs.getInt("id"),
					new Vessel(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getTimestamp("arrival"),
						rs.getTimestamp("deadline"),
						rs.getInt("containers"),
						rs.getDouble("cost_per_hour"),
						rs.getInt("destination"),
						rs.getInt("length"),
						rs.getInt("width"),
						rs.getInt("depth")
					)
				);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return result;
	}
}
