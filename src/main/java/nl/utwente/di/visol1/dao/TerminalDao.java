package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.Terminal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TerminalDao extends GenericDao{
    private final static String table = "terminal"; //needed??

    public Terminal getTerminal(int terminalId) {
        ResultSet rs = executeQuery("SELECT * FROM terminal WHERE id = ?", stmt -> stmt.setInt(1, terminalId));

        try {
            //public Terminal(int id, Time open, Time close, int portId) {
            return new Terminal(
                    rs.getInt("id"),
                    rs.getTime("open"),
                    rs.getTime("close"),
                    rs.getInt("id")
            );
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
