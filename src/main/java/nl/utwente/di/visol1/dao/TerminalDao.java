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
            rs.next();
            return new Terminal(
                    rs.getInt("id"),
                    rs.getInt("port"),
                    rs.getString("name"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void deleteTerminal(int terminalId) {
        executeUpdate("DELETE FROM terminal WHERE id = ?", stm -> stm.setInt(1, terminalId));
    }

    public static void replaceTerminal(int terminalId, Terminal terminal){
        String query = "UPDATE terminal SET port = ?, name = ? WHERE id = ?";
        executeUpdate(query, stmt -> {
            stmt.setInt(1, terminal.getPortId());
	          stmt.setString(2, terminal.getName());
	          stmt.setInt(3, terminalId);
        });

    }

    public static List<Terminal> getTerminalsByPort(int portId) {
        ResultSet rs = executeQuery("SELECT * FROM terminal WHERE port = ?", stmt -> stmt.setInt(1, portId));
        List<Terminal> res = new ArrayList<>();
        try {
            while(rs.next()) {
                res.add(new Terminal(
                        rs.getInt("id"),
                        rs.getInt("port"),
                        rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Terminal createTerminal(Terminal terminal){
        String query = "INSERT INTO terminal (port, name) VALUES(?, ?);";
        executeUpdate(query, stmt -> {
            stmt.setInt(1, terminal.getPortId());
						stmt.setString(2, terminal.getName());
        });
        return terminal;
    }
}
