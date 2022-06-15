package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Berth;

import javax.xml.bind.JAXBElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BerthDao extends GenericDao{

    public static Berth getBerth(int berthId) {

        ResultSet rs = executeQuery("SELECT * FROM berth WHERE id = ?;", stmt -> stmt.setInt(1, berthId));
        try {
            rs.next();
            return new Berth(
                    rs.getInt("id"),
                    rs.getInt("terminal"), rs.getTime("open"), rs.getTime("close"), rs.getDouble("unloadSpeed"),
                    rs.getInt("width"),
                    rs.getInt("depth"),
                    rs.getInt("length"));
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
                        rs.getInt("terminal"), rs.getTime("open"), rs.getTime("close"), rs.getDouble("unloadSpeed"),
                        rs.getInt("width"),
                        rs.getInt("depth"),
                        rs.getInt("length")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static void deleteBerth(int berthId) {
        executeUpdate("DELETE FROM berth WHERE id = ?;", stmt -> stmt.setInt(1, berthId));
    }

    public static Berth createBerth(JAXBElement<Berth> berthXML){
        String query = "INSERT INTO berth (unloadspeed, terminal, open, close, width, depth, length) VALUES(?, ?, ?, ?, ?, ?, ?);";
        Berth berth = berthXML.getValue();
        executeUpdate(query, stmt -> {
            stmt.setDouble(1, berth.getUnloadSpeed());
            stmt.setInt(2, berth.getTerminalId());
            stmt.setTime(3, berth.getOpen());
            stmt.setTime(4, berth.getClose());
            stmt.setInt(5, berth.getWidth());
            stmt.setInt(6, berth.getDepth());
            stmt.setInt(7, berth.getLength());
        });
        return berth;
    }

    public static void replaceBerth(int berthId, JAXBElement<Berth> berthXML){
        String query = "UPDATE berth SET unloadSpeed = ?, terminal = ?, open = ?, close = ?, width = ?, depth = ?, length = ? WHERE id = ?;";
        Berth berth = berthXML.getValue();
        executeUpdate(query, stmt -> {
            stmt.setDouble(1, berth.getUnloadSpeed());
            stmt.setInt(2, berth.getTerminalId());
            stmt.setTime(3, berth.getOpen());
            stmt.setTime(4, berth.getClose());
            stmt.setInt(5, berth.getWidth());
            stmt.setInt(6, berth.getDepth());
            stmt.setInt(7, berth.getLength());
            stmt.setInt(8, berthId);
        });

    }



}
