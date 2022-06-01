package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Terminal;

import javax.xml.bind.JAXBElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TerminalDao extends GenericDao{
    private final static String table = "terminal"; //needed??

    public static Terminal getTerminal(int terminalId) {
        ResultSet rs = executeQuery("SELECT * FROM terminal WHERE id = ?", stmt -> stmt.setInt(1, terminalId));
        try {
            return new Terminal(
                    rs.getInt("id"),
                    rs.getTime("open"),
                    rs.getTime("close"),
                    rs.getInt("port"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void deleteTerminal(int terminalId) {
        executeUpdate("DELETE FROM terminal WHERE id = ?", stm -> stm.setInt(1, terminalId));
    }

    public static void replaceTerminal(int terminalId, JAXBElement<Terminal> terminalXML){
        String query = "UPDATE terminal SET open = ?, close = ?, port = ? where id = ?";
        Terminal terminal = terminalXML.getValue();
        executeUpdate(query, stmt -> {
            stmt.setTime(1, terminal.getOpen());
            stmt.setTime(2, terminal.getClose());
            stmt.setInt(3, terminal.getPortId());
            stmt.setInt(4, terminalId);
        });

    }

    public static List<Terminal> getTerminalsByPort(int portId) {
        ResultSet rs = executeQuery("SELECT * FROM terminal WHERE port = ?", stmt -> stmt.setInt(1, portId));
        List<Terminal> res = new ArrayList<>();
        try {
            while(rs.next()) {
                res.add(new Terminal(
                        rs.getInt("id"),
                        rs.getTime("open"),
                        rs.getTime("close"),
                        rs.getInt("port")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Terminal createTerminal(JAXBElement<Terminal> terminalXML){
        String query = "INSERT INTO terminal (open, close, port) VALUES(?, ?, ?);";
        Terminal terminal = terminalXML.getValue();
        executeUpdate(query, stmt -> {
            stmt.setTime(1, terminal.getOpen());
            stmt.setTime(2, terminal.getClose());
            stmt.setInt(3, terminal.getPortId());
        });
        return terminal;
    }
}
