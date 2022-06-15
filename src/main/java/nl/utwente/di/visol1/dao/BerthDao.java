package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Berth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BerthDao extends GenericDao{

    public static Berth getBerth(int berth) {

        ResultSet rs = executeQuery("SELECT * FROM berth WHERE id = ?;", stmt -> stmt.setInt(1, berth));
        try {
            rs.next();
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


    public static List<Berth> getBerthsByTerminal(int terminalId){
        List<Berth> res = new ArrayList<>();
        ResultSet rs = executeQuery("SELECT * FROM berth WHERE terminal = ?", stmt -> stmt.setInt(1, terminalId));
        try {
            while(rs.next()) {
                res.add(new Berth(
                        rs.getInt("id"),
                        rs.getInt("terminal"),
                        rs.getTime("open"),
                        rs.getTime("close"),
                        rs.getDouble("unload_speed"),
                        rs.getInt("length"),
                        rs.getInt("width"),
                        rs.getInt("depth")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static void deleteBerth(int berthId) {
        executeUpdate("DELETE FROM berth WHERE id = ?;", stmt -> stmt.setInt(1, berthId));
    }

    public static Berth createBerth(Berth berth){
        String query = "INSERT INTO berth (terminal, open, close, unload_speed, length, width, depth) VALUES(?, ?, ?, ?, ?, ?, ?);";
        executeUpdate(query, stmt -> {
            stmt.setInt(1, berth.getTerminalId());
            stmt.setTime(2, berth.getOpen());
            stmt.setTime(3, berth.getClose());
	        stmt.setDouble(4, berth.getUnloadSpeed());
	        stmt.setInt(5, berth.getLength());
            stmt.setInt(6, berth.getWidth());
            stmt.setInt(7, berth.getDepth());
        });
        return berth;
    }

    public static void replaceBerth(int berthId, Berth berth){
        String query = "UPDATE berth SET terminal = ?, open = ?, close = ?, unload_speed = ?, length = ?, width = ?, depth = ? WHERE id = ?;";
        executeUpdate(query, stmt -> {
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
