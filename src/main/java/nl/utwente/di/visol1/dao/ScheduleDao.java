package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleDao extends GenericDao {
    private final static String table = "schedule"; //needed??

    public Schedule getSchedule(int scheduleId) {
        ResultSet rs = executeQuery("SELECT * FROM schedule WHERE id = ?", stmt -> stmt.setInt(1, scheduleId));

        try {
            //public Schedule(Future<Vessel> vessel, Future<Berth> berth, boolean manual, Timestamp start, Timestamp finish) {
            //TODO: add promises for vessel and berth
            return new Schedule(
                    rs.getInt("vessel"),
                    rs.getInt("berth"),
                    rs.getBoolean("manual"),
                    rs.getTimestamp("start"),
                    rs.getTimestamp("finish")
            );
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
