package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Berth;

import javax.xml.bind.JAXBElement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BerthDao extends GenericDao{

    public static Berth getBerth(int berthId) {

        ResultSet rs = executeQuery("SELECT * FROM berth WHERE id = ?;", stmt -> stmt.setInt(1, berthId));
        try {
            return new Berth(
                    rs.getInt("id"),
                    rs.getDouble("unloadSpeed"),
                    rs.getInt("terminal"),
                    rs.getTime("open"),
                    rs.getTime("close"),
                    rs.getInt("width"),
                    rs.getInt("depth"),
                    rs.getInt("length"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void deleteBerth(int berthId) {
        executeUpdate("DELETE FROM berth WHERE id = ?;", stmt -> stmt.setInt(1, berthId));
    }

    public static void createBerth(JAXBElement<Berth> berthXML){
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
