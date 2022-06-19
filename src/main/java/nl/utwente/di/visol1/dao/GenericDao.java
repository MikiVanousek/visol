package nl.utwente.di.visol1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.stream.Stream;

import nl.utwente.di.visol1.util.Configuration;

public abstract class GenericDao {
	public static final Timestamp MIN_TIME = new Timestamp(0);
	public static final Timestamp MAX_TIME = Timestamp.valueOf("3000-1-1 23:05:06");
	private static Connection connection;

	protected GenericDao() {

	}

	public static void truncateAllTables() {
		Stream.of("schedule", "vessel", "berth", "terminal", "port").map(table -> "TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE")
			.forEach(GenericDao::executeUpdate);
	}

	private static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException exception) {
				throw new RuntimeException("Could not find the PostgreSQL driver", exception);
			}

			connection = DriverManager.getConnection(
				String.format(
					"jdbc:postgresql://%s/%s?currentSchema=%s",
					Configuration.Database.URL.get(),
					Configuration.Database.NAME.get(),
					Configuration.Database.SCHEMA.get()
				),
				Configuration.Database.USERNAME.get(),
				Configuration.Database.PASSWORD.get()
			);
		}
		return connection;
	}

	protected static ResultSet executeQuery(String query) {
		try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
			return statement.executeQuery(query);
		} catch (SQLException exception) {
			System.err.println("Exception executing query: " + query);
			exception.printStackTrace();
			return null;
		}
	}

	protected static int executeUpdate(String query) {
		try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
			return statement.executeUpdate(query);
		} catch (SQLException exception) {
			System.err.println("Exception executing update: " + query);
			exception.printStackTrace();
			return -1;
		}
	}

	protected static ResultSet executeQuery(String query, StatementData statementData) {
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
			return statementData.prepare(statement).executeQuery();
		} catch (SQLException exception) {
			System.err.println("Exception executing prepared query: " + query);
			exception.printStackTrace();
			return null;
		}
	}

	protected static int executeUpdate(String query, StatementData statementData) {
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
			return statementData.prepare(statement).executeUpdate();
		} catch (SQLException exception) {
			System.err.println("Exception executing prepared update: " + query);
			exception.printStackTrace();
			return -1;
		}
	}

	@FunctionalInterface
	interface StatementData {
		void inject(PreparedStatement preparedStatement) throws SQLException;

		default PreparedStatement prepare(PreparedStatement preparedStatement) throws SQLException {
			inject(preparedStatement);
			return preparedStatement;
		}
	}
}
