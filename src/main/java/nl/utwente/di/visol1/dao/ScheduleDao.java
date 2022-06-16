package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleDao extends GenericDao {
    private final static String table = "schedule"; //needed??

    public static Schedule getScheduleByVessel(int vessel){
        ResultSet rs = executeQuery("SELECT * FROM schedule WHERE vessel = ?", stmt -> stmt.setInt(1, vessel));
        try {
	         if(!rs.next()) return null;
            return new Schedule(
                    rs.getInt("vessel"),
                    rs.getInt("berth"),
                    rs.getBoolean("manual"),
                    rs.getTimestamp("start"),
                    rs.getTimestamp("expected_end")
            );
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static List<Schedule> getSchedulesByBerth(int berthId, Timestamp from, Timestamp to) {
        List<Schedule> res = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM schedule s WHERE s.berth = ? AND ((s.start >= ? AND s.start <= ?) OR (s.expected_end >= ? AND s.expected_end <= ?))",
                stmt -> {
                    stmt.setInt(1, berthId);
                    stmt.setTimestamp(2, from);
                    stmt.setTimestamp(3, to);
                    stmt.setTimestamp(4, from);
                    stmt.setTimestamp(5, to);
                });
        try {
	        if(!rs.next()) return null;
            do {
                res.add(new Schedule(
                        rs.getInt("vessel"),
                        rs.getInt("berth"),
                        rs.getBoolean("manual"),
                        rs.getTimestamp("start"),
                        rs.getTimestamp("expected_end")));
            }while(rs.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Map<Integer, Map<Integer, List<Schedule>>> getSchedulesByPort(int portId, Timestamp from, Timestamp to) {
        Map<Integer, Map<Integer, List<Schedule>>> res = new HashMap<>();
        ResultSet rs = executeQuery("SELECT s.*, b.terminal FROM schedule s, berth b, terminal t " +
                "WHERE s.berth = b.id AND b.terminal = t.id AND t.port = ?" +
                "AND ((s.start >= ? AND s.start <= ?) OR (s.expected_end >= ? AND s.expected_end <= ?))", stmt -> {
            stmt.setInt(1, portId);
            stmt.setTimestamp(2, from);
            stmt.setTimestamp(3, to);
            stmt.setTimestamp(4, from);
            stmt.setTimestamp(5, to);
        });

        try {
	        if(!rs.next()) return null;
            do {
                int terminal = rs.getInt("terminal");
                int berth = rs.getInt("berth");
                res.putIfAbsent(terminal, new HashMap<>());
                res.get(terminal).putIfAbsent(berth, new ArrayList<>());
                res.get(terminal).get(berth).add(new Schedule(
                        rs.getInt("vessel"),
                        berth,
                        rs.getBoolean("manual"),
                        rs.getTimestamp("start"),
                        rs.getTimestamp("expected_end")));
            } while(rs.next());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return res;
    }

    public static Map<Integer, List<Schedule>> getSchedulesByTerminal(int terminalId, Timestamp from ,Timestamp to) {
        Map<Integer, List<Schedule>> res = new HashMap<>();
        ResultSet rs = executeQuery("SELECT s.* FROM schedule s, berth b " +
                "WHERE s.berth = b.id AND b.terminal = ? " +
                "AND ((s.start >= ? AND s.start <= ?) OR (s.expected_end >= ? AND s.expected_end <= ?))", stmt -> {
            stmt.setInt(1, terminalId);
            stmt.setTimestamp(2, from);
            stmt.setTimestamp(3, to);
            stmt.setTimestamp(4, from);
            stmt.setTimestamp(5, to);
        });

        try {
	          if(!rs.next()) return null;
            do {
                int berth = rs.getInt("berth");
                res.putIfAbsent(berth, new ArrayList<>());
                res.get(berth).add(new Schedule(
                        rs.getInt("vessel"),
                        berth,
                        rs.getBoolean("manual"),
                        rs.getTimestamp("start"),
                        rs.getTimestamp("expected_end")));
            } while(rs.next());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return res;
    }

    public static int deleteScheduleByVessel(int vesselId) {
        return executeUpdate("DELETE FROM schedule where vessel = ?", stmt -> stmt.setInt(1, vesselId));
    }



    public static Schedule replaceSchedule(int vesselId, Schedule schedule){
        String query = "INSERT INTO schedule (vessel, berth, manual, start, expected_end) VALUES(?, ?, ?, ?, ?) " +
                "ON CONFLICT (vessel) DO UPDATE SET berth = ?, manual = ?, start = ?, expected_end = ? RETURNING *";

        ResultSet rs = executeQuery(query, stmt -> {
            stmt.setInt(1, vesselId);
            stmt.setInt(2, schedule.getBerth());
            stmt.setBoolean(3, schedule.isManual());
            stmt.setTimestamp(4, schedule.getStart());
            stmt.setTimestamp(5, schedule.getExpectedEnd());
            stmt.setInt(6, schedule.getBerth());
            stmt.setBoolean(7, schedule.isManual());
            stmt.setTimestamp(8, schedule.getStart());
            stmt.setTimestamp(9, schedule.getExpectedEnd());
        });
	    try {
		    if(!rs.next()) return null;
		    //TODO: add promises for vessel and berth
		    return new Schedule(
			    rs.getInt("vessel"),
			    rs.getInt("berth"),
			    rs.getBoolean("manual"),
			    rs.getTimestamp("start"),
			    rs.getTimestamp("expected_end")
		    );
	    } catch (SQLException exception) {
		    exception.printStackTrace();
		    return null;
	    }
    }
}
