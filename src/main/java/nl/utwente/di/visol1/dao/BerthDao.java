package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Berth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BerthDao extends GenericDao{

    public static Berth getBerth(int berth) {
        ResultSet rs = executeQuery("SELECT * FROM berth WHERE id = ?;", stmt -> stmt.setInt(1, berth));
        try {
            if(!rs.next()) return null;
            return new Berth(
                    rs.getInt("id"),
                    rs.getInt("terminal"),
                    rs.getTime("open"),
                    rs.getTime("close"),
                    rs.getDouble("unload_speed"),
                    rs.getInt("length"),
                    rs.getInt("width"),
                    rs.getInt("depth"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }


    public static Map<Integer, Berth> getBerthsByTerminal(int terminalId){
	    Map<Integer, Berth> res = new HashMap<>();
        ResultSet rs = executeQuery("SELECT * FROM berth WHERE terminal = ?", stmt -> stmt.setInt(1, terminalId));
        try {
						if(!rs.next()) return null;
	        do {
		        res.put(rs.getInt("id"), (new Berth(
			        rs.getInt("id"),
			        rs.getInt("terminal"),
			        rs.getTime("open"),
			        rs.getTime("close"),
			        rs.getDouble("unload_speed"),
			        rs.getInt("length"),
			        rs.getInt("width"),
			        rs.getInt("depth"))));
	        } while (rs.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static int deleteBerth(int berthId) {
        return executeUpdate("DELETE FROM berth WHERE id = ?;", stmt -> stmt.setInt(1, berthId));
    }

    public static Berth createBerth(Berth berth){
        String query = "INSERT INTO berth (terminal, open, close, unload_speed, length, width, depth) VALUES(?, ?, ?, ?, ?, ?, ?) RETURNING *;";
        ResultSet rs = executeQuery(query, stmt -> {
            stmt.setInt(1, berth.getTerminalId());
            stmt.setTime(2, berth.getOpen());
            stmt.setTime(3, berth.getClose());
	        stmt.setDouble(4, berth.getUnloadSpeed());
	        stmt.setInt(5, berth.getLength());
            stmt.setInt(6, berth.getWidth());
            stmt.setInt(7, berth.getDepth());
        });
	    try {
		    if(!rs.next()) return null;
		    return new Berth(
			    rs.getInt("id"),
			    rs.getInt("terminal"),
			    rs.getTime("open"),
			    rs.getTime("close"),
			    rs.getDouble("unload_speed"),
			    rs.getInt("length"),
			    rs.getInt("width"),
			    rs.getInt("depth"));
	    } catch (SQLException exception) {
		    exception.printStackTrace();
		    return null;
	    }
    }

    public static int replaceBerth(int berthId, Berth berth){
        String query = "UPDATE berth SET terminal = ?, open = ?, close = ?, unload_speed = ?, length = ?, width = ?, depth = ? WHERE id = ?;";
        return executeUpdate(query, stmt -> {
            stmt.setInt(1, berth.getTerminalId());
            stmt.setTime(2, berth.getOpen());
            stmt.setTime(3, berth.getClose());
	        stmt.setDouble(4, berth.getUnloadSpeed());
	        stmt.setInt(5, berth.getLength());
            stmt.setInt(6, berth.getWidth());
            stmt.setInt(7, berth.getDepth());
            stmt.setInt(8, berthId);
        });

    }



}
