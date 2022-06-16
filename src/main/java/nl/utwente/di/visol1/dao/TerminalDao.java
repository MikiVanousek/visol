package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Terminal;

import javax.xml.bind.JAXBElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TerminalDao extends GenericDao{
    private final static String table = "terminal"; //needed??

    public static Terminal getTerminal(int terminalId) {
        ResultSet rs = executeQuery("SELECT * FROM terminal WHERE id = ?", stmt -> stmt.setInt(1, terminalId));
        try {
	          if(!rs.next()) return null;
            return new Terminal(
                    rs.getInt("id"),
                    rs.getInt("port"),
                    rs.getString("name"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static int deleteTerminal(int terminalId) {
        return executeUpdate("DELETE FROM terminal WHERE id = ?", stm -> stm.setInt(1, terminalId));
    }

    public static int replaceTerminal(int terminalId, Terminal terminal){
        String query = "UPDATE terminal SET port = ?, name = ? WHERE id = ?";
        return executeUpdate(query, stmt -> {
            stmt.setInt(1, terminal.getPortId());
	          stmt.setString(2, terminal.getName());
	          stmt.setInt(3, terminalId);
        });

    }

    public static Map<Integer, Terminal> getTerminalsByPort(int portId) {
        ResultSet rs = executeQuery("SELECT * FROM terminal WHERE port = ?", stmt -> stmt.setInt(1, portId));
	    Map<Integer, Terminal> res = new HashMap<>();
        try {
	        if(!rs.next()) return null;
            do {
                res.put((rs.getInt("id")),(new Terminal(
                        rs.getInt("id"),
                        rs.getInt("port"),
                        rs.getString("name"))));
            }while(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Terminal createTerminal(Terminal terminal){
        String query = "INSERT INTO terminal (port, name) VALUES(?, ?) RETURNING *;";
        ResultSet rs = executeQuery(query, stmt -> {
            stmt.setInt(1, terminal.getPortId());
						stmt.setString(2, terminal.getName());
        });
	    try {
		    if(!rs.next()) return null;
		    return new Terminal(
			    rs.getInt("id"),
			    rs.getInt("port"),
			    rs.getString("name"));
	    } catch (SQLException exception) {
		    exception.printStackTrace();
		    return null;
	    }
    }
}
