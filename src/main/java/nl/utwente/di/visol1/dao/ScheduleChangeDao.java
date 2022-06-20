package nl.utwente.di.visol1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;

import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.ScheduleChange;
import nl.utwente.di.visol1.models.Vessel;

public class ScheduleChangeDao extends GenericDao{

	public static ScheduleChange createScheduleChange(ScheduleChange scheduleChange){
		String query = "INSERT INTO schedulechange (vessel, date, old, new, reason) VALUES(?, ?, "
		               + "(SELECT to_jsonb(schedule) FROM schedule WHERE vessel = 1), ?::jsonb, ?) RETURNING *;";
		ResultSet rs = executeQuery(query, stmt -> {
			stmt.setInt(1, scheduleChange.getVessel());
			stmt.setTimestamp(2, scheduleChange.getDate());
			stmt.setInt(3, scheduleChange.getVessel());
			stmt.setString(4, scheduleChange.getNewSchedule());
			stmt.setString(5, scheduleChange.getReason());

		});
		try {
			if(!rs.next()) return null;
			return new ScheduleChange(
				rs.getInt("vessel"),
				rs.getTimestamp("date"),
				rs.getString("old"),
				rs.getString("new"),
				rs.getString("reason"));
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
}
