package nl.utwente.di.visol1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import nl.utwente.di.visol1.models.Port;

public class PortDao extends GenericDao {
	public static Port getPort(int portId) {
		try (Query query = Query.prepared("SELECT * FROM port WHERE id = ?", stmt -> stmt.setInt(1, portId))) {
			ResultSet rs = query.getResultSet();
			if (!rs.next()) return null;
			return new Port(
				rs.getInt("id"),
				rs.getString("name")
			);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public static Map<Integer, Port> getPorts() {
		Map<Integer, Port> result = new HashMap<>();
		try (Query query = Query.simple("SELECT * FROM port")) {
			ResultSet rs = query.getResultSet();
			while (rs.next()) {
				result.put(
					rs.getInt("id"),
					new Port(
						rs.getInt("id"),
						rs.getString("name")
					)
				);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return result;
	}

	public static int replacePort(int portId, Port port) {
		try (Update update = Update.prepared("UPDATE port SET name = ? WHERE id = ?", stmt -> {
			stmt.setString(1, port.getName());
			stmt.setInt(2, portId);
		})) {
			return update.getRowsChanged();
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}


	public static Port createPort(Port port) {
		try (Query query = Query.prepared("INSERT INTO port (name) VALUES (?) RETURNING *", stmt -> stmt.setString(1, port.getName()))) {
			ResultSet rs = query.getResultSet();
			if (!rs.next()) return null;
			return new Port(
				rs.getInt("id"),
				rs.getString("name")
			);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public static int deletePort(int portId) {
		try (Update update = Update.prepared("DELETE FROM port WHERE id = ?", stmt -> stmt.setInt(1, portId))) {
			return update.getRowsChanged();
		} catch (SQLException exception) {
			exception.printStackTrace();
			return -1;
		}
	}
}
