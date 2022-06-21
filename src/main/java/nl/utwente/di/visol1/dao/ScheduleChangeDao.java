package nl.utwente.di.visol1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.ScheduleChange;
import nl.utwente.di.visol1.models.Vessel;

public class ScheduleChangeDao extends GenericDao{

	public static ScheduleChange createScheduleChange(ScheduleChange scheduleChange){
		try (Query query = Query.prepared("INSERT INTO schedulechange (vessel, date, old, new, reason) VALUES(?, ?, "
		                                  + "(SELECT to_jsonb(schedule) FROM schedule WHERE vessel = ?), ?::jsonb, ?) RETURNING *;", stmt -> {
			stmt.setInt(1, scheduleChange.getVessel());
			stmt.setTimestamp(2, scheduleChange.getDate());
			stmt.setInt(3, scheduleChange.getVessel());
			stmt.setString(4, scheduleChange.getNewSchedule());
			stmt.setString(5, scheduleChange.getReason());
		})){
			ResultSet rs = query.getResultSet();
			if (!rs.next()) return null;
			return new ScheduleChange(
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

	public static List<ScheduleChange> getScheduleChangesByVessel(int vesselId, Timestamp from, Timestamp to){
		List<ScheduleChange> result = new ArrayList<>();
		try (Query query = Query.prepared("SELECT * FROM schedulechange WHERE vessel = ? AND date >= ? AND date <= ?", stmt -> {
			stmt.setInt(1, vesselId);
			stmt.setTimestamp(2, from);
			stmt.setTimestamp(3, to);
		})) {
			ResultSet rs = query.getResultSet();
			while (rs.next()) {
				result.add(new ScheduleChange(
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

	public static ScheduleChange getScheduleChangeByDate(int vesselId, Timestamp date){
		try (Query query = Query.prepared("SELECT * FROM schedulechange WHERE vessel = ? AND date = ?", stmt -> {
			stmt.setInt(1, vesselId);
			stmt.setTimestamp(2, date);
		})) {
			ResultSet rs = query.getResultSet();
			if (!rs.next()) return null;
			return new ScheduleChange(
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

	public static Map<Integer, List<ScheduleChange>> getScheduleChanges(Timestamp from, Timestamp to){
		Map <Integer, List<ScheduleChange>> result = new HashMap<>();
		try (Query query = Query.prepared("SELECT * FROM schedulechange WHERE date >= ? AND date <= ?", stmt -> {
			stmt.setTimestamp(1, from);
			stmt.setTimestamp(2, to);
		})) {
			ResultSet rs = query.getResultSet();
			while (rs.next()) {
				result.computeIfAbsent(rs.getInt("vessel"), k -> new ArrayList<>()).add(new ScheduleChange(
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

	public static int deleteScheduleChanges(int vesselId, Timestamp from, Timestamp to) {
		try (Update update = Update.prepared("DELETE FROM schedulechange WHERE vessel = ? AND date >= ? AND date <= ?", stmt -> {
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

	public static int deleteScheduleChangeByDate(int vesselId, Timestamp date) {
		try (Update update = Update.prepared("DELETE FROM schedulechange WHERE vessel = ? AND date = ?", stmt -> {
			stmt.setInt(1, vesselId);
			stmt.setTimestamp(2, date);
		})) {
			return update.getRowsChanged();
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}
}
