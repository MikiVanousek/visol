package nl.utwente.di.visol1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.utwente.di.visol1.models.ScheduleChange;
import nl.utwente.di.visol1.models.VesselChange;

public class VesselChangeDao {
	public static VesselChange createVesselChange(VesselChange vesselChange){
		try (GenericDao.Query query = GenericDao.Query.prepared("INSERT INTO vesselchange (vessel, date, old, new, reason) VALUES(?, ?, "
		                                                        + "?::jsonb, ?::jsonb, ?) RETURNING *;", stmt -> {
			stmt.setInt(1, vesselChange.getVessel());
			stmt.setTimestamp(2, vesselChange.getDate());
			stmt.setString(3, vesselChange.getOldVessel().toString());
			stmt.setString(4, vesselChange.getNewVessel().toString());
			stmt.setString(5, vesselChange.getReason());
		})){
			ResultSet rs = query.getResultSet();
			if (!rs.next()) return null;
			return new VesselChange(
				rs.getInt("vessel"),
				rs.getTimestamp("date"),
				rs.getString("old"),
				rs.getString("new"),
				rs.getString("reason")
			);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public static List<VesselChange> getVesselChangesByVessel(int vesselId, Timestamp from, Timestamp to){
		List<VesselChange> result = new ArrayList<>();
		try (GenericDao.Query query = GenericDao.Query.prepared("SELECT * FROM vesselchange WHERE vessel = ? AND date >= ? AND date <= ?", stmt -> {
			stmt.setInt(1, vesselId);
			stmt.setTimestamp(2, from);
			stmt.setTimestamp(3, to);
		})) {
			ResultSet rs = query.getResultSet();
			while (rs.next()) {
				result.add(new VesselChange(
					rs.getInt("vessel"),
					rs.getTimestamp("date"),
					rs.getString("old"),
					rs.getString("new"),
					rs.getString("reason")
				));
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return result;
	}



	public static Map<Integer, List<VesselChange>> getVesselChanges(Timestamp from, Timestamp to){
		Map <Integer, List<VesselChange>> result = new HashMap<>();
		try (GenericDao.Query query = GenericDao.Query.prepared("SELECT * FROM vesselchange WHERE date >= ? AND date <= ?", stmt -> {
			stmt.setTimestamp(1, from);
			stmt.setTimestamp(2, to);
		})) {
			ResultSet rs = query.getResultSet();
			while (rs.next()) {
				result.computeIfAbsent(rs.getInt("vessel"), k -> new ArrayList<>()).add(new VesselChange(
					rs.getInt("vessel"),
					rs.getTimestamp("date"),
					rs.getString("old"),
					rs.getString("new"),
					rs.getString("reason")
				));
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return result;
	}

	public static VesselChange getVesselChangeByDate(int vesselId, Timestamp date){
		try (GenericDao.Query query = GenericDao.Query.prepared("SELECT * FROM vesselchange WHERE vessel = ? AND date = ?", stmt -> {
			stmt.setInt(1, vesselId);
			stmt.setTimestamp(2, date);
		})) {
			ResultSet rs = query.getResultSet();
			if (!rs.next()) return null;
			return new VesselChange(
				rs.getInt("vessel"),
				rs.getTimestamp("date"),
				rs.getString("old"),
				rs.getString("new"),
				rs.getString("reason")
			);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public static int deleteVesselChangeByDate(int vesselId, Timestamp date) {
		try (GenericDao.Update update = GenericDao.Update.prepared("DELETE FROM vesselchange WHERE vessel = ? AND date = ?", stmt -> {
			stmt.setInt(1, vesselId);
			stmt.setTimestamp(2, date);
		})) {
			return update.getRowsChanged();
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}

	public static int deleteVesselChanges(int vesselId, Timestamp from, Timestamp to) {
		try (GenericDao.Update update = GenericDao.Update.prepared("DELETE FROM vesselchange WHERE vessel = ? AND date >= ? AND date <= ?", stmt -> {
			stmt.setInt(1, vesselId);
			stmt.setTimestamp(2, from);
			stmt.setTimestamp(3, to);
		})) {
			return update.getRowsChanged();
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}


}
